package com.zendaimoney.thirdpp.account.sei;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.filter.bizsys.BizsysSimpleFilterChain;
import com.zendaimoney.thirdpp.account.filter.channel.ChannelSimpleFilterChain;
import com.zendaimoney.thirdpp.account.pub.service.IHandleAccountService;
import com.zendaimoney.thirdpp.account.pub.vo.AccountResponseVo;
import com.zendaimoney.thirdpp.account.service.BizsysAccountConfigService;
import com.zendaimoney.thirdpp.account.service.BizsysAccountRequestService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountConfigService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.IDGenerateUtil;

public class HandleAccountService implements IHandleAccountService {

	private static final Log logger = LogFactory.getLog(HandleAccountService.class);
	
	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	@Autowired
	private ChannelAccountConfigService channelAccountConfigService;
	@Autowired
	private BizsysAccountRequestService bizsysAccountRequestService;
	@Autowired
	private BizsysAccountConfigService bizsysAccountConfigService;
	
	@Override
	public AccountResponseVo channelAccount(String reqId, String configId, String accountDay) {
		AccountResponseVo responseVo = new AccountResponseVo(AccountResponseVo.FAILED_RESPONSE_CODE);
		if (StringUtils.isBlank(reqId) && (StringUtils.isBlank(configId) || StringUtils.isBlank(accountDay))) {
			responseVo.setMsg("通道手工对账-缺少必要的参数");
			return responseVo;
		}
		
		logger.info("reqId = " + reqId);
		// 要是reqId 存在
		if (StringUtils.isNotBlank(reqId)) {
			channelAccountByChannelRequestId(reqId, responseVo);
		} else {
			channelAccountByChannelConfigIdAndAccountDay(configId, accountDay, responseVo);
		}
		if (StringUtils.isNotBlank(responseVo.getMsg())) {
			return responseVo;
		}
		responseVo.setCode(AccountResponseVo.SUCCESS_RESPONSE_CODE);
		return responseVo;
	}

	private AccountResponseVo channelAccountByChannelRequestId(String reqId, AccountResponseVo responseVo) {
		ChannelAccountRequest car = checkChannelAccountRequest(responseVo, reqId);
		if (StringUtils.isNotBlank(responseVo.getMsg())) {
			return responseVo;
		}
		
		ChannelAccountConfig config = checkChannelAccountConfig(responseVo, car);
		if (StringUtils.isNotBlank(responseVo.getMsg())) {
			return responseVo;
		}
		
		// 检查request的状态，要是状态是 8 则表示当前对账已经已经成功，无法进行手工对账
		int currentChannelRequestStatus = car.getStatus();
		if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_SUCCESS == currentChannelRequestStatus || 
				Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_SUCCESS == car.getHandleAccountStatus()) {
			logger.error("通道手工对账-通道对账请求状态已经是对账成功状态");
			responseVo.setMsg("通道手工对账-通道对账请求状态已经是对账成功状态");
			return responseVo;
		}
		
		if (Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING == car.getHandleAccountStatus()) {
			logger.error("通道手工对账-手工对账正在处理中");
			responseVo.setMsg("通道手工对账-手工对账正在处理中");
			return responseVo;
		}
		
		boolean haveChannelAutoAccountFinished = channelAutoAccountFinished(car, config, car.getAccountDay());
		if (!haveChannelAutoAccountFinished) {
			logger.error("通道手工对账-自动对账【" + car.getReqId() +"】尚未完成");
			responseVo.setMsg("通道手工对账-自动对账【" + car.getReqId() +"】尚未完成");
			return responseVo;
		}
		
		try {
			car.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING);
			int updateCount = updateChannelHandleAccountStatus(car);
			if (updateCount == 0) {
				logger.error("通道手工对账-手工对账【" + car.getReqId() + "】正在处理中");
				responseVo.setMsg("通道手工对账-手工对账【" + car.getReqId() + "】正在处理中");
				return responseVo;
			}
			
			if (updateCount == 1) {
				ChannelSimpleFilterChain chain = (ChannelSimpleFilterChain) ServerConfig.getBean(ChannelSimpleFilterChain.class.getName());
				chain.doFilter(config, car, Constants.ChannelFilter.FILTER_ANNOTATION_INITIAL, true);
			}
			
		} catch (Exception e) {
			car.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
			updateChannelAccountRequest(car);
			logger.error("通道手工对账异常", e);
			responseVo.setMsg("通道手工对账系统异常");
			return responseVo;
		}
		return responseVo;
	}
	
	private AccountResponseVo channelAccountByChannelConfigIdAndAccountDay(String configId, String accountDay, AccountResponseVo responseVo) {
		// 系统宕机导致当日没有生成对账请求，系统在处理之前需要验证该 config 在当日是否存在对账请求记录
		ChannelAccountConfig config = null;
		try {
			config = channelAccountConfigService.getChannelAccountConfigByConfigId(Long.parseLong(configId));
		} catch (Exception e) {
			logger.error("通道手工对账-查询通道配置【" + configId + "】异常", e);
			responseVo.setMsg("通道手工对账-查询通道配置【" + configId + "】异常");
			return responseVo;
		}
		
		if (config == null || config.getStatus() == Constants.ChannelAccountConfigStatus.CLOSED) {
			responseVo.setMsg("通道手工对账-通道配置项【" + configId + "】不存在或者已关闭");
			return responseVo;
		}
		
		if (!isAccountDayBeforeOrEqualsToday(accountDay)) {
			logger.error("通道手工对账-accountDay【" + accountDay + "】不合法，请重新选择对账日期");
			responseVo.setMsg("通道手工对账-accountDay【" + accountDay + "】不合法，请重新选择对账日期");
			return responseVo;
		}
		
		if (!isAutoAccountStarted(config.getAccountTime(), accountDay)) {
			logger.error("通道手工对账-今日自动对账尚未开始，无法进行手工对账");
			responseVo.setMsg("通道手工对账-今日自动对账尚未开始，无法进行手工对账");
			return responseVo;
		}
		
		ChannelAccountRequest car = null;
		try {
			car = channelAccountRequestService.getChannelAccountRequestByConfig(config, accountDay);
		} catch (Exception e) {
			logger.error("通道手工对账-查询通道对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】异常", e);
			responseVo.setMsg("通道手工对账-查询通道对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】异常");
			return responseVo;
		}
		
		if (car == null) {
			try {
				// TODO 这边可能存在自动对账和手工对账的瞬间并发的问题
				car = initAccountRequest(config, accountDay);
			} catch (Exception e) {
				logger.error("通道手工对账-通道对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】正在处理中", e);
				responseVo.setMsg("通道手工对账-通道对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】正在处理中");
				return responseVo;
			}
		} else {
			// 检查request的状态，要是状态是 8 则表示当前对账已经已经成功，无法进行手工对账
			int currentChannelRequestStatus = car.getStatus();
			if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_SUCCESS == currentChannelRequestStatus || 
					Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_SUCCESS == car.getHandleAccountStatus()) {
				logger.error("通道手工对账-通道对账请求状态已经是对账成功状态");
				responseVo.setMsg("通道手工对账-通道对账请求状态已经是对账成功状态");
				return responseVo;
			}
			
			if (Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING == car.getHandleAccountStatus()) {
				logger.error("通道手工对账-手工对账正在处理中");
				responseVo.setMsg("通道手工对账-手工对账正在处理中");
				return responseVo;
			}
			
			boolean haveChannelAutoAccountFinished = channelAutoAccountFinished(car, config, accountDay);
			if (!haveChannelAutoAccountFinished) {
				logger.error("通道手工对账-自动对账【" + car.getReqId() +"】尚未完成");
				responseVo.setMsg("通道手工对账-自动对账【" + car.getReqId() +"】尚未完成");
				return responseVo;
			}
		}
		
		try {
			car.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING);
			int updateCount = updateChannelHandleAccountStatus(car);
			if (updateCount == 0) {
				logger.error("通道手工对账-手工对账【" + car.getReqId() + "】正在处理中");
				responseVo.setMsg("通道手工对账-手工对账【" + car.getReqId() + "】正在处理中");
				return responseVo;
			}
			
			if (updateCount == 1) {
				ChannelSimpleFilterChain chain = (ChannelSimpleFilterChain) ServerConfig.getBean(ChannelSimpleFilterChain.class.getName());
				chain.doFilter(config, car, Constants.ChannelFilter.FILTER_ANNOTATION_INITIAL, true);
			}
		} catch (Exception e) {
			car.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
			updateChannelAccountRequest(car);
			logger.error("通道手工对账异常", e);
			responseVo.setMsg("通道手工对账系统异常");
			return responseVo;
		}
		return responseVo;
	}
	
	private boolean channelAutoAccountFinished(ChannelAccountRequest car, ChannelAccountConfig config, String accountDay) {
		boolean autoChannelAccountFinished = false;
		int status = car.getStatus();
		int downloadFailedTimes = car.getDownloadFailedTimes();
		int insertFailedTimes = car.getInsertFailedTimes();
		int accountFailedTimes = car.getAccountFailedTimes();
		int backupFailedTimes = car.getBackupFailedTimes();
		if (isAccountDayEqualsToday(accountDay)) {
			if ((downloadFailedTimes >= config.getMaxDownloadFailedTimes())
					|| (insertFailedTimes >= config.getMaxInsertFailedTimes()) 
					|| (accountFailedTimes >= config.getMaxAccountFailedTimes())
					|| (backupFailedTimes >= config.getMaxBackupFailedTimes()) 
					|| Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_SUCCESS == status) {
				autoChannelAccountFinished = true;
			}
		} else if (isAccountDayBeforeToday(accountDay)) {
			if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_DOWNLOAD_FILE_FAILED == status
					&& downloadFailedTimes < config.getMaxDownloadFailedTimes()) {
				car.setDownloadFailedTimes(config.getMaxDownloadFailedTimes());
			} else if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INSERT_TABLE_FAILED == status
					&& insertFailedTimes < config.getMaxInsertFailedTimes()) {
				car.setInsertFailedTimes(config.getMaxInsertFailedTimes());
			} else if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_FAILED == status
					&& accountFailedTimes < config.getMaxAccountFailedTimes()) {
				car.setAccountFailedTimes(config.getMaxAccountFailedTimes());
			} else if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_FAILED == status
					&& backupFailedTimes < config.getMaxBackupFailedTimes()) {
				car.setBackupFailedTimes(config.getMaxBackupFailedTimes());
			}
			
			channelAccountRequestService.update(car);
			autoChannelAccountFinished = true;
		}
	
		return autoChannelAccountFinished;
	}
	
	private boolean bizsysAutoAccountFinished(BizsysAccountRequest bar, BizsysAccountConfig config, String accountDay) {
		boolean autoBizsysAccountFinished = false;
		int status = bar.getStatus();
		int localizeFailedTimes = bar.getLocalizeFailedTimes();
		int pushFailedTimes = bar.getPushFailedTimes();
		if (isAccountDayEqualsToday(accountDay)) {
			if ((localizeFailedTimes >= config.getMaxLocalizeFailedTimes())
					|| (pushFailedTimes >= config.getMaxPushFailedTimes())
					|| Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_SUCCESS == status) {
				autoBizsysAccountFinished = true;
			}
		}  else if (isAccountDayBeforeToday(accountDay)) {
			if (Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED == status 
					&& localizeFailedTimes < config.getMaxLocalizeFailedTimes()) {
				bar.setLocalizeFailedTimes(config.getMaxLocalizeFailedTimes());
			} else if (Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_FAILED == status 
					&& pushFailedTimes < config.getMaxPushFailedTimes()) {
				bar.setPushFailedTimes(config.getMaxPushFailedTimes());
			}
			bizsysAccountRequestService.update(bar);
			autoBizsysAccountFinished = true;
		}
		return autoBizsysAccountFinished;
	}
	
	private ChannelAccountRequest checkChannelAccountRequest(AccountResponseVo responseVo, String reqId){
		ChannelAccountRequest car = null;
		try {
			car = channelAccountRequestService.getChannelAccountRequestByRequestId(reqId);
		} catch (Exception e) {
			logger.error("通道手工对账-查询通道对账请求【" + reqId + "】异常", e);
			responseVo.setMsg("通道手工对账-查询通道对账请求【" + reqId + "】异常");
			return car;
		}
		// 对账请求已经被手工删除
		if (car == null) {
			logger.error("通道手工对账-请求【" + reqId + "】不存在");
			responseVo.setMsg("通道手工对账-请求【" + reqId + "】不存在");
		}
		return car;
	}
	
	private void updateChannelAccountRequest(ChannelAccountRequest request){
		channelAccountRequestService.update(request);
	}
	
	private int updateChannelHandleAccountStatus(ChannelAccountRequest request) throws SQLException{
		return channelAccountRequestService.updateHandleAccountStatus(request);
	}
	
	private int updateBizsysHandleAccountStatus(BizsysAccountRequest request) throws SQLException{
		return bizsysAccountRequestService.updateHandleAccountStatus(request);
	}
	
	private void updateBizsysAccountRequest(BizsysAccountRequest request){
		bizsysAccountRequestService.update(request);
	}
	
	private ChannelAccountConfig checkChannelAccountConfig(AccountResponseVo responseVo,ChannelAccountRequest car) {
		// 查询request 相关联的 config 对象
		ChannelAccountConfig config = null;
		try {
			config = channelAccountConfigService.getChannelAccountConfigByConfigId(car.getConfigId());
		} catch (Exception e) {
			logger.error("通道手工对账-查询通道配置【" + car.getConfigId() + "】异常", e);
			responseVo.setMsg("通道手工对账-查询通道配置【" + car.getConfigId() + "】异常");
			return config;
		}
		logger.info("config = " + config);
		if (config == null || config.getStatus() == Constants.ChannelAccountConfigStatus.CLOSED) {
			responseVo.setMsg("通道手工对账-配置项【" + car.getConfigId() + "】不存在或者已关闭");
		}
		return config;
	}
	
	@Override
	public AccountResponseVo bizsysAccount(String reqId, String configId, String accountDay) {
		AccountResponseVo responseVo = new AccountResponseVo(AccountResponseVo.FAILED_RESPONSE_CODE);
		if (StringUtils.isBlank(reqId) && (StringUtils.isBlank(configId) || StringUtils.isBlank(accountDay))) {
			responseVo.setMsg("业务系统手工对账-缺少必要的参数");
			return responseVo;
		}
		
		// 要是reqId 存在
		if (StringUtils.isNotBlank(reqId)) {
			bizAccountByChannelRequestId(reqId, responseVo);
		} else {
			bizAccountByChannelConfigIdAndAccountDay(configId, accountDay, responseVo);
		}
		
		if (StringUtils.isNotBlank(responseVo.getMsg())) {
			return responseVo;
		}
		responseVo.setCode(AccountResponseVo.SUCCESS_RESPONSE_CODE);
		return responseVo;
	}

	private AccountResponseVo bizAccountByChannelRequestId(String reqId, AccountResponseVo responseVo) {
		BizsysAccountRequest car = null;
		try {
			car = bizsysAccountRequestService.getBizsysAccountRequestByRequestId(reqId);
		} catch (Exception e) {
			logger.error("业务系统手工对账-查询业务系统对账请求【" + reqId + "】异常", e);
			responseVo.setMsg("业务系统手工对账-查询业务系统对账请求【" + reqId + "】异常");
			return responseVo;
		}
		// 对账请求已经被手工删除
		if (car == null) {
			responseVo.setMsg("业务系统手工对账-请求【" + reqId + "】不存在");
			return responseVo;
		}
		
		if (Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_SUCCESS == car.getStatus()
				|| Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_SUCCESS == car.getHandleAccountStatus()) {
			logger.error("业务系统手工对账-手工对账已经完成");
			responseVo.setMsg("业务系统手工对账-手工对账已经完成");
			return responseVo;
		}
		
		if (Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING == car.getHandleAccountStatus()) {
			logger.error("业务系统手工对账-手工对账正在处理中");
			responseVo.setMsg("业务系统手工对账-手工对账正在处理中");
			return responseVo;
		}
		
		BizsysAccountConfig config = null;
		try {
			config = bizsysAccountConfigService.getBizsysAccountConfigByConfigId(String.valueOf(car.getConfigId()));
		} catch (Exception e) {
			logger.error("业务系统手工对账-查询业务系统配置【" + car.getConfigId() + "】异常", e);
			responseVo.setMsg("业务系统手工对账-查询业务系统配置【" + car.getConfigId() + "】异常");
			return responseVo;
		}
		if (config == null || config.getStatus() == Constants.BizsysAccountConfigStatus.CLOSED) {
			responseVo.setMsg("业务系统手工对账-配置项【" + car.getConfigId() + "】不存在或者已关闭");
			return responseVo;
		}
		
		try {
			boolean isRelatedBiztypeAccounted = whetherAllBizTypeAccounted(config, car.getAccountDay());
			if (!isRelatedBiztypeAccounted) {
				logger.error("业务系统对账请求【 " + reqId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
				responseVo.setMsg("业务系统对账请求【" + reqId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
				return responseVo;
			}
		} catch (Exception e1) {
			logger.error("业务系统对账请求【" + reqId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
			responseVo.setMsg("业务系统对账请求【" + reqId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
			return responseVo;
		}
		
		boolean haveBizsysAutoAccountFinished = bizsysAutoAccountFinished(car, config, car.getAccountDay());
		if (!haveBizsysAutoAccountFinished) {
			logger.error("业务系统手工对账-自动对账【" + car.getReqId() +"】尚未完成");
			responseVo.setMsg("业务系统手工对账-自动对账【" + car.getReqId() +"】尚未完成");
			return responseVo;
		}
		
		try {
			car.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING);
			int updateCount = updateBizsysHandleAccountStatus(car);
			if (updateCount == 0) {
				logger.error("业务系统手工对账-手工对账【" + car.getReqId() +"】正在处理中");
				responseVo.setMsg("业务系统手工对账-手工对账【" + car.getReqId() +"】正在处理中");
				return responseVo;
			} 
			
			if (updateCount == 1) {
				BizsysSimpleFilterChain chain = (BizsysSimpleFilterChain) ServerConfig
						.getBean(BizsysSimpleFilterChain.class.getName());
				chain.doFilter(car,config, Constants.ChannelFilter.FILTER_ANNOTATION_INITIAL, true);
			}
			
		} catch (Exception e) {
			car.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
			updateBizsysAccountRequest(car);
			logger.error("业务系统手工对账异常", e);
			responseVo.setMsg("业务系统手工对账系统异常");
			return responseVo;
		}
		return responseVo;
	}
	
	private AccountResponseVo bizAccountByChannelConfigIdAndAccountDay(String configId, String accountDay, AccountResponseVo responseVo) {
		// 系统宕机导致当日没有生成对账请求，系统在处理之前需要验证该 config 在当日是否存在对账请求记录
		BizsysAccountConfig config = null;
		try {
			config = bizsysAccountConfigService.getBizsysAccountConfigByConfigId(configId);
		} catch (Exception e) {
			logger.error("业务系统手工对账-查询业务系统配置【" + configId + "】异常", e);
			responseVo.setMsg("业务系统手工对账-查询业务系统配置【" + configId + "】异常");
			return responseVo;
		}
		
		if (config == null || config.getStatus() == Constants.BizsysAccountConfigStatus.CLOSED) {
			responseVo.setMsg("业务系统手工对账-业务系统配置项【" + configId + "】不存在或者已关闭");
			return responseVo;
		}
		
		if (!isAccountDayBeforeOrEqualsToday(accountDay)) {
			logger.error("业务系统手工对账-accountDay【" + accountDay + "】不合法，请重新选择对账日期");
			responseVo.setMsg("业务系统手工对账-accountDay【" + accountDay + "】不合法，请重新选择对账日期");
			return responseVo;
		}
		
		if (!isAutoAccountStarted(config.getAccountTime(), accountDay)) {
			logger.error("业务系统手工对账-今日自动对账尚未开始，无法进行手工对账");
			responseVo.setMsg("业务系统手工对账-今日自动对账尚未开始，无法进行手工对账");
			return responseVo;
		}
		
		try {
			boolean isRelatedBiztypeAccounted = whetherAllBizTypeAccounted(config, accountDay);
			if (!isRelatedBiztypeAccounted) {
				logger.error("业务系统对账配置项【config_id = " + configId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
				responseVo.setMsg("业务系统对账配置项【config_id = " + configId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
				return responseVo;
			}
		} catch (Exception e1) {
			logger.error("业务系统对账配置项【config_id = " + configId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
			responseVo.setMsg("业务系统对账配置项【config_id = " + configId + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
			return responseVo;
		}
		
		BizsysAccountRequest bar = null;
		try {
			bar = bizsysAccountRequestService.getBizsysAccountRequestByConfigId(configId, accountDay);
		} catch (Exception e) {
			logger.error("业务系统手工对账-查询业务系统对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】异常", e);
			responseVo.setMsg("业务系统手工对账-查询业务系统对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】异常");
			return responseVo;
		}
		
		if (bar == null) {
			try {
				bar = initialBizsysAccountRequest(config, accountDay);
			} catch (Exception e) {
				logger.error("业务系统手工对账-业务系统对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】正在处理中", e);
				responseVo.setMsg("业务系统手工对账-业务系统对账请求 config_id【" + configId + "】, accountDay【" + accountDay + "】正在处理中");
				return responseVo;
			}
		} else {
			if (Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_SUCCESS == bar.getStatus()
					|| Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_SUCCESS == bar.getHandleAccountStatus()) {
				logger.error("业务系统手工对账-手工对账已经完成");
				responseVo.setMsg("业务系统手工对账-手工对账已经完成");
				return responseVo;
			}
			
			if (Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING == bar.getHandleAccountStatus()) {
				logger.error("业务系统手工对账-手工对账正在处理中");
				responseVo.setMsg("业务系统手工对账-手工对账正在处理中");
				return responseVo;
			}
			
			boolean haveBizsysAutoAccountFinished = bizsysAutoAccountFinished(bar, config, accountDay);
			if (!haveBizsysAutoAccountFinished) {
				logger.error("业务系统手工对账-自动对账【" + bar.getReqId() +"】尚未完成");
				responseVo.setMsg("业务系统手工对账-自动对账【" + bar.getReqId() +"】尚未完成");
				return responseVo;
			}
		}
		
		try {
			bar.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_DEALING);
			int updateCount = updateBizsysHandleAccountStatus(bar);
			if (updateCount == 0) {
				logger.error("业务系统手工对账-手工对账【" + bar.getReqId() +"】正在处理中");
				responseVo.setMsg("业务系统手工对账-手工对账【" + bar.getReqId() +"】正在处理中");
				return responseVo;
			} 
			
			if (updateCount == 1) {
				BizsysSimpleFilterChain chain = (BizsysSimpleFilterChain) ServerConfig
						.getBean(BizsysSimpleFilterChain.class.getName());
				chain.doFilter(bar,config, Constants.ChannelFilter.FILTER_ANNOTATION_INITIAL, true);
			}
		} catch (Exception e) {
			bar.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
			updateBizsysAccountRequest(bar);
			logger.error("业务系统手工对账异常", e);
			responseVo.setMsg("业务系统手工对账系统异常");
			return responseVo;
		}
	
		return responseVo;
	}
	private BizsysAccountRequest initialBizsysAccountRequest(BizsysAccountConfig config, String accountDay){
		BizsysAccountRequest bizsysAccountRequest = new BizsysAccountRequest();
		bizsysAccountRequest.setAccountDay(accountDay);
		bizsysAccountRequest.setBizSysNo(config.getBizSysNo());
		bizsysAccountRequest.setReqId(IDGenerateUtil.createReqId());
		bizsysAccountRequest.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_INITIAL);
		bizsysAccountRequest.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_INITIAL);
		bizsysAccountRequest.setConfigId(config.getId());
		bizsysAccountRequest.setBizType(config.getBizType());
		bizsysAccountRequest.setAppName(ServerConfig.systemConfig.getAppName());
		bizsysAccountRequest.setLocalizeFailedTimes(config.getMaxLocalizeFailedTimes());
		bizsysAccountRequest.setPushFailedTimes(config.getMaxPushFailedTimes());
		bizsysAccountRequestService.insert(bizsysAccountRequest);
		return bizsysAccountRequest;
	}
	
	/**
	 * 初始化第三方对账请求记录
	 * 
	 * @param config
	 * @throws SQLException
	 */
	private ChannelAccountRequest initAccountRequest(ChannelAccountConfig config, String accountDay) {
		ChannelAccountRequest accountRequest = new ChannelAccountRequest();
		accountRequest.setReqId(IDGenerateUtil.createReqId());
		accountRequest
				.setStatus(Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_INITIAL);
		accountRequest.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_INITIAL);
		accountRequest.setThirdTypeNo(config.getThirdTypeNo());
		accountRequest.setMerchantNo(config.getMerchantNo());
		accountRequest.setConfigId(config.getId());
		accountRequest
				.setDownloadFailedTimes(config.getMaxDownloadFailedTimes());
		accountRequest.setAccountFailedTimes(config.getMaxAccountFailedTimes());
		accountRequest.setInsertFailedTimes(config.getMaxInsertFailedTimes());
		accountRequest.setAccountDay(accountDay);
		accountRequest.setBackupFailedTimes(config.getMaxBackupFailedTimes());
		accountRequest.setBizType(config.getBizType());
		accountRequest.setAppName(ServerConfig.systemConfig.getAppName());
		channelAccountRequestService.insert(accountRequest);
		return accountRequest;
	}
	
	/**
	 * 判断对账日期是否大于当前的系统日期，对账日期不能大于当前日期（大于表示还没进行自动对账就进行手工对账了）
	 * 
	 * @param accountDay
	 * @return
	 */
	private boolean isAccountDayBeforeOrEqualsToday(String accountDay) {
		return isAccountDayBeforeToday(accountDay) || isAccountDayEqualsToday(accountDay);
	}
	
	/**
	 * 判断对账日期是否大于当前的系统日期，对账日期不能大于当前日期（大于表示还没进行自动对账就进行手工对账了）
	 * 
	 * @param accountDay
	 * @return
	 */
	private boolean isAccountDayBeforeToday(String accountDay) {
		boolean a = false;
		int currentDay = Integer.parseInt(CalendarUtils.getShortFormatNow());
		int accountDayInt =  Integer.parseInt(accountDay);
		if (accountDayInt < currentDay) {
			a = true;
		}
		return a;
	}
	
	
	/**
	 * 判断对账日期是否大于当前的系统日期，对账日期不能大于当前日期（大于表示还没进行自动对账就进行手工对账了）
	 * 
	 * @param accountDay
	 * @return
	 */
	private boolean isAccountDayEqualsToday(String accountDay) {
		boolean a = false;
		int currentDay = Integer.parseInt(CalendarUtils.getShortFormatNow());
		int accountDayInt =  Integer.parseInt(accountDay);
		if (accountDayInt == currentDay) {
			a = true;
		}
		return a;
	}
	
	private boolean isAutoAccountStarted(String accountTime, String accountDay) {
		if (StringUtils.isBlank(accountTime)) {
			accountTime = CalendarUtils.DEFAULT_TIME_FORMAT_TIME_COLON;
		}
		String dayShortLineFormat = CalendarUtils.getShortFormatNow();
		if (accountDay.equals(dayShortLineFormat)) {
			String completeAccountTime = dayShortLineFormat.concat(accountTime);
			Date accountTimeDate = CalendarUtils.convertTimeFormatStringToDate(completeAccountTime);
			long a = CalendarUtils.getCurrentDateTime().getTime(); // 现在的系统时间
			long b = accountTimeDate.getTime(); // 当日的对账时间
			return b < a;
		}
		return true;
	}
	
	/**
	 * 检查所有的业务类型是否都对账完成
	 * @param config
	 * @return true 表示该  config 中配置的bizType中，涉及的业务类型都已经完成对账
	 * @return false 表示该  config 中配置的bizType中，涉及的业务类型未都已经完成对账
	 */
	private boolean whetherAllBizTypeAccounted(BizsysAccountConfig config, String accountDay) throws SQLException{
		boolean flag = true;
		String bizType = config.getBizType();
		if (StringUtils.isNotBlank(bizType)) {
			boolean moreThanOne = bizType.contains(Constants.STRING_DOWN_LINE);
			if (moreThanOne) {
				// 配置了多个业务类型
				String[] bizTypes = bizType.split(Constants.STRING_DOWN_LINE);
				for (String s : bizTypes) {
					if (!accounted(s, accountDay)) {
						flag = false;
						break;
					}
				}
			} else {
				flag = accounted(bizType, accountDay);
			}
		}
		return flag;
	}
	
	private boolean accounted(String bizType, String accountDay) throws SQLException{
		boolean flag = true;
		// 只配置了单个业务类型
		List<ChannelAccountConfig> cacList = channelAccountConfigService.getChannelAccountConfigByBizType(bizType);
		for(ChannelAccountConfig cac : cacList) {
			ChannelAccountRequest car = channelAccountRequestService.getChannelAccountRequestByConfig(cac, accountDay);
			if (car == null || car.getStatus() < Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_SUCCESS) {
				flag = false;
				break;
			}
		}
		return flag;
	}
}

package com.zendaimoney.thirdpp.account.action;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.filter.bizsys.BizsysSimpleFilterChain;
import com.zendaimoney.thirdpp.account.service.BizsysAccountRequestService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountConfigService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.AccountResponse;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.account.util.Constants;
import com.zendaimoney.thirdpp.account.util.IDGenerateUtil;

@Component(value = "com.zendaimoney.thirdpp.account.action.BizsysAccountCoreAction")
public class BizsysAccountCoreAction {

	private static final Log logger = LogFactory.getLog(BizsysAccountCoreAction.class);
	@Autowired
	private ChannelAccountConfigService channelAccountConfigService;
	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	@Autowired
	private BizsysAccountRequestService bizsysAccountRequestService;
	
	public AccountResponse process(BizsysAccountConfig config, String threadName) throws SQLException {
		logger.info("业务系统对账线程【" + threadName + "】开始处理");		
		
		AccountResponse response = new AccountResponse(
				AccountResponse.ACCOUNT_RESPONSE_STATUS_SUCCESS,
				AccountResponse.ACCOUNT_RESPONSE_MESSAGEID_DEFAULT);
		
		String accountTime = config.getAccountTime();
		if (StringUtils.isBlank(accountTime)) {
			logger.info("业务系统对账配置项【config_id = " + config.getId() + "】未配置对账时间");
			response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
			response.setMessageId(AccountResponse.CommonMessage.THIRD_PARTY_ACCOUNT_NO_ACCOUNT_TIME.getmId());
			response.setMessageContent(AccountResponse.CommonMessage.THIRD_PARTY_ACCOUNT_NO_ACCOUNT_TIME.getmContent());
			return response;
		}
		
		BizsysSimpleFilterChain chain = (BizsysSimpleFilterChain) ServerConfig
				.getBean(BizsysSimpleFilterChain.class.getName());
		
		long id = config.getId();
		BizsysAccountRequest bar = bizsysAccountRequestService.getBizsysAccountRequestByConfigId(String.valueOf(id));
		// 今天该配置还没有进行对账
		if (bar == null) {
			long t = compareNowToAccountTime(config);
			if (t > 0) {
				// 还没有到当日对账时间
				logger.info("业务系统对账未到当日对账时间," + threadName + "将休眠" + t);
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setMessageId(AccountResponse.CommonMessage.CONFIG_NOT_START
						.getmId());
				response.setMessageContent(AccountResponse.CommonMessage.CONFIG_NOT_START
						.getmContent());
				response.setSleepTime(t);
				return response;
			} else {
				bar = initialBizsysAccountRequest(config);
				// 检查相关业务的第三方有没有对账完成
				if (!whetherAllBizTypeAccounted(config)) {
					bar.setLocalizeFileName(StringUtils.EMPTY);
					bar.setLocalizePath(StringUtils.EMPTY);
					bar.setLocalizeFailedTimes(bar.getLocalizeFailedTimes() + 1);
					bar.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED);
					bar.setFailedReason("业务系统对账配置项【config_id = " + id + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
					bizsysAccountRequestService.update(bar);
					throw new PlatformException("业务系统对账配置项【config_id = " + id + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
				} else {
					chain.doFilter(bar, config, Constants.BizsysFilter.FILTER_ANNOTATION_INITIAL, false);
				}
			}
		} else {
			int generateFileFailedTimes = bar.getLocalizeFailedTimes();
			int maxGenerateFileFailedTimes = config.getMaxLocalizeFailedTimes();
			if (generateFileFailedTimes >= maxGenerateFileFailedTimes) {
				logger.info("业务系统对账入表失败次数大于等于配置的允许失败最大次数");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
			
			int pushFailedTimes = bar.getPushFailedTimes();
			int maxPushFailedTimes = config.getMaxPushFailedTimes();
			if (pushFailedTimes >= maxPushFailedTimes) {
				logger.info("业务系统对账推送失败次数大于等于配置的允许失败最大次数");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
			
			// 今天该配置已经进行了对账
			if (Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_PUSH_SUCCESS == bar.getStatus()) {
				long sleepTime = compareNowToNextDayAccountTime(config);
				logger.info("业务系统对账【 "+ threadName + "】今日对账已经完成");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_SUCCESS);
				response.setMessageId(AccountResponse.CommonMessage.FINISHED.getmId());
				response.setMessageContent(AccountResponse.CommonMessage.FINISHED.getmContent());
				response.setSleepTime(sleepTime);
				return response;
			}
			// 检查相关业务的第三方有没有对账完成
			if (!whetherAllBizTypeAccounted(config)) {
				bar.setLocalizeFileName(StringUtils.EMPTY);
				bar.setLocalizePath(StringUtils.EMPTY);
				bar.setLocalizeFailedTimes(bar.getLocalizeFailedTimes() + 1);
				bar.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED);
				bar.setFailedReason("业务系统对账配置项【config_id = " + id + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
				bizsysAccountRequestService.update(bar);
				throw new PlatformException("业务系统对账配置项【config_id = " + id + "】配置属性bizType中涉及的业务类型未全部完成第三方对账");
			} else {
				chain.doFilter(bar, config, Constants.BizsysFilter.FILTER_ANNOTATION_INITIAL, false);
			}
		}
		return response;
	}
	
	/**
	 * 当前时间和对账时间的时间差
	 * 
	 * @return 时间差 为 0 或者正直表示当前时间到达对账时间可以开始对账 为负值表示尚未到当日的对账开始时间
	 */
	private long compareNowToAccountTime(BizsysAccountConfig config) {
		String accountTime = config.getAccountTime();
		if (StringUtils.isBlank(accountTime)) {
			accountTime = CalendarUtils.DEFAULT_TIME_FORMAT_TIME_COLON;
		}
		String dayShortLineFormat = CalendarUtils.getShortFormatNow();
		String completeAccountTime = dayShortLineFormat.concat(accountTime);
		Date accountTimeDate = CalendarUtils
				.convertTimeFormatStringToDate(completeAccountTime);
		long a = CalendarUtils.getCurrentDateTime().getTime(); // 现在的系统时间
		long b = accountTimeDate.getTime(); // 当日的对账时间
		return b - a;
	}
	
	/**
	 * 检查所有的业务类型是否都对账完成
	 * @param config
	 * @return true 表示该  config 中配置的bizType中，涉及的业务类型都已经完成对账
	 * @return false 表示该  config 中配置的bizType中，涉及的业务类型未都已经完成对账
	 */
	private boolean whetherAllBizTypeAccounted(BizsysAccountConfig config) throws SQLException{
		boolean flag = true;
		String bizType = config.getBizType();
		if (StringUtils.isNotBlank(bizType)) {
			boolean moreThanOne = bizType.contains(Constants.STRING_DOWN_LINE);
			if (moreThanOne) {
				// 配置了多个业务类型
				String[] bizTypes = bizType.split(Constants.STRING_DOWN_LINE);
				for (String s : bizTypes) {
					if (!accounted(s)) {
						flag = false;
						break;
					}
				}
			} else {
				flag = accounted(bizType);
			}
		}
		return flag;
	}
	
	private boolean accounted(String bizType) throws SQLException{
		boolean flag = true;
		// 只配置了单个业务类型
		List<ChannelAccountConfig> cacList = channelAccountConfigService.getChannelAccountConfigByBizType(bizType);
		for(ChannelAccountConfig cac : cacList) {
			ChannelAccountRequest car = channelAccountRequestService.getChannelAccountRequestByConfig(cac);
			if (car == null || car.getStatus() < Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_ACCOUNT_SUCCESS) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	private BizsysAccountRequest initialBizsysAccountRequest(BizsysAccountConfig config){
		BizsysAccountRequest bizsysAccountRequest = new BizsysAccountRequest();
		bizsysAccountRequest.setAccountDay(CalendarUtils.getShortFormatNow());
		bizsysAccountRequest.setBizSysNo(config.getBizSysNo());
		bizsysAccountRequest.setReqId(IDGenerateUtil.createReqId());
		bizsysAccountRequest.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_INITIAL);
		bizsysAccountRequest.setConfigId(config.getId());
		bizsysAccountRequest.setBizType(config.getBizType());
		bizsysAccountRequest.setAppName(config.getAppName());
		bizsysAccountRequestService.insert(bizsysAccountRequest);
		return bizsysAccountRequest;
	}
	
	/**
	 * 当前时间和明日对账时间的时间差
	 * 发生在今日对账已经完成的情况下
	 * @return 
	 */
	private long compareNowToNextDayAccountTime(BizsysAccountConfig config) {
		String accountTime = config.getAccountTime();
		if (StringUtils.isBlank(accountTime)) {
			accountTime = CalendarUtils.DEFAULT_TIME_FORMAT_TIME_COLON;
		}
		String dayShortLineFormat = CalendarUtils.parsefomatCalendar(CalendarUtils.getNextDay(Calendar.getInstance()), CalendarUtils.SHORT_FORMAT);
		String completeAccountTime = dayShortLineFormat.concat(accountTime);
		Date accountTimeDate = CalendarUtils
				.convertTimeFormatStringToDate(completeAccountTime);
		long a = CalendarUtils.getCurrentDateTime().getTime(); // 现在的系统时间
		long b = accountTimeDate.getTime(); // 明日的对账时间
		return b - a;
	}

	
}

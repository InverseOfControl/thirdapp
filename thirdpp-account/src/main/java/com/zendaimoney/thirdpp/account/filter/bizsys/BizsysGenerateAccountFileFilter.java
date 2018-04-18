package com.zendaimoney.thirdpp.account.filter.bizsys;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.annotation.BizsysFilterAnnotation;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountInfo;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.BizsysAccountInfoService;
import com.zendaimoney.thirdpp.account.service.BizsysAccountRequestService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountInfoService;
import com.zendaimoney.thirdpp.account.util.Constants;

@BizsysFilterAnnotation(filterStep = Constants.BizsysFilter.FILTER_ANNOTATION_GENERATE_FILE)
@Component(value = "com.zendaimoney.thirdpp.account.filter.bizsys.BizsysGenerateAccountFileFilter")
public class BizsysGenerateAccountFileFilter implements BizsysFilter {
	@Autowired
	private BizsysAccountRequestService bizsysAccountRequestService;
	@Autowired
	private BizsysAccountInfoService bizsysAccountStatisticService;
	@Autowired
	private ChannelAccountInfoService channelAccountInfoService;
	@Autowired
	private BizsysAccountInfoService bizsysAccountInfoService;

	private static final Log logger = LogFactory.getLog(BizsysGenerateAccountFileFilter.class);

	@Override
	public void doFilter(BizsysAccountRequest request, BizsysAccountConfig config,BizsysSimpleFilterChain chain, boolean isHandle) {
		String currentThreadName = Thread.currentThread().getName();
		if (willGenereateFile(request, config)) {
			try {
				startLocalizeOperation(request);
			} catch (Exception e) {
				request.setLocalizeStartTime(null);
				request.setLocalizeFailedTimes(request.getLocalizeFailedTimes() + 1);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED);
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				bizsysAccountRequestService.update(request);
				throw new PlatformException("生成业务系统对账文件更新本地化开始时间失败", e);
			}
			
			try {
				if (isHandle) {
					logger.info("业务系统【手工】对账生成文件头文件开始......");
				} else {
					logger.info(currentThreadName + "业务系统对账生成文件头文件开始......");
				}
				long currentStartTime = System.currentTimeMillis();
				
				bizsysAccountStatisticService.generateFile(request, config);
				long currentEndTime = System.currentTimeMillis();
				if (isHandle) {
					logger.info("业务系统【手工】对账生成文件头文件结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				} else {
					logger.info(currentThreadName + "业务系统对账生成文件头文件结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				}
			} catch (Exception e) {
				request.setLocalizeFileName(StringUtils.EMPTY);
				request.setLocalizePath(StringUtils.EMPTY);
				request.setLocalizeFailedTimes(request.getLocalizeFailedTimes() + 1);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED);
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				bizsysAccountRequestService.update(request);
				throw new PlatformException("生成业务系统对账文件失败", e);
			}
			try {
				if (isHandle) {
					logger.info("业务系统【手工】对账填充数据开始......");
				} else {
					logger.info(currentThreadName + "业务系统对账填充数据开始......");
				}
				long currentStartTime = System.currentTimeMillis();
				pushData(request, config);
				
				long currentEndTime = System.currentTimeMillis();
				if (isHandle) {
					logger.info("业务系统【手工】对账填充数据结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				} else {
					logger.info(currentThreadName + "业务系统对账填充数据结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				}
			} catch (Exception e) {
				request.setLocalizeFailedTimes(request.getLocalizeFailedTimes() + 1);
				request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				bizsysAccountRequestService.update(request);
				throw new PlatformException("填充业务系统对账文件数据失败", e);
			}
			try {
				if (isHandle) {
					logger.debug("业务系统【手工】对账更新业务系统对账请求表开始......");
				} else {
					logger.debug(currentThreadName + "业务系统对账更新业务系统对账请求表开始......");
				}
				long currentStartTime = System.currentTimeMillis();
				
				updateBizsysAccountRequestToSuccess(request);
				long currentEndTime = System.currentTimeMillis();
				if (isHandle) {
					logger.info("业务系统【手工】对账更新业务系统对账请求表结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				} else {
					logger.info(currentThreadName + "业务系统对账更新业务系统对账请求表结束耗时(" + (currentEndTime - currentStartTime) + ")......");
				}
				
			} catch (Exception e){
				request.setLocalizeEndTime(null);
				request.setPushStartTime(null);
				request.setLocalizeFailedTimes(request.getLocalizeFailedTimes() + 1);
				request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED);
				if (isHandle) {
					request.setHandleAccountStatus(Constants.HandleAccountStatus.HANDLE_ACCOUNT_STATUS_FAILED);
				}
				request.setFailedReason(StringUtils.substring(e.getMessage(),0, Constants.DEFAULT_EXCEPTION_MESSAGE_LENGTH));
				bizsysAccountRequestService.update(request);
				throw new PlatformException("更新业务系统对账请求表失败", e);
			}
		}
		
		chain.doFilter(request, config, Constants.BizsysFilter.FILTER_ANNOTATION_GENERATE_FILE, isHandle);
	}

	private void startLocalizeOperation(BizsysAccountRequest request){
		if (request.getLocalizeStartTime() == null) {
			request.setLocalizeStartTime(new Date());
			bizsysAccountRequestService.update(request);
		}
	}
	
	private void updateBizsysAccountRequestToSuccess(BizsysAccountRequest request){
		request.setStatus(Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_SUCCESS);
		request.setFailedReason(StringUtils.EMPTY);
		request.setLocalizeEndTime(new Date());
		bizsysAccountRequestService.update(request);
	}
	
	private boolean willGenereateFile(BizsysAccountRequest request,  BizsysAccountConfig config) {
		boolean b = true;
		int status = request.getStatus();
		if (status != Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_INITIAL 
				&& status != Constants.BizsysAccountRequestStatus.BIZSYS_ACCOUNT_REQUEST_STATUS_LOCALIZE_FAILED) {
			b = false;
		}
		
		return b;
	}

	private void pushData(BizsysAccountRequest request, BizsysAccountConfig config) throws SQLException, IOException, ReflectiveOperationException {
		List<String> btStr = getAllConfigedBiztype(request);
		AccountInfo lastBizsysAccountInfo = getLastAccountInfoByBizsysRequest(request);
		if (lastBizsysAccountInfo != null) {
			// 已经存在与业务系统对账成功的业务类型
			String alreadyFinishedBizsysType = lastBizsysAccountInfo.getBizType();
			int currentIndex = btStr.indexOf(alreadyFinishedBizsysType);
			for (int i = currentIndex; i < btStr.size() ; i++) {
				List<Long> taskIdList = new ArrayList<Long>();
				List<BizsysAccountInfo> statisticList = new ArrayList<BizsysAccountInfo>();
				List<String> data = generateData(config, btStr.get(i), taskIdList, statisticList, request.getAccountDay());
				seriesUpdateOperation(taskIdList, statisticList, data, request, config.getFileEncoding());
				
				taskIdList = null; statisticList = null; data = null;
			}
		} else {
			// 业务系统对账没有对账成功的业务类型,则从配置的第一个业务类型进行数据统计
			for (String s : btStr) {
				List<Long> taskIdList = new ArrayList<Long>();
				List<BizsysAccountInfo> statisticList = new ArrayList<BizsysAccountInfo>();
				List<String> data = generateData(config, s, taskIdList, statisticList, request.getAccountDay());
				seriesUpdateOperation(taskIdList, statisticList, data, request, config.getFileEncoding());
				
				taskIdList = null; statisticList = null; data = null;
			}
		}
	}
	
	private List<String> getAllConfigedBiztype(BizsysAccountRequest request){
		String[] btStrs = request.getBizType().split(Constants.STRING_DOWN_LINE);
		List<String> needAccountBiztype = new ArrayList<String>();
		for (String str : btStrs) {
			needAccountBiztype.add(str);
		}
		return needAccountBiztype;
	}
	
	private void seriesUpdateOperation(List<Long> taskIdList, List<BizsysAccountInfo> statisticList, List<String> data, BizsysAccountRequest request, String fileEncoding) throws SQLException, IOException {
		if (statisticList != null && !statisticList.isEmpty()) {
			int size = statisticList.size();
			int batchDefinitionSize = ServerConfig.systemConfig.getBatchSize();
			if (batchDefinitionSize <= 0) {
				batchDefinitionSize = 500;
			}
			int batchSize = size / batchDefinitionSize;
			for (int i = 0; i < batchSize; i++) {
				List<Long> subBatchTaskId = taskIdList.subList(batchDefinitionSize * i, batchDefinitionSize * (i + 1));
				List<BizsysAccountInfo> subBatchAccountInfo = statisticList.subList(batchDefinitionSize * i, batchDefinitionSize * (i + 1));
				List<String> subBatchAccountInfoTempleInfo = data.subList(batchDefinitionSize * i, batchDefinitionSize * (i + 1));
				channelAccountInfoService.seriesUpdate(subBatchTaskId, subBatchAccountInfo, subBatchAccountInfoTempleInfo, request, fileEncoding);
			}
			
			List<Long> reamainTaskId = taskIdList.subList(batchDefinitionSize * batchSize,size);
			List<BizsysAccountInfo> remainAccountInfo = statisticList.subList(batchDefinitionSize * batchSize,size);
			List<String> remainAccountInfoTempleInfo =  data.subList(batchDefinitionSize * batchSize,size);
			channelAccountInfoService.seriesUpdate(reamainTaskId, remainAccountInfo, remainAccountInfoTempleInfo, request, fileEncoding);
		}
	}

	private AccountInfo getLastAccountInfoByBizsysRequest(BizsysAccountRequest request) throws SQLException{
		String bizsysRequestId = request.getReqId();
		return channelAccountInfoService.getLastAccountInfoByBizsysRequest(bizsysRequestId);
	}
	/**
	 * 生成业务系统对账的数据
	 * @param config 业务系统对账配置
	 * @param bizType 业务类型
	 * @param taskIdList 需要更新的task
	 * @param statisticList
	 * @return
	 * @throws SQLException 
	 * @throws ReflectiveOperationException 
	 */
	private List<String> generateData(BizsysAccountConfig config, String bizType, List<Long> taskIdList, List<BizsysAccountInfo> statisticList, String accountDay) throws SQLException, ReflectiveOperationException {
		List<String> strList = new ArrayList<String>();
		String attrsDefinition = config.getAttrsDefinition();
		String attrSplit = transfered(config.getAttrsSplit());
		if (StringUtils.isBlank(attrsDefinition) || StringUtils.isBlank(attrSplit)) {
			return strList;
		}
		Class<BizsysAccountInfo> cla = BizsysAccountInfo.class;
		String[] tStr = attrsDefinition.split(attrSplit);
		List<BizsysAccountInfo> temp = bizsysAccountStatisticService.getBizsysAccountStatistic(
				config.getBizSysNo(), bizType, accountDay);
		if (temp != null && temp.size() > 0) {
			for (BizsysAccountInfo statistic : temp) {
				taskIdList.add(statistic.getTaskId());
				statisticList.add(statistic);
				StringBuffer sbf = new StringBuffer();
				for (int i = 0 ; i < tStr.length; i++) {
					Field field = cla.getDeclaredField(tStr[i]);
					field.setAccessible(true);
					String value = field.get(statistic).toString();
					sbf.append(value);
					if (i !=  tStr.length - 1) {
						sbf.append(config.getAttrsSplit());
					}
				}
				if (StringUtils.isNotBlank(sbf.toString())) {
					strList.add(sbf.toString());
				}
			}
		}
		return strList; 
	}
	
	private String transfered(String split){
		if (split.equals(Constants.STRING_CA)) {
			return Constants.STRING_CA_TR;
		}
		return split;
	}
	
}

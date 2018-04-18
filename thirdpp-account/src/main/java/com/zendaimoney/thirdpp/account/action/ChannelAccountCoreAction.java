package com.zendaimoney.thirdpp.account.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.ChannelAccountChainService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountRequestService;
import com.zendaimoney.thirdpp.account.util.AccountResponse;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.account.util.Constants;

@Component(value = "com.zendaimoney.thirdpp.account.action.ChannelAccountCoreAction")
public class ChannelAccountCoreAction {

	private static final Log logger = LogFactory.getLog(ChannelAccountCoreAction.class);

	@Autowired
	private ChannelAccountRequestService channelAccountRequestService;
	@Autowired
	private ChannelAccountChainService channelAccountChainService;

	public AccountResponse process(ChannelAccountConfig config, String threadName) throws IOException {
		
		logger.info("通道对账线程【" + threadName + "】开始处理");
		
		AccountResponse response = new AccountResponse(
				AccountResponse.ACCOUNT_RESPONSE_STATUS_SUCCESS,
				AccountResponse.ACCOUNT_RESPONSE_MESSAGEID_DEFAULT);
		
		long t = compareNowToAccountTime(config);
		if (t > 0) {
			// 还没有到当日对账时间
			logger.info("通道对账未到当日对账时间," + threadName + "将休眠" + t);
			response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
			response.setMessageId(AccountResponse.CommonMessage.CONFIG_NOT_START
					.getmId());
			response.setMessageContent(AccountResponse.CommonMessage.CONFIG_NOT_START
					.getmContent());
			response.setSleepTime(t);
			return response;
		}

		ChannelAccountRequest channelAccountRequest = null;
		try {
			channelAccountRequest = channelAccountRequestService.getChannelAccountRequestByConfig(config);
		} catch (SQLException e) {
			logger.error("通道对账获取通道对账请求表异常", e);
			throw new PlatformException("通道对账获取通道对账请求表异常", e);
		}
		
		if (channelAccountRequest != null) {
			int status =  channelAccountRequest.getStatus();
			int downloadFailedTimes = channelAccountRequest.getDownloadFailedTimes();
			int configDownloadFailedMaxTimes = config.getMaxDownloadFailedTimes();
			if (downloadFailedTimes >= configDownloadFailedMaxTimes) {
				logger.info("通道对账下载失败次数大于等于配置的允许失败最大次数");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
			int insertFailedTimes = channelAccountRequest.getInsertFailedTimes();
			int maxInsertFailedTimes = config.getMaxInsertFailedTimes();
			if (insertFailedTimes >= maxInsertFailedTimes) {
				logger.info("通道对账入表失败次数大于等于配置的允许失败最大次数");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
			
			int accountFaildTimes = channelAccountRequest.getAccountFailedTimes();
			int maxAccountFailedTimes = config.getMaxAccountFailedTimes();
			if (accountFaildTimes >= maxAccountFailedTimes) {
				logger.info("通道对账对账失败次数大于等于配置的允许失败最大次数");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
			
			int backupFailedTimes = channelAccountRequest.getBackupFailedTimes();
			int maxBackupFailedTimes = config.getMaxBackupFailedTimes();
			if (backupFailedTimes >= maxBackupFailedTimes) {
				logger.info("通道对账备份失败次数大于等于配置的允许失败最大次数");
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED);
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
			
			if (Constants.ChannelAccountRequestStatus.ACCOUNT_REQUEST_STATUS_BACKUP_SUCCESS == status) {
				// 已经完成今天的对账任务，线程进入休眠状态
				response.setStatus(AccountResponse.ACCOUNT_RESPONSE_STATUS_SUCCESS);
				response.setMessageId(AccountResponse.CommonMessage.FINISHED.getmId());
				response.setMessageContent(AccountResponse.CommonMessage.FINISHED.getmContent());
				response.setSleepTime(compareNowToNextDayAccountTime(config));
				return response;
			}
		}
		
		channelAccountChainService.handle(config, channelAccountRequest);
		
		return response;
	}

	/**
	 * 当前时间和对账时间的时间差
	 * 
	 * @return 时间差 为 0 或者正直表示当前时间到达对账时间可以开始对账 为负值表示尚未到当日的对账开始时间
	 */
	private long compareNowToAccountTime(ChannelAccountConfig config) {
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
	 * 当前时间和明日对账时间的时间差
	 * 发生在今日对账已经完成的情况下
	 * @return 
	 */
	private long compareNowToNextDayAccountTime(ChannelAccountConfig config) {
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

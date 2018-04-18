package com.zendaimoney.thirdpp.account.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.account.action.ChannelAccountCoreAction;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.util.AccountResponse;

public class ChannelAccountThread implements Runnable {

	private static final Log logger = LogFactory.getLog(ChannelAccountThread.class);

	private ChannelAccountCoreAction coreAction;
	private long defaultSleepTime;
	private long errorSleepTime;
	private String threadName;
	private ChannelAccountConfig config;
	
	public ChannelAccountThread(ChannelAccountConfig config,
			ChannelAccountCoreAction action, String threadName) {
		this.coreAction = action;
		this.config = config;
		this.threadName = threadName;
		defaultSleepTime = ServerConfig.systemConfig.getDefaultSleepTime();
		errorSleepTime = ServerConfig.systemConfig.getErrorSleepTime();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		Thread.currentThread().setName(threadName);
		long sleepTime = defaultSleepTime;
		AccountResponse response = null;
		while (true) {
			try {
				response = coreAction.process(config, threadName);
				if (AccountResponse.ACCOUNT_RESPONSE_STATUS_FAILED == response.getStatus()) {
					if (response.getSleepTime() > 0) {
						sleepTime = response.getSleepTime();
					} else {
						sleepTime = errorSleepTime;
					}
				}
				
				if (AccountResponse.ACCOUNT_RESPONSE_STATUS_SUCCESS == response.getStatus()) {
					if (response.getSleepTime() > 0) {
						logger.info("今日通道对账任务完成， " + threadName + " 将开始休眠");
						sleepTime = response.getSleepTime();
					} else {
						sleepTime = defaultSleepTime;
					}
				}
			} catch (Exception e) {
				logger.error("通道对账业务异常", e);
				sleepTime = errorSleepTime;
			} finally {
				try {
					logger.info(threadName + " 将开始休眠  " + sleepTime);
					Thread.currentThread().sleep(sleepTime);
					logger.info(threadName + " 休眠结束 ");
				} catch (InterruptedException e) {
					logger.error("通道对账业务休眠线程异常", e);
				}
			}
		}

	}

}

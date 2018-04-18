package com.zendaimoney.thirdpp.account.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.account.action.BizsysAccountCoreAction;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.util.AccountResponse;

public class BizsysAccountThread implements Runnable {

	private static final Log logger = LogFactory.getLog(BizsysAccountThread.class);
	
	private  BizsysAccountCoreAction coreAction;
	private long defaultSleepTime;
	private long errorSleepTime;
	private String threadName;
	private BizsysAccountConfig config;
	
	public BizsysAccountThread(BizsysAccountConfig config, BizsysAccountCoreAction coreAction, String threadName) {
		this.coreAction = coreAction;
		this.threadName = threadName;
		this.config = config;
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
						sleepTime = response.getSleepTime();
						logger.info("今日业务对账任务完成， " + threadName + " 将开始休眠  " + sleepTime);
					} else {
						sleepTime = defaultSleepTime;
					}
				}
			} catch (Exception e) {
				logger.error("业务对账业务异常", e);
				sleepTime = errorSleepTime;
			} finally {
				try {
					logger.info(threadName + " 将开始休眠  " + sleepTime);
					Thread.currentThread().sleep(sleepTime);
					logger.info(threadName + " 休眠结束 ");
				} catch (InterruptedException e) {
					logger.error("业务对账业务休眠线程异常", e);
				}
			}
		}
	}

}

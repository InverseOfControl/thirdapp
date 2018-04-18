package com.zendaimoney.thirdpp.notify.consumer;

import com.zendaimoney.thirdpp.notify.conf.ServerConfig;
import com.zendaimoney.thirdpp.notify.service.IMergeService;
import com.zendaimoney.thirdpp.notify.util.LogPrn;

/**
 * 查询待合单的线程
 * 
 * @author 00225642
 * 
 */
public class QueryThread implements Runnable {

	private static final LogPrn logger = new LogPrn(QueryThread.class);

	private IMergeService mergeService;

	private long QUERY_WAITING_SLEEP_TIME = ServerConfig.systemConfig
			.getQueryWaitingSleepTime();

	public QueryThread(IMergeService mergeService) {
		this.mergeService = mergeService;
	}

	@Override
	public void run() {
		while (true) {
			try {
				mergeService.doWaitingMerge();
			} catch (Exception e) {
				logger.error(e);
			} finally {
				try {
					Thread.sleep(QUERY_WAITING_SLEEP_TIME);
				} catch (InterruptedException e) {
					logger.error(e);
				}
			}
		}
	}

}

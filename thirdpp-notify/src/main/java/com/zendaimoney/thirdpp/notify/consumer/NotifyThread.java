package com.zendaimoney.thirdpp.notify.consumer;

import java.sql.SQLException;
import java.util.List;

import com.zendaimoney.thirdpp.notify.conf.ServerConfig;
import com.zendaimoney.thirdpp.notify.entity.TradeNotify;
import com.zendaimoney.thirdpp.notify.service.INotifyService;
import com.zendaimoney.thirdpp.notify.util.LogPrn;

public class NotifyThread implements Runnable {

	private static final LogPrn logger = new LogPrn(NotifyThread.class);

	private INotifyService notifyService;
	
	// 运营方式(0线下运营1线上运营)
	private String opMode;

	private long NOTIFY_SLEEP_TIME = ServerConfig.systemConfig.getNotifySleepTime();

	public NotifyThread(INotifyService notifyService, String opMode) {
		this.notifyService = notifyService;
		this.opMode = opMode;
	}

	@Override
	public void run() {
		while (true) {
			try {
				List<TradeNotify> notifyList = notifyService.queryByAppName(opMode);
				if (null == notifyList || notifyList.size() == 0) {
					logger.debug("【暂无推送记录】");
					continue;
				}
				notifyService.notifyBusiSys(notifyList);
			} catch (SQLException e) {
				logger.error("【数据库异常】" + e.getMessage());
			} catch (Exception e) {
				e.getMessage();
				logger.error("【系统异常】" + e.getMessage());
			} finally {
				try {
					Thread.sleep(NOTIFY_SLEEP_TIME);
				} catch (InterruptedException e) {
					logger.error("【线程运行异常】" + e.getMessage());
				}
			}
		}
	}

}

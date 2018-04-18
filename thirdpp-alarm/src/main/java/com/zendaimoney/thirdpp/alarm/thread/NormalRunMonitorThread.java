package com.zendaimoney.thirdpp.alarm.thread;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.alarm.common.Monitor;
import com.zendaimoney.thirdpp.alarm.sms.SMSSend;
import com.zendaimoney.thirdpp.alarm.util.CsswebConfig;
import com.zendaimoney.thirdpp.alarm.util.DateTimeUtil;
import com.zendaimoney.thirdpp.alarm.util.Sender;

public class NormalRunMonitorThread implements Runnable {

	public static Log logger = LogFactory.getLog(NormalRunMonitorThread.class);
	private SMSSend dataSend;
	private Monitor monitor = null;
	private long sleepTime = 600000;
	Sender sender = null;

	public NormalRunMonitorThread(SMSSend dataSend) {
		this.dataSend = dataSend;
	}

	@Override
	public void run() {
		while (true) {
			monitor = new Monitor();
			monitor.setId(0L);
			monitor.setCreateTime(new Date());
			monitor.setMobiles(CsswebConfig.alarmMobiles);
			monitor.setNote("告警短信发送程序运行正常,应用名=" + CsswebConfig.appName);
			sender = dataSend.newSenders(monitor);
			if (sender != null) {
				dataSend.send(sender);
			}
			sleepTime = DateTimeUtil.getSleepTime(7);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}

	}

}

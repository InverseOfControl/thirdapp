package com.zendaimoney.thirdpp.alarm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

import com.zendaimoney.ice.uc.client.service.IDataSendService;
import com.zendaimoney.ice.uc.client.service.impl.LongSendService;
import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;
import com.zendaimoney.thirdpp.alarm.sms.SMSQueryThread;
import com.zendaimoney.thirdpp.alarm.sms.SMSSend;
import com.zendaimoney.thirdpp.alarm.thread.NormalRunMonitorThread;
import com.zendaimoney.thirdpp.alarm.util.CsswebConfig;
import com.zendaimoney.thirdpp.alarm.util.LoadConfig;
import com.zendaimoney.thirdpp.alarm.util.ServerConfig;

public class ServerStartup {
	public static Log logger = LogFactory.getLog(ServerStartup.class);
	private SMSSend dataSend = null;
	private ServerConfig serverConfig;
	public static Integer errorNum = 0;
	
	public ServerStartup(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 加载指定log4j文件
		PropertyConfigurator.configure(Thread.currentThread()
				.getContextClassLoader().getResource("conf/log4j.properties")
				.getPath());
		try {
			String configPath = LoadConfig.getConfigFilePath(args);
			ServerConfig serverConfig = new ServerConfig();
			serverConfig.loadFromFile(configPath);
			// 启动服务
			ServerStartup startup = new ServerStartup(serverConfig);
			startup.start();
			logger.info("发送服务启动正常");
		} catch (Exception e) {
			logger.error("发送服务启动失败", e);
			System.exit(1);
		}
	}

	public void start() {
		logger.info("应用启动开始");
		// 加载spring配置
		CsswebConfig.setApplicationContext(new ClassPathXmlApplicationContext(
				"conf/applicationContext.xml"));
		logger.info("applicationContext.xml加载成功");
		CsswebConfig.setAppName(serverConfig.getAppName());
		CsswebConfig.setAlarmMobiles(serverConfig.getAlarmMobiles());
		CsswebConfig.setAlarmMails(serverConfig.getAlarmMails());
		CsswebConfig.setSleepTime(serverConfig.getSleepTime());
		CsswebConfig.setErrorSleepTime(serverConfig.getErrorSleepTime());
		CsswebConfig.setMailConfig(serverConfig.getMailConfig());
		CsswebConfig.setTytxServerConfig(serverConfig.getTytxServerConfig());
		if (serverConfig.getName().equals("alarm")) {
			dataSend = new SMSSend();
			// 系统正常运行监控
			Thread normalRunMonitorThread = new Thread(
					new NormalRunMonitorThread(dataSend));
			normalRunMonitorThread.start();
			//启动查询线程
			Thread smsQueryThread = new Thread(new SMSQueryThread());
			smsQueryThread.start();
		}
		logger.info("应用启动结束");
	}

}
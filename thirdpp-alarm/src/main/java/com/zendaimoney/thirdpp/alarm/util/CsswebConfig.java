package com.zendaimoney.thirdpp.alarm.util;

import org.springframework.context.ApplicationContext;

public class CsswebConfig {

	private static ApplicationContext applicationContext;

	public static MailConfig mailConfig;
	public static String appName;
	public static String alarmMobiles;
	public static String alarmMails;
	public static Integer sleepTime;
	public static Integer errorSleepTime;

	public static TytxServerConfig tytxServerConfig;

	private CsswebConfig() {
	}

	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		CsswebConfig.applicationContext = applicationContext;
	}

	public static void setMailConfig(MailConfig mailConfig) {
		CsswebConfig.mailConfig = mailConfig;
	}

	public static void setAlarmMobiles(String alarmMobiles) {
		CsswebConfig.alarmMobiles = alarmMobiles;
	}

	public static void setAlarmMails(String alarmMails) {
		CsswebConfig.alarmMails = alarmMails;
	}

	public static void setAppName(String appName) {
		CsswebConfig.appName = appName;
	}

	public static void setTytxServerConfig(TytxServerConfig tytxServerConfig) {
		CsswebConfig.tytxServerConfig = tytxServerConfig;
	}

	public static void setSleepTime(Integer sleepTime) {
		CsswebConfig.sleepTime = sleepTime;
	}

	public static void setErrorSleepTime(Integer errorSleepTime) {
		CsswebConfig.errorSleepTime = errorSleepTime;
	}

}
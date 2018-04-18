package com.zendaimoney.thirdpp.notify.conf;

import org.springframework.context.ApplicationContext;

public class ServerConfig {

	private static ApplicationContext applicationContext;

	/**
	 * 系统配置
	 */
	public static SystemConfig systemConfig;


	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}


	public static void setSystemConfig(SystemConfig systemConfig) {
		ServerConfig.systemConfig = systemConfig;
	}

	public static void setApplicationContext(
			ApplicationContext applicationContext) {
		ServerConfig.applicationContext = applicationContext;
	}
	

}

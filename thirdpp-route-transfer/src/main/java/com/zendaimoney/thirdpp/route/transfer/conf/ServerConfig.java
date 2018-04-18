package com.zendaimoney.thirdpp.route.transfer.conf;


import org.springframework.context.ApplicationContext;

import com.zendaimoney.thirdpp.route.transfer.action.biz.CollectTransferAction;

public class ServerConfig {

	private static ApplicationContext applicationContext;

	/**
	 * 系统配置
	 */
	public static SystemConfig systemConfig;

	public static Object getBean() {
		return applicationContext.getBean(CollectTransferAction.class);
	}

	public static void setSystemConfig(SystemConfig systemConfig) {
		ServerConfig.systemConfig = systemConfig;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ServerConfig.applicationContext = applicationContext;
	}

}

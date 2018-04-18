package com.zendaimoney.thirdpp.query.conf;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;

public class ServerConfig {

	public static ApplicationContext applicationContext;

	/**
	 * 系统配置
	 */
	public static SystemConfig systemConfig;
	
	public static TransferConfig transferConfig;
	
	/**
	 * 通道集合
	 */
	public static HashMap<String, ChannelConfig> channelMap = new HashMap<String, ChannelConfig>();

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


	public static void setChannelMap(HashMap<String, ChannelConfig> channelMap) {
		ServerConfig.channelMap = channelMap;
	}
	
	public static void setTransferConfig(TransferConfig transferConfig) {
		ServerConfig.transferConfig = transferConfig;
	}
}

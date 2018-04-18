package com.zendaimoney.thirdpp.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zendaimoney.thirdpp.notify.conf.ServerConfig;
import com.zendaimoney.thirdpp.notify.util.ConfigUtil;

/**
 * 系统启动入口
 * 
 * @author 00231257
 * 
 */
public class ServerStartup {

	// 日志工具类
	public static Log logger = LogFactory.getLog(ServerStartup.class);

	/**
	 * 程序入口.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// 加载指定log4j文件
		PropertyConfigurator.configure(Thread.currentThread()
				.getContextClassLoader().getResource("conf/log4j.properties")
				.getPath());
		try {
			// 加载系统配置文件
			// 获取系统配置文件路径(格式 -f ../conf/)
			String configPath = ConfigUtil.getConfigFilePath(args);
			ConfigUtil configUtil = new ConfigUtil();
			configUtil.loadFromFile(configPath);
			// 初始化系统配置
			ServerConfig.setSystemConfig(configUtil.getSystemConfig());
			
			// 加载spring配置
			ServerConfig
					.setApplicationContext(new ClassPathXmlApplicationContext(
							new String[] { "spring/applicationContext.xml" }));
			logger.info("applicationContext.xml加载成功");

			// 启动服务
			ServerStartup startup = new ServerStartup();
			startup.start(); 

		} catch (Exception e) {
			logger.error("===服务启动失败", e);
			System.exit(1);
		}

	}

	/**
	 * 服务器启动
	 */
	public void start() {
		logger.info("=====服务启动正常");
	}

}

package com.zendaimoney.thirdpp.query;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;
import com.zendaimoney.thirdpp.query.mq.producter.AmqpProductService;
import com.zendaimoney.thirdpp.query.thread.QueryDataMoveDealThread;
import com.zendaimoney.thirdpp.query.thread.QueryThread;
import com.zendaimoney.thirdpp.query.thread.TransferThread;
import com.zendaimoney.thirdpp.query.util.ConfigUtil;
import com.zendaimoney.thirdpp.query.util.QueryActionUtil;

/**
 * 系统启动入口
 * 
 * @author 00231257
 *
 */
public class ServerStartup {

	// 日志工具类
	public static Log logger = LogFactory.getLog(ServerStartup.class);
	
	public static int size = 1;
	// 线程池
	private ExecutorService pools;
	
	private static Map<String, CollectionConfig> collectionMap;

	public ServerStartup() {

		// 初始化线程池
		pools = Executors.newCachedThreadPool();
	}

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
			
			ServerConfig.setTransferConfig(configUtil.getTransferConfig());
			logger.info("配置文件读取成功");
			
			// 加载spring配置
			ServerConfig
					.setApplicationContext(new ClassPathXmlApplicationContext(
							new String[] { "spring/applicationContext.xml"}));
			logger.info("applicationContext.xml加载成功");

			// 启动服务
			ServerStartup startup = new ServerStartup();
			startup.start();
			logger.info("=====服务启动正常");
		} catch (Exception e) {
			logger.error("===服务启动失败", e);
			System.exit(1);
		}

	}

	/**
	 * 服务器启动
	 * @throws SQLException 
	 */
	public void start() throws SQLException {
		
		// 处理Action实例化加载
		QueryActionUtil actionUtil = new QueryActionUtil();
		actionUtil.init();
		
		// 启动备份数据的线程
		Runnable backupThread = new QueryDataMoveDealThread(MongoConfig.mongoConfig.getCollectionName());
		pools.execute(backupThread);
		
		// 启动转移数据的线程
		if (ServerConfig.transferConfig != null) {
			
			Runnable transferThread = new TransferThread();
			pools.execute(transferThread);
		}
		
		// MQ消息处理模板
		AmqpProductService amqpService = new AmqpProductService();
		// 获取业务类型与业务处理action集合对应关系
		collectionMap = MongoConfig.getCollectionMap();
		// 启动业务运行线程
		for (String key : collectionMap.keySet()) {
			
			// 初始化每个
			Runnable thread = new QueryThread(ServerConfig.systemConfig.getAppName(), collectionMap.get(key), amqpService);
			pools.execute(thread);
			logger.info(key);
		}
			

	}

}

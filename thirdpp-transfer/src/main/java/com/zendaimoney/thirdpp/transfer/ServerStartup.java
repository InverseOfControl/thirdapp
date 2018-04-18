package com.zendaimoney.thirdpp.transfer;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zendaimoney.thirdpp.transfer.action.Action;
import com.zendaimoney.thirdpp.transfer.action.TransferActionTarget;
import com.zendaimoney.thirdpp.transfer.action.TransferActionThread;
import com.zendaimoney.thirdpp.transfer.conf.ChannelConfig;
import com.zendaimoney.thirdpp.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.transfer.util.ConfigUtil;
import com.zendaimoney.thirdpp.transfer.util.TransferActionUtil;

/**
 * 系统启动入口
 * 
 * @author 00231257
 * 
 */
public class ServerStartup {

	// 日志工具类
	public static Log logger = LogFactory.getLog(ServerStartup.class);

	private static Map<String, TransferActionTarget> transferActionTargetMap;

	// 线程池
	private ExecutorService pools = null;

	public ServerStartup() {
		// 获取业务类型与业务处理action集合对应关系
		transferActionTargetMap = TransferActionUtil.getTransferActionTarget();
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
			// 初始化通道集合
			ServerConfig.setChannelMap(configUtil.getChannelMap());
			
			logger.info("配置文件读取成功");
			// 加载spring配置
			ServerConfig
					.setApplicationContext(new ClassPathXmlApplicationContext(
							new String[] { "spring/applicationContext.xml" }));
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
	 */
	public void start() {

		String errorMsg;
		// 启动线程数
		int numThread = 0;
		// 转化action线程
		TransferActionThread transferActionThread = null;
		// 获取该业务所对应业务处理action
		TransferActionTarget target = transferActionTargetMap
				.get(ServerConfig.systemConfig.getBizType().getCode());
		if (target == null) {
			errorMsg = " match action error. action is not found. bizType : "
					+ ServerConfig.systemConfig.getBizType().getCode();
			logger.error(errorMsg);
			return;
		}
		Class<? extends Action> clazz = target.getActionClazz();
		Action action = null;
		try {
			action = (Action) ServerConfig.getBean(clazz.getName());
		} catch (Exception e) {
			errorMsg = "instance action error : " + clazz.getName();
			logger.error(errorMsg, e);
			return;
		}
		// 启动业务运行线程
		for (Map.Entry<String, ChannelConfig> entry : ServerConfig.channelMap
				.entrySet()) {
			// 获取每个通道下规定的启动线程数.
			numThread = entry.getValue().getNumThread();
			for (int i = 0; i < numThread; i++) {
				transferActionThread = new TransferActionThread(action,
						entry.getKey(), ServerConfig.systemConfig.getAppName() + "_" + entry.getKey() + "_" + i);
				pools.execute(transferActionThread);
				logger.info("线程" + ServerConfig.systemConfig.getAppName() + "- 通道"
						+ entry.getKey() + "_" + i + "创建成功");
			}
		}

	}

}

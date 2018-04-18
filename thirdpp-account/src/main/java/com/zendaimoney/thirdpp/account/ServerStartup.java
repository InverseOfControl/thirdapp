package com.zendaimoney.thirdpp.account;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zendaimoney.thirdpp.account.action.BizsysAccountCoreAction;
import com.zendaimoney.thirdpp.account.action.ChannelAccountCoreAction;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.exception.PlatformException;
import com.zendaimoney.thirdpp.account.service.BizsysAccountConfigService;
import com.zendaimoney.thirdpp.account.service.ChannelAccountConfigService;
import com.zendaimoney.thirdpp.account.thread.BizsysAccountThread;
import com.zendaimoney.thirdpp.account.thread.ChannelAccountThread;
import com.zendaimoney.thirdpp.account.util.ConfigUtil;
import com.zendaimoney.thirdpp.account.util.Constants;

public class ServerStartup {

	public static final Log logger = LogFactory.getLog(ServerStartup.class);

	private static ExecutorService pools = Executors.newCachedThreadPool();

	public static void main(String[] args) {
		PropertyConfigurator.configure(Thread.currentThread()
				.getContextClassLoader().getResource("conf/log4j.properties")
				.getPath());

		try {
			String configFilePath = ConfigUtil.getConfigFilePath(args);
			ConfigUtil configUtil = new ConfigUtil();
			configUtil.loadConfigFile(configFilePath);

			ServerConfig.setSystemConfig(configUtil.getSystemConfig());
			ServerConfig.setBizsysAccountPropertyConfig(configUtil
					.getBizsysAccountPropertiesConfig());
			ServerConfig.setChannelAccountPropertyConfig(configUtil
					.getChannelAccountPropertiesConfig());

			logger.info("加载自定义配置文件完成");
			ServerConfig
					.setApplicationContext(new ClassPathXmlApplicationContext(
							new String[] { "spring/applicationContext.xml" }));
			logger.info("加载spring配置文件完成");

			ServerStartup serverStartup = new ServerStartup();
			serverStartup.start();

			logger.info("对账服务正常启动");
		} catch (Exception e) {
			logger.error("对账服务启动失败", e);
			System.exit(1);
		}
	}

	private void start() {

		ChannelAccountConfigService channelAccountConfigService = (ChannelAccountConfigService) ServerConfig
				.getBean(ChannelAccountConfigService.class.getName());
		List<ChannelAccountConfig> channelAccountConfigList = null;
		try {
			channelAccountConfigList = channelAccountConfigService
					.getAvailableChannelAccountConfig();
		} catch (SQLException e) {
			logger.error("获得通道对账配置项失败", e);
			throw new PlatformException("获得通道对账配置项失败", e);
		}

		ChannelAccountCoreAction channelCoreAction = (ChannelAccountCoreAction) ServerConfig.getBean(ChannelAccountCoreAction.class.getName());

		if (channelAccountConfigList != null && channelAccountConfigList.size() > 0) {
			for (ChannelAccountConfig config : channelAccountConfigList) {
				if (config == null)
					continue;
				String threadName = initialChannelThreadName(config);
				ChannelAccountThread thread = new ChannelAccountThread(
						config, channelCoreAction, threadName);
				pools.execute(thread);
				logger.info("通道对账线程 【" + threadName + "】创建成功");
			}
		}
		
		BizsysAccountConfigService bizsysAccountConfigService = (BizsysAccountConfigService)ServerConfig.getBean(BizsysAccountConfigService.class.getName());
		List<BizsysAccountConfig> bizAccountConfigList = null;
		
		try {
			bizAccountConfigList = bizsysAccountConfigService.getAvaiableBizsysAccountConfig();
		} catch (SQLException e) {
			logger.error("获得业务系统对账配置项失败", e);
			throw new PlatformException("获得业务系统对账配置项失败", e);
		}
		
		BizsysAccountCoreAction bizsysCoreAction = (BizsysAccountCoreAction) ServerConfig.getBean(BizsysAccountCoreAction.class.getName());
		if (bizAccountConfigList != null && bizAccountConfigList.size() > 0) {
			for (BizsysAccountConfig config : bizAccountConfigList) {
				if (config == null)
					continue;
				String threadName = initialBizsysThreadName(config);
				BizsysAccountThread thread = new BizsysAccountThread(
						config, bizsysCoreAction, threadName);
				pools.execute(thread);
				logger.info("业务对账线程 【" + threadName + "】创建成功");
			}
		}
		
		// 线程挂起，定时休眠
		if ((channelAccountConfigList == null || channelAccountConfigList.size() == 0) && (bizAccountConfigList == null || bizAccountConfigList.size() == 0)) {
			while (true) { 
				try {
					logger.info("无通道对账配置，无业务系统对账配置, 线程 【" + Thread.currentThread().getName() + "】开始休眠");
					Thread.sleep(ServerConfig.systemConfig.getErrorSleepTime());
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	public String initialChannelThreadName(ChannelAccountConfig config) {
		long configId = config.getId();
		String channelName = config.getChannelName();
		String merchantNo = config.getMerchantNo();
		String bizType = config.getBizType();
		return ServerConfig.systemConfig.getAppName()
				.concat(Constants.STRING_DOWN_LINE).concat(String.valueOf(configId))
				.concat(Constants.STRING_DOWN_LINE).concat(channelName)
				.concat(Constants.STRING_DOWN_LINE).concat(merchantNo)
				.concat(Constants.STRING_DOWN_LINE).concat(bizType);
	}
	
	public String initialBizsysThreadName(BizsysAccountConfig config) {
		long configId = config.getId();
		String bizsysNo = config.getBizSysNo();
		String bizType = config.getBizType();
		return ServerConfig.systemConfig.getAppName()
				.concat(Constants.STRING_DOWN_LINE).concat(String.valueOf(configId))
				.concat(Constants.STRING_DOWN_LINE).concat(bizsysNo)
				.concat(Constants.STRING_DOWN_LINE).concat(bizType);
	}
}

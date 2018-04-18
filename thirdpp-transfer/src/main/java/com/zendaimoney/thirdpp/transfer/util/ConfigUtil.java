package com.zendaimoney.thirdpp.transfer.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.transfer.conf.ChannelConfig;
import com.zendaimoney.thirdpp.transfer.conf.SystemConfig;
import com.zendaimoney.thirdpp.transfer.exception.PlatformException;

public class ConfigUtil implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;

	private static final LogPrn log = new LogPrn(ConfigUtil.class);

	/**
	 * 通道集合
	 */
	public static HashMap<String, ChannelConfig> channelMap = new HashMap<String, ChannelConfig>();

	private SystemConfig systemConfig;

	/**
	 * 加载指定路径下配置文件
	 * 
	 * @param path
	 */
	public void loadFromFile(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				log.error("File " + path + " is not exists");
				throw new PlatformException("File " + path + " is not exists");
			}
			Ini conf = new Ini(file);
			this.populateAttributes(conf);
			log.info("Load file " + path + " sucessful");
		} catch (IOException e) {
			log.error("Parse configuration failed,path=" + path);
			throw new PlatformException("Parse configuration failed,path="
					+ path, e);
		} catch (Exception e) {
			log.error("Parse configuration failed,path=" + path);
			throw new PlatformException("Parse configuration failed,path="
					+ path, e);
		}

	}

	private void populateAttributes(Ini conf) {
		this.populateSystemConf(conf);
		this.populateAllinpayConf(conf); //通联1
		this.populateAllinpayV2Conf(conf); //通联2
		
		
		this.populateSHUnionpayConf(conf); // 银联线程配置
		this.populateFuioupayConf(conf); // 富友线程配置
		
		this.populateYyoupayConf(conf);
		this.populateZendaipayConf(conf);
		
		this.populateKjtpayConf(conf);
		this.populateBaofoopayConf(conf);

		this.populateUnspayConf(conf);
	}

	private void populateSystemConf(Ini conf) {
		Section section = conf.get("system");
		if (section != null) {
			systemConfig = new SystemConfig();
			systemConfig.setAlarmEmails(section.get("alarmEmails"));
			systemConfig.setAlarmMobiles(section.get("alarmMobiles"));
			systemConfig.setAppName(section.get("appName"));
			systemConfig.setBizType(BizType.get(section.get("bizType")));
			systemConfig.setErrorSleepTime(Long.valueOf(section
					.get("errorSleepTime")));
			systemConfig.setMaxWarnNum(Integer.valueOf(section
					.get("maxWarnNum")));
			systemConfig.setNotEmptySleepTime(Integer.valueOf(section
					.get("notEmptySleepTime")));
			systemConfig.setSleepTime(Long.valueOf(section.get("sleepTime")));
			systemConfig.setMaxSendNum(Integer.valueOf(section
					.get("maxSendNum")));
			systemConfig.setTaskSplitNum(Integer.valueOf(section
					.get("taskSplitNum")));
			systemConfig.setMerge_online_key(section.get("merge_online_key")); // 线上合并队列key
			systemConfig.setMerge_offline_key(section.get("merge_offline_key")); // 先下合并队列key
			
			// 设置全局变量
			systemConfig.setGlobalConfigPath(System.getenv("TPPGLOBALCONFIGPATH"));
		}
	}

	private void populateAllinpayConf(Ini conf) {
		Section section = conf.get("allinpay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}
	
	private void populateAllinpayV2Conf(Ini conf) {
		Section section = conf.get("allinpay2");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}
	
	private void populateSHUnionpayConf(Ini conf) {
		Section section = conf.get("shunionpay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}
	
	private void populateFuioupayConf(Ini conf) {
		Section section = conf.get("fuioupay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}
	
	private void populateYyoupayConf(Ini conf) {
		Section section = conf.get("yyoupay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}
	
	private void populateZendaipayConf(Ini conf) {
		Section section = conf.get("zendaipay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}
	
	private void populateKjtpayConf(Ini conf) {
		Section section = conf.get("kjtpay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}

	private void populateBaofoopayConf(Ini conf) {
		Section section = conf.get("baofoopay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}

	private void populateUnspayConf(Ini conf) {
		Section section = conf.get("unspay");
		ChannelConfig channelConfig = null;
		if (section != null) {
			channelConfig = new ChannelConfig();
			channelConfig
					.setNumThread(Integer.valueOf(section.get("numThread")));
			channelConfig.setThirdType(ThirdType.get(section.get("thirdType")));
			channelMap.put(section.get("thirdType"), channelConfig);
		}

	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public HashMap<String, ChannelConfig> getChannelMap() {
		return channelMap;
	}

	/**
	 * 根据main参数获取文件路径(格式: -f 路径)
	 * 
	 * @param args
	 * @return
	 */
	public static String getConfigFilePath(String[] args) {
		Options options = new Options();
		Option file = new Option("f", true, "Configuration file path");
		options.addOption(file);
		CommandLineParser parser = new PosixParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			throw new PlatformException("Parse command line failed", e);
		}
		String configFilePath = null;
		if (line.hasOption("f")) {
			configFilePath = line.getOptionValue("f");
		} else {
			System.err
					.println("Please tell me the configuration file path by -f option");
			System.exit(1);
		}
		if (StringUtils.isBlank(configFilePath)) {
			throw new PlatformException("Blank file path");
		}
		return configFilePath;
	}

	public static void main(String args[]) {
	}

}
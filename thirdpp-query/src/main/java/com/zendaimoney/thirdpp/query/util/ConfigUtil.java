package com.zendaimoney.thirdpp.query.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.zendaimoney.thirdpp.query.conf.BackUpConfig;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.SystemConfig;
import com.zendaimoney.thirdpp.query.conf.TransferConfig;
import com.zendaimoney.thirdpp.query.exception.PlatformException;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;

public class ConfigUtil implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;

	private static final LogPrn log = new LogPrn(ConfigUtil.class);

	private SystemConfig systemConfig;
	
	private TransferConfig transferConfig;
	
	/**
	 * mongo系统配置
	 */
	private MongoConfig mongoConfig;
	
	private BackUpConfig backUpConfig;
	
	private static ConfigUtil single = null;
	
	// 单例.
	public static ConfigUtil getInstance() {
		if (single == null) {
			single = new ConfigUtil();
		}
		return single;
	}
	

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
		this.populateSystemConf(conf); // system配置
		this.populateMongoDBConf(conf); // mongo配置
		
		this.populateBackUpConf(conf); // backup配置
		
		this.populateTransferConf(conf); // 数据转移配置
	}

	/**
	 * 系统配置信息
	 * @param conf
	 */
	private void populateSystemConf(Ini conf) {
		Section section = conf.get("system");
		if (section != null) {
			systemConfig = new SystemConfig();
			systemConfig.setAlarmEmails(section.get("alarmEmails"));
			systemConfig.setAlarmMobiles(section.get("alarmMobiles"));
			systemConfig.setAppName(section.get("appName"));
			systemConfig.setErrorSleepTime(Long.valueOf(section
					.get("errorSleepTime")));
			systemConfig.setMaxWarnNum(Integer.valueOf(section
					.get("maxWarnNum")));
			systemConfig.setNotEmptySleepTime(Integer.valueOf(section
					.get("notEmptySleepTime")));
			
			systemConfig.setWaitingSize(Integer.valueOf(section.get("waitingSize")));
			
			systemConfig.setMerge_online_key(section.get("merge_online_key")); // 线上合并队列key
			systemConfig.setMerge_offline_key(section.get("merge_offline_key")); // 先下合并队列key
			
			// 设置全局变量
			log.info("TPPGLOBALCONFIGPATH" + System.getenv("TPPGLOBALCONFIGPATH"));
			systemConfig.setGlobalConfigPath(System.getenv("TPPGLOBALCONFIGPATH"));
			
		}
	}
	
	/**
	 * 系统配置信息
	 * @param conf
	 */
	private void populateTransferConf(Ini conf) {
		Section section = conf.get("transfer");
		if (section != null) {
			transferConfig = new TransferConfig();
			
			transferConfig.setMaxResideNum(Integer.valueOf(section.get("maxResideNum"))); // 最大驻留量
			transferConfig.setSleepTime(Long.valueOf(section.get("sleepTime")));
		}
	}


	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
	
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}
	
	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	public BackUpConfig getBackUpConfig() {
		return backUpConfig;
	}
	
	public void setBackUpConfig(BackUpConfig backUpConfig) {
		this.backUpConfig = backUpConfig;
	}
	
	public TransferConfig getTransferConfig() {
		return transferConfig;
	}
	
	public void setTransferConfig(TransferConfig transferConfig) {
		this.transferConfig = transferConfig;
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
	
	/**
	 * mongo配置信息
	 * @param conf
	 */
	private void populateMongoDBConf(Ini conf) {
		
		for (String key: conf.keySet()) {
			
			if ("mongodb".equals(key)) {
				Section section = conf.get("mongodb");
				if (section != null) {
					mongoConfig = new MongoConfig();
					mongoConfig.setCollectionName(section.get("collectionName")); // 周围系统插入的mongo 集合名称
					mongoConfig.setMoveSleepTime(Long.valueOf(section.get("moveSleepTime"))); // 转移休眠时间
					mongoConfig.setMoveSize(Integer.valueOf(section.get("moveSize"))); // 批量转移处理条数
					
					MongoConfig.setMongoConfig(mongoConfig);
				}
			} else {
				Section section = conf.get(key);
				if (key.contains("UNKNOW") && section != null) {
					CollectionConfig collectionConfig = new CollectionConfig();
					collectionConfig.setCode(section.get("code").trim()); // 索引
					collectionConfig.setCollectionName(section.get("collectionName").trim());// 集合名称
					collectionConfig.setSleepTime(Long.valueOf(StringUtils.isEmpty(section.get("sleepTime").trim()) ? "0" : section.get("sleepTime").trim()));// 停滞时间
					collectionConfig.setNextCollection(section.get("nextCollection").trim()); // 对应下一阶段的集合名称
					collectionConfig.setResideTime(Long.valueOf(StringUtils.isEmpty(section.get("resideTime").trim()) ? "0" : section.get("resideTime").trim())); // 驻留时间
					collectionConfig.setDealSize(Integer.valueOf(StringUtils.isEmpty(section.get("dealSize").trim()) ? "0" : section.get("dealSize").trim())); // 批量处理量
					collectionConfig.setOpMode(section.get("opMode").trim()); // 业务模式(0：线下；1：线上；2：资金托管)
					
					if (section.containsKey("maxQueryNum")) {
						
						collectionConfig.setMaxQueryNum(Integer.valueOf(StringUtils.isEmpty(section.get("maxQueryNum").trim()) ? "1" : section.get("maxQueryNum").trim())); // 最大查询次数
					}
					
					MongoConfig.collectionMap.put(key, collectionConfig);
				}
			}
		}

	}
	
	/**
	 * 备份配置信息
	 * @param conf
	 */
	private void populateBackUpConf(Ini conf) {
		Section section = conf.get("backup");
		if (section != null) {
			backUpConfig = new BackUpConfig();
			backUpConfig.setBackupPath(section.get("backupPath"));
			backUpConfig.setBackSize(Integer.valueOf(section.get("backSize")));
			backUpConfig.setBeforeDayNum(Integer.valueOf(section.get("beforeDayNum"))); // 备份N天前的数据
			
			BackUpConfig.setBackUpConfig(backUpConfig);
		}
	}
	
	public static void main(String args[]) {
	}

}
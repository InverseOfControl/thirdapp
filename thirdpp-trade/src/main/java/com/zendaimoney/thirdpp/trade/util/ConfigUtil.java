package com.zendaimoney.thirdpp.trade.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

import com.zendaimoney.thirdpp.trade.conf.SystemConfig;
import com.zendaimoney.thirdpp.trade.conf.allinpay.AllinpayConfig;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.trade.exception.PlatformException;

public class ConfigUtil implements Serializable {

	private static final long serialVersionUID = 8433608222120950801L;

	private static final LogPrn log = new LogPrn(ConfigUtil.class);

	private AllinpayConfig allinpayConfig;

	private SystemConfig systemConfig;

	private static ConfigUtil single = null;

	public ConfigUtil() {
		// 加载thirdpp.properties配置文件
		loadThirdppFile();
	}

	public static ConfigUtil getInstance() {
		if (single == null) {
			single = new ConfigUtil();
		}
		return single;
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
	 * 加载thirdpp.properties配置文件.
	 */
	public void loadThirdppFile() throws PlatformException {
		
		// 读取配置文件路径
		String path = "";
		try {
			if (StringUtils.isEmpty(System.getenv("TPPGLOBALCONFIGPATH"))) {
				
				path = Thread.currentThread().getContextClassLoader().getResource("conf/thirdpp-trade.properties").getPath();
			} else {
				path = System.getenv("TPPGLOBALCONFIGPATH") + File.separator + "thirdpp-trade.properties";
			}
			File thirdFile = new File(path);
			log.info("thirdpp-trade: " + path);
			Ini conf = new Ini(thirdFile);
			this.populateAttributes(conf);
			log.info("LoadThirdppFile sucessful");
		} catch (IOException e) {
			log.error("Parse thirdpp configuration failed,path=" + path, e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		} catch (PlatformException e) {
			throw e;
		} catch (Exception e) {
			log.error("Parse thirdpp configuration failed,path=" + path, e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}

	}

	private void populateAttributes(Ini conf) throws PlatformException {
		this.populateSystemConf(conf);
		this.populateAllinpayConf(conf);
	}

	private void populateSystemConf(Ini conf) throws PlatformException {
		Section section = conf.get("system");
		try {
			if (section != null) {
				systemConfig = new SystemConfig();
				systemConfig.setBatchLimit(Long.valueOf(section
						.get("batchLimit")));
				systemConfig.setOracleUniqeErrorCode(section
						.get("oracleUniqeErrorCode"));
				systemConfig.setMerge_online_key(section.get("merge_online_key")); // 线上合并队列key
				systemConfig.setMerge_offline_key(section.get("merge_offline_key")); // 先下合并队列key
				
				// 设置全局变量
				log.info("TPPGLOBALCONFIGPATH: " + System.getenv("TPPGLOBALCONFIGPATH"));
				systemConfig.setGlobalConfigPath(System.getenv("TPPGLOBALCONFIGPATH"));
			}
		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
	}

	private void populateAllinpayConf(Ini conf) throws PlatformException {
		Section section = conf.get("allinpay");
		try {

		} catch (Exception e) {
			log.error("===", e);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));

		}
	}

	public AllinpayConfig getAllinpayConfig() {
		return allinpayConfig;
	}

	public void setAllinpayConfig(AllinpayConfig allinpayConfig) {
		this.allinpayConfig = allinpayConfig;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
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

	public static void main(String args[]) {
		ConfigUtil configUtil = ConfigUtil.getInstance();
		System.out.println("batchLimit:"
				+ configUtil.systemConfig.getBatchLimit());

	}

}
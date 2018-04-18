package com.zendaimoney.thirdpp.account.util;

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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.zendaimoney.thirdpp.account.conf.BizsysAccountPropertyConfig;
import com.zendaimoney.thirdpp.account.conf.ChannelAccountPropertyConfig;
import com.zendaimoney.thirdpp.account.conf.SystemConfig;
import com.zendaimoney.thirdpp.account.exception.PlatformException;

public class ConfigUtil implements Serializable {

	private static final long serialVersionUID = 7364183764577527251L;

	private static final Log logger = LogFactory.getLog(ConfigUtil.class);

	private SystemConfig systemConfig;
	
	private ChannelAccountPropertyConfig channelAccountPropertyConfig;
	
	private BizsysAccountPropertyConfig bizsysAccountPropertyConfig;

	public static String getConfigFilePath(String[] args) {
		logger.info("args of main method :" + args);
		Options options = new Options();
		Option fOption = new Option("f", true, "Configuration file path");
		options.addOption(fOption);

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
			throw new PlatformException("None f argument");
		}

		return configFilePath;
	}

	public void loadConfigFile(String configFilePath) {
		try {
			if (StringUtils.isBlank(configFilePath)) {
				throw new PlatformException("Blank argument configFilePath");
			}
			File file = new File(configFilePath);
			if (!file.exists()) {
				logger.error("File within path " + configFilePath
						+ " not exist");
				throw new PlatformException("File within path "
						+ configFilePath + " not exist");
			}

			Ini ini = new Ini(file);
			initialSystemProperties(ini);
			initialChannelAccountProperties(ini);
			initialBizsysAccountProperties(ini);
			logger.info("Load file within path " + configFilePath + " success");
		} catch (IOException e) {
			logger.error("Parse configuration failed,path=" + configFilePath);
			throw new PlatformException("Parse configuration failed,path="
					+ configFilePath, e);
		} catch (Exception e) {
			logger.error("Parse configuration failed,path=" + configFilePath);
			throw new PlatformException("Parse configuration failed,path="
					+ configFilePath, e);
		}
	}

	private void initialSystemProperties(Ini ini) {
		Section section = ini.get("system");
		if (section != null) {
			systemConfig = new SystemConfig();
			systemConfig.setAppName(section.get("appName"));
			systemConfig.setDefaultSleepTime(Long.parseLong(section.get("defaultSleepTime")));
			systemConfig.setErrorSleepTime(Long.parseLong(section.get("errorSleepTime")));
            systemConfig.setFtpServer(section.get("ftp_server"));
			systemConfig.setFtpPort(section.get("ftp_port"));
			systemConfig.setFtpRootUsername(section.get("ftp_root_username"));
			systemConfig.setFtpRootPwd(section.get("ftp_root_pwd"));
			systemConfig.setBatchSize(Integer.parseInt(section.get("batch_size")));
		}
	}
	
	private void initialChannelAccountProperties(Ini ini) {
		Section section = ini.get("channel_account");
		if (section != null) {
			channelAccountPropertyConfig = new ChannelAccountPropertyConfig();
			channelAccountPropertyConfig.setFtpChannelAccountFileTempPath(section.get("ftp_channel_account_file_temp_path"));
			channelAccountPropertyConfig.setFtpChannelAccountFileBackupPath(section.get("ftp_channel_account_file_backup_path"));
		}
	}
	
	private void initialBizsysAccountProperties(Ini ini) {
		Section section = ini.get("bizsys_account");
		if (section != null) {
			bizsysAccountPropertyConfig = new BizsysAccountPropertyConfig();
		}
	}
	
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	
	public ChannelAccountPropertyConfig getChannelAccountPropertiesConfig() {
		return channelAccountPropertyConfig;
	}
	
	public BizsysAccountPropertyConfig getBizsysAccountPropertiesConfig() {
		return bizsysAccountPropertyConfig;
	}

}

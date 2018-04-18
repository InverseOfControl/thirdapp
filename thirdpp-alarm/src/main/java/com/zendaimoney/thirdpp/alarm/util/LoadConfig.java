package com.zendaimoney.thirdpp.alarm.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;

import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;

public class LoadConfig {

	public static String getConfigFilePath(String[] args)
			throws CsswebException {
		Options options = new Options();
		Option file = new Option("f", true, "Configuration file path");
		options.addOption(file);
		CommandLineParser parser = new PosixParser();
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			throw new CsswebException("Parse command line failed", e);
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
			throw new CsswebException("Blank file path");
		}
		return configFilePath;
	}
}

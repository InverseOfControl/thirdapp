package com.zendaimoney.thirdpp.account.conf;

import java.io.Serializable;

public class SystemConfig implements Serializable {

	private static final long serialVersionUID = 9062227496304675480L;

	// 应用系统名称
	private String appName;

	private long defaultSleepTime;

	private long errorSleepTime;
	
	private String ftpServer;
	
	private String ftpPort;
	
	private String ftpRootUsername;
	
	private String ftpRootPwd;
	
	private int batchSize;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getDefaultSleepTime() {
		return defaultSleepTime;
	}

	public void setDefaultSleepTime(long defaultSleepTime) {
		this.defaultSleepTime = defaultSleepTime;
	}

	public long getErrorSleepTime() {
		return errorSleepTime;
	}

	public void setErrorSleepTime(long errorSleepTime) {
		this.errorSleepTime = errorSleepTime;
	}

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpRootUsername() {
		return ftpRootUsername;
	}

	public void setFtpRootUsername(String ftpRootUsername) {
		this.ftpRootUsername = ftpRootUsername;
	}

	public String getFtpRootPwd() {
		return ftpRootPwd;
	}

	public void setFtpRootPwd(String ftpRootPwd) {
		this.ftpRootPwd = ftpRootPwd;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}

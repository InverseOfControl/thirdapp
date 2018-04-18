package com.zendaimoney.trust.channel.conf;

import java.io.Serializable;

/**
 * FTP配置对象
 * @author mencius
 *
 */
public class FtpConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * ftp地址
	 */
	private String ftpServer;
	
	/**
	 * ftp端口
	 */
	private String ftpPort;
	
	/**
	 * ftp用户名
	 */
	private String ftpUsername;
	
	/**
	 * ftp密码
	 */
	private String ftpPwd;
	
	/**
	 * ftp_upload存储目录
	 */
	private String ftpUploadPath;
	
	/**
	 * ftp_download_path
	 */
	private String ftpDownloadPath;
	
	/**
	 * 配置静态化
	 */
	public static FtpConfig ftpConfig;

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

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}


	public String getFtpUploadPath() {
		return ftpUploadPath;
	}

	public void setFtpUploadPath(String ftpUploadPath) {
		this.ftpUploadPath = ftpUploadPath;
	}

	public String getFtpDownloadPath() {
		return ftpDownloadPath;
	}

	public void setFtpDownloadPath(String ftpDownloadPath) {
		this.ftpDownloadPath = ftpDownloadPath;
	}

	public static FtpConfig getFtpConfig() {
		
		if (ftpConfig == null) {
			ftpConfig = new FtpConfig();
		}
		return ftpConfig;
	}
	
	
	public static void setFtpConfig(FtpConfig ftpConfig) {
		FtpConfig.ftpConfig = ftpConfig;
	}
	
}

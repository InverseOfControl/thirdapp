package com.zendaimoney.thirdpp.account.conf;

import java.io.Serializable;

public class ChannelAccountPropertyConfig implements Serializable {

	private static final long serialVersionUID = -7625333698324095370L;

	// 第三方对账本地对账文件存放目录
	private String ftpChannelAccountFileBackupPath;
	private String ftpChannelAccountFileTempPath;
	
	public String getFtpChannelAccountFileBackupPath() {
		return ftpChannelAccountFileBackupPath;
	}
	public void setFtpChannelAccountFileBackupPath(
			String ftpChannelAccountFileBackupPath) {
		this.ftpChannelAccountFileBackupPath = ftpChannelAccountFileBackupPath;
	}
	public String getFtpChannelAccountFileTempPath() {
		return ftpChannelAccountFileTempPath;
	}
	public void setFtpChannelAccountFileTempPath(
			String ftpChannelAccountFileTempPath) {
		this.ftpChannelAccountFileTempPath = ftpChannelAccountFileTempPath;
	}


}

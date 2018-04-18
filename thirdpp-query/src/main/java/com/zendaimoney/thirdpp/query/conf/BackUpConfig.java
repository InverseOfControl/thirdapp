package com.zendaimoney.thirdpp.query.conf;



/**
 * @author user
 *
 */
public class BackUpConfig {

	// 备份路径
	private String backupPath;
	
	// 备份批量
	private int backSize;
	
	/**
	 * 批量备份往前推N天
	 */
	private int beforeDayNum;
	
	public static BackUpConfig backUpConfig;

	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}

	public int getBackSize() {
		return backSize;
	}

	public void setBackSize(int backSize) {
		this.backSize = backSize;
	}

	public int getBeforeDayNum() {
		return beforeDayNum;
	}

	public void setBeforeDayNum(int beforeDayNum) {
		this.beforeDayNum = beforeDayNum;
	}

	public static BackUpConfig getBackUpConfig() {
		return backUpConfig;
	}

	public static void setBackUpConfig(BackUpConfig backUpConfig) {
		BackUpConfig.backUpConfig = backUpConfig;
	}
	
	
}

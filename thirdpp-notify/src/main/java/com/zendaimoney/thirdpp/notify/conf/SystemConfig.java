package com.zendaimoney.thirdpp.notify.conf;

import java.io.Serializable;

/**
 * 系統配置文件
 * 
 * @author 00231257
 *
 */
public class SystemConfig implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 938684883375092040L;
	// 应用系统名称
	private String appName;
	
	//通知休眠时间
	private long notifySleepTime;
	
	//合单休眠时间
	private long mergeSleepTime;
	
	//查询待合单的休眠时间
	private long queryWaitingSleepTime;
	
	//通知表每次查询记录数
	private int notifyQueryCount;
	
	//待通知表每次查询记录数
	private int waitingQueryCount;
	
	//通知次数
	private int notifyNumber;
	
	//合并队列keyName
	private String merge_online_key;
	
	//合并队列keyName
	private String merge_offline_key;
	
	// 配置文件路径
	private String globalConfigPath;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getNotifySleepTime() {
		return notifySleepTime;
	}

	public void setNotifySleepTime(long notifySleepTime) {
		this.notifySleepTime = notifySleepTime;
	}

	public long getMergeSleepTime() {
		return mergeSleepTime;
	}

	public void setMergeSleepTime(long mergeSleepTime) {
		this.mergeSleepTime = mergeSleepTime;
	}

	public int getNotifyQueryCount() {
		return notifyQueryCount;
	}

	public void setNotifyQueryCount(int notifyQueryCount) {
		this.notifyQueryCount = notifyQueryCount;
	}

	public int getNotifyNumber() {
		return notifyNumber;
	}

	public void setNotifyNumber(int notifyNumber) {
		this.notifyNumber = notifyNumber;
	}

	public long getQueryWaitingSleepTime() {
		return queryWaitingSleepTime;
	}

	public void setQueryWaitingSleepTime(long queryWaitingSleepTime) {
		this.queryWaitingSleepTime = queryWaitingSleepTime;
	}

	public int getWaitingQueryCount() {
		return waitingQueryCount;
	}

	public void setWaitingQueryCount(int waitingQueryCount) {
		this.waitingQueryCount = waitingQueryCount;
	}
	
	public String getMerge_offline_key() {
		return merge_offline_key;
	}
	
	public void setMerge_offline_key(String merge_offline_key) {
		this.merge_offline_key = merge_offline_key;
	}
	
	public String getMerge_online_key() {
		return merge_online_key;
	}
	
	public void setMerge_online_key(String merge_online_key) {
		this.merge_online_key = merge_online_key;
	}

	public String getGlobalConfigPath() {
		return globalConfigPath;
	}
	
	public void setGlobalConfigPath(String globalConfigPath) {
		this.globalConfigPath = globalConfigPath;
	}
}

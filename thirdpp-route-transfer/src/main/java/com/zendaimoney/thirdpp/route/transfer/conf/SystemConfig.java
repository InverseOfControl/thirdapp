package com.zendaimoney.thirdpp.route.transfer.conf;

import java.io.Serializable;

/**
 * 系統配置文件
 * 
 * @author 00231257
 *
 */
public class SystemConfig implements Serializable {

	
	/**  */
    private static final long serialVersionUID = -615096298962211848L;
    // 应用系统名称
	private String appName;
	// 线程休眠时间
	private long sleepTime;
	// 线程运行错误休眠时间
	private long errorSleepTime;
	// 有待发数据时线程休眠时间
	private long notEmptySleepTime;
	// 运行失败次数峰值(超过该峰值，系统自动告警)
	private int maxWarnNum;
	// 系统告警手机号码
	private String alarmMobiles;
	// 系统告警邮箱地址
	private String alarmEmails;
	// 最大发送次数
	private int maxSendNum;
	// 任务拆分数
	private int taskSplitNum = 0;
	//线程数
	private int numThread;
	//默认扫描的通道编码
	private String thirdTypeNo;
	
	public String getThirdTypeNo() {
        return thirdTypeNo;
    }

    public void setThirdTypeNo(String thirdTypeNo) {
        this.thirdTypeNo = thirdTypeNo;
    }

    public int getNumThread() {
        return numThread;
    }

    public void setNumThread(int numThread) {
        this.numThread = numThread;
    }

    //合并队列keyName
	private String merge_online_key;
	
	//合并队列keyName
	private String merge_offline_key;
	
	// #全局配置文件路径
	private String globalConfigPath;
	
	public String getGlobalConfigPath() {
		return globalConfigPath;
	}
	
	public void setGlobalConfigPath(String globalConfigPath) {
		this.globalConfigPath = globalConfigPath;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public long getErrorSleepTime() {
		return errorSleepTime;
	}

	public void setErrorSleepTime(long errorSleepTime) {
		this.errorSleepTime = errorSleepTime;
	}

	public int getMaxWarnNum() {
		return maxWarnNum;
	}

	public void setMaxWarnNum(int maxWarnNum) {
		this.maxWarnNum = maxWarnNum;
	}

	public String getAlarmMobiles() {
		return alarmMobiles;
	}

	public void setAlarmMobiles(String alarmMobiles) {
		this.alarmMobiles = alarmMobiles;
	}

	public String getAlarmEmails() {
		return alarmEmails;
	}

	public void setAlarmEmails(String alarmEmails) {
		this.alarmEmails = alarmEmails;
	}

	public long getNotEmptySleepTime() {
		return notEmptySleepTime;
	}

	public void setNotEmptySleepTime(long notEmptySleepTime) {
		this.notEmptySleepTime = notEmptySleepTime;
	}

	public int getMaxSendNum() {
		return maxSendNum;
	}

	public void setMaxSendNum(int maxSendNum) {
		this.maxSendNum = maxSendNum;
	}

	public int getTaskSplitNum() {
		return taskSplitNum;
	}

	public void setTaskSplitNum(int taskSplitNum) {
		this.taskSplitNum = taskSplitNum;
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

}

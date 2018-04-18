package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.util.Date;

public class BizsysAccountRequest implements Serializable {

	private static final long serialVersionUID = 2730267029047486714L;

	// 请求ID
	private String reqId;
	// 处理进程
	private String appName;
	// 业务系统号
	private String bizSysNo;
	// 业务类型
	private String bizType;
	// 配置id
	private Long configId;
	// 对账日期
	private String accountDay;
	// 状态 0=初始状态  1=保存至本地失败 2=保存至本地成功 3=推送失败 4=推送成功
	private int status;
	
	// 手工对账状态 0：未处理，1：处理中，2：处理失败，3：处理成功
	private int handleAccountStatus;
	// 本地存储开始时间
	private Date localizeStartTime;
	// 本地存储对账文件目录
	private String localizePath;
	// 本地存储对账文件名称
	private String localizeFileName;
	// 本地存储结束时间
	private Date localizeEndTime;
	// 本地存储失败次数
	private int localizeFailedTimes;
	// 推送开始时间
	private Date pushStartTime;
	// 推送的对账文件名称
	private String pushFileName;
	// 推送的对账文件大小
	private long pushFileSize;
	// 对账文件推送目录
	private String pushFilePath;
	// 推送结束时间
	private Date pushEndTime;
	// 推送失败的次数
	private int pushFailedTimes;
	// 失败原因
	private String failedReason;
	// 预留字段
	private String spare1;
	// 预留字段
	private String spare2;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLocalizeStartTime() {
		return localizeStartTime;
	}

	public void setLocalizeStartTime(Date localizeStartTime) {
		this.localizeStartTime = localizeStartTime;
	}

	public String getLocalizePath() {
		return localizePath;
	}

	public void setLocalizePath(String localizePath) {
		this.localizePath = localizePath;
	}

	public String getLocalizeFileName() {
		return localizeFileName;
	}

	public void setLocalizeFileName(String localizeFileName) {
		this.localizeFileName = localizeFileName;
	}

	public Date getLocalizeEndTime() {
		return localizeEndTime;
	}

	public void setLocalizeEndTime(Date localizeEndTime) {
		this.localizeEndTime = localizeEndTime;
	}

	public int getLocalizeFailedTimes() {
		return localizeFailedTimes;
	}

	public void setLocalizeFailedTimes(int localizeFailedTimes) {
		this.localizeFailedTimes = localizeFailedTimes;
	}

	public Date getPushStartTime() {
		return pushStartTime;
	}

	public void setPushStartTime(Date pushStartTime) {
		this.pushStartTime = pushStartTime;
	}

	public String getPushFileName() {
		return pushFileName;
	}

	public void setPushFileName(String pushFileName) {
		this.pushFileName = pushFileName;
	}

	public Long getPushFileSize() {
		return pushFileSize;
	}

	public void setPushFileSize(Long pushFileSize) {
		this.pushFileSize = pushFileSize;
	}

	public String getPushFilePath() {
		return pushFilePath;
	}

	public void setPushFilePath(String pushFilePath) {
		this.pushFilePath = pushFilePath;
	}

	public Date getPushEndTime() {
		return pushEndTime;
	}

	public void setPushEndTime(Date pushEndTime) {
		this.pushEndTime = pushEndTime;
	}

	public int getPushFailedTimes() {
		return pushFailedTimes;
	}

	public void setPushFailedTimes(int pushFailedTimes) {
		this.pushFailedTimes = pushFailedTimes;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public String getSpare1() {
		return spare1;
	}

	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}

	public String getSpare2() {
		return spare2;
	}

	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getHandleAccountStatus() {
		return handleAccountStatus;
	}

	public void setHandleAccountStatus(int handleAccountStatus) {
		this.handleAccountStatus = handleAccountStatus;
	}

}

package com.zdmoney.manager.models;

import java.util.Date;

public class TppAccountBizsysRequest {
    private String reqId;

    private String bizSysNo;
    
    private String bizType;

    private Long configId;

    private String accountDay;

    private Long status;

    private Date localizeStartTime;

    private String localizePath;

    private String localizeFileName;

    private Date localizeEndTime;

    private Date pushStartTime;

    private String pushFileName;

    private Long pushFileSize;

    private String pushFilePath;

    private Date pushEndTime;

    private Long pushFailedTimes;

    private String failedReason;

    private String spare1;

    private String spare2;

    private Date createTime;

    private Date updateTime;
    
    private String appName;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }


    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType == null ? null : bizType.trim();
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
        this.accountDay = accountDay == null ? null : accountDay.trim();
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
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
        this.localizePath = localizePath == null ? null : localizePath.trim();
    }

    public String getLocalizeFileName() {
        return localizeFileName;
    }

    public void setLocalizeFileName(String localizeFileName) {
        this.localizeFileName = localizeFileName == null ? null : localizeFileName.trim();
    }

    public Date getLocalizeEndTime() {
        return localizeEndTime;
    }

    public void setLocalizeEndTime(Date localizeEndTime) {
        this.localizeEndTime = localizeEndTime;
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
        this.pushFileName = pushFileName == null ? null : pushFileName.trim();
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
        this.pushFilePath = pushFilePath == null ? null : pushFilePath.trim();
    }

    public Date getPushEndTime() {
        return pushEndTime;
    }

    public void setPushEndTime(Date pushEndTime) {
        this.pushEndTime = pushEndTime;
    }

    public Long getPushFailedTimes() {
        return pushFailedTimes;
    }

    public void setPushFailedTimes(Long pushFailedTimes) {
        this.pushFailedTimes = pushFailedTimes;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
    }

    public String getSpare1() {
        return spare1;
    }

    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    public String getSpare2() {
        return spare2;
    }

    public void setSpare2(String spare2) {
        this.spare2 = spare2 == null ? null : spare2.trim();
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
    
    public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName == null ? "" : appName.trim();
	}
}
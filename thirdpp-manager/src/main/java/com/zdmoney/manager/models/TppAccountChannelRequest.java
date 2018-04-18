package com.zdmoney.manager.models;

import java.util.Date;

public class TppAccountChannelRequest {
	
    private String reqId;
    
    private String appName;

    private String thirdTypeNo;

    private String merchantNo;

    private String bizType;

    private String accountDay;

    private Long configId;

    private Date downloadStartTime;

    private Long downloadFailedTimes;

    private String downloadFileName;

    private Long downloadFileSize;

    private String downloadFilePath;

    private Date downloadEndTime;

    private String totalCountAmountDesc;

    private Date insertStartTime;

    private Long insertFailedTimes;

    private Date insertEndTime;

    private Long accountFailedTimes;
    
    private Date backupStartTime;

    private Long backupFailedTimes;
    
    private Date backupEndTime;

    private Long status;

    private Long handleAccountStatus;
    
    private String failedReason;

    private Date createTime;

    private Date updateTime;

    private String spare1;

    private String spare2;
    
    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }

    public String getThirdTypeNo() {
        return thirdTypeNo;
    }

    public void setThirdTypeNo(String thirdTypeNo) {
        this.thirdTypeNo = thirdTypeNo == null ? null : thirdTypeNo.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getAccountDay() {
        return accountDay;
    }

    public void setAccountDay(String accountDay) {
        this.accountDay = accountDay == null ? null : accountDay.trim();
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Date getDownloadStartTime() {
        return downloadStartTime;
    }

    public void setDownloadStartTime(Date downloadStartTime) {
        this.downloadStartTime = downloadStartTime;
    }

    public Long getDownloadFailedTimes() {
        return downloadFailedTimes;
    }

    public void setDownloadFailedTimes(Long downloadFailedTimes) {
        this.downloadFailedTimes = downloadFailedTimes;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName == null ? null : downloadFileName.trim();
    }

    public Long getDownloadFileSize() {
        return downloadFileSize;
    }

    public void setDownloadFileSize(Long downloadFileSize) {
        this.downloadFileSize = downloadFileSize;
    }

    public String getDownloadFilePath() {
        return downloadFilePath;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath == null ? null : downloadFilePath.trim();
    }

    public Date getDownloadEndTime() {
        return downloadEndTime;
    }

    public void setDownloadEndTime(Date downloadEndTime) {
        this.downloadEndTime = downloadEndTime;
    }

    public Date getInsertStartTime() {
        return insertStartTime;
    }

    public void setInsertStartTime(Date insertStartTime) {
        this.insertStartTime = insertStartTime;
    }

    public Long getInsertFailedTimes() {
        return insertFailedTimes;
    }

    public void setInsertFailedTimes(Long insertFailedTimes) {
        this.insertFailedTimes = insertFailedTimes;
    }

    public Date getInsertEndTime() {
        return insertEndTime;
    }

    public void setInsertEndTime(Date insertEndTime) {
        this.insertEndTime = insertEndTime;
    }

    public Date getBackupStartTime() {
        return backupStartTime;
    }

    public void setBackupStartTime(Date backupStartTime) {
        this.backupStartTime = backupStartTime;
    }

    public Date getBackupEndTime() {
        return backupEndTime;
    }

    public void setBackupEndTime(Date backupEndTime) {
        this.backupEndTime = backupEndTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason == null ? null : failedReason.trim();
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
    
    public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName == null ? "" : appName.trim();
	}

	public String getTotalCountAmountDesc() {
		return totalCountAmountDesc;
	}

	public void setTotalCountAmountDesc(String totalCountAmountDesc) {
		this.totalCountAmountDesc = totalCountAmountDesc;
	}

	public Long getAccountFailedTimes() {
		return accountFailedTimes;
	}

	public void setAccountFailedTimes(Long accountFailedTimes) {
		this.accountFailedTimes = accountFailedTimes;
	}

	public Long getBackupFailedTimes() {
		return backupFailedTimes;
	}

	public void setBackupFailedTimes(Long backupFailedTimes) {
		this.backupFailedTimes = backupFailedTimes;
	}

	public Long getHandleAccountStatus() {
		return handleAccountStatus;
	}

	public void setHandleAccountStatus(Long handleAccountStatus) {
		this.handleAccountStatus = handleAccountStatus;
	}
	
}
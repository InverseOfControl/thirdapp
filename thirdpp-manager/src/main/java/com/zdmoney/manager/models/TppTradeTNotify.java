package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TppTradeTNotify implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2159135860477252140L;

	private Long id;

    private String bizFlow;

    private String bizSysNo;

    private String bizType;

    private Date createTime;

    private Date updateTime;

    private Long notifyStatus;

    private String tradeStatus;

    private String tradeResultInfo;

    private Long taskId;

    private String tradeFlow;

    private BigDecimal tradeSuccessAmount;

    private String appName;

    private Long notifyCount;

    private String failReason;

    

	

	private String beginTime;
    private String endTime;
   

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizFlow() {
        return bizFlow;
    }

    public void setBizFlow(String bizFlow) {
        this.bizFlow = bizFlow == null ? null : bizFlow.trim();
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

    public Long getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Long notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }

    public String getTradeResultInfo() {
        return tradeResultInfo;
    }

    public void setTradeResultInfo(String tradeResultInfo) {
        this.tradeResultInfo = tradeResultInfo == null ? null : tradeResultInfo.trim();
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow == null ? null : tradeFlow.trim();
    }

    public BigDecimal getTradeSuccessAmount() {
        return tradeSuccessAmount;
    }

    public void setTradeSuccessAmount(BigDecimal tradeSuccessAmount) {
        this.tradeSuccessAmount = tradeSuccessAmount;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public Long getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(Long notifyCount) {
        this.notifyCount = notifyCount;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }
}
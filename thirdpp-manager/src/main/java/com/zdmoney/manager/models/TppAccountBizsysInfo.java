package com.zdmoney.manager.models;

import java.math.BigDecimal;
import java.util.Date;

public class TppAccountBizsysInfo {
    private Short taskId;

    private String bizSysNo;

    private String bizFlow;

    private String bizType;

    private String paySysNo;

    private BigDecimal totalAmount;

    private BigDecimal successAmount;

    private String accountDay;

    private Date createTime;

    public Short getTaskId() {
        return taskId;
    }

    public void setTaskId(Short taskId) {
        this.taskId = taskId;
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getBizFlow() {
        return bizFlow;
    }

    public void setBizFlow(String bizFlow) {
        this.bizFlow = bizFlow == null ? null : bizFlow.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo == null ? null : paySysNo.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public String getAccountDay() {
        return accountDay;
    }

    public void setAccountDay(String accountDay) {
        this.accountDay = accountDay == null ? null : accountDay.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
package com.zdmoney.manager.models;

import java.math.BigDecimal;
import java.util.Date;

public class TppAccountInfo {
    private Long id;

    private String thirdTypeNo;

    private String merchantNo;

    private String bizType;

    private String thirdPartyAccountReqId;

    private String bizsysAccountReqId;

    private String accountDay;
    
    private Long taskId;
    
    private BigDecimal taskAmount;

    private Long isSeparate;

    private Long separateCount;

    private String bizSysNo;

    private String tradeFlow;

    private BigDecimal amount;

    private String originalAmount;

    private String thirdPartyTradeFlow;

    private String tradeTime;

    private Long currentIndex;

    private Long accountStatus;

    private Long bizsysAccountStatus;

    private String failedReason;

    private Date createTime;

    private String bizFlow;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getThirdPartyAccountReqId() {
        return thirdPartyAccountReqId;
    }

    public void setThirdPartyAccountReqId(String thirdPartyAccountReqId) {
        this.thirdPartyAccountReqId = thirdPartyAccountReqId == null ? null : thirdPartyAccountReqId.trim();
    }

    public String getBizsysAccountReqId() {
        return bizsysAccountReqId;
    }

    public void setBizsysAccountReqId(String bizsysAccountReqId) {
        this.bizsysAccountReqId = bizsysAccountReqId == null ? null : bizsysAccountReqId.trim();
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getIsSeparate() {
        return isSeparate;
    }

    public void setIsSeparate(Long isSeparate) {
        this.isSeparate = isSeparate;
    }

    public Long getSeparateCount() {
        return separateCount;
    }

    public void setSeparateCount(Long separateCount) {
        this.separateCount = separateCount;
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow == null ? null : tradeFlow.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(String originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getThirdPartyTradeFlow() {
        return thirdPartyTradeFlow;
    }

    public void setThirdPartyTradeFlow(String thirdPartyTradeFlow) {
        this.thirdPartyTradeFlow = thirdPartyTradeFlow == null ? null : thirdPartyTradeFlow.trim();
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime == null ? null : tradeTime.trim();
    }

    public Long getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Long currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Long getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Long getBizsysAccountStatus() {
        return bizsysAccountStatus;
    }

    public void setBizsysAccountStatus(Long bizsysAccountStatus) {
        this.bizsysAccountStatus = bizsysAccountStatus;
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

    public String getBizFlow() {
        return bizFlow;
    }

    public void setBizFlow(String bizFlow) {
        this.bizFlow = bizFlow == null ? null : bizFlow.trim();
    }

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
	}

	public BigDecimal getTaskAmount() {
		return taskAmount;
	}

	public void setTaskAmount(BigDecimal taskAmount) {
		this.taskAmount = taskAmount;
	}
    
    
}
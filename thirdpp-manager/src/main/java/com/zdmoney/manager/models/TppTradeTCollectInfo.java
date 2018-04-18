package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TppTradeTCollectInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7038776431276308255L;

	private Long id;

    private Long taskId;

    private String reqId;

    private String paySysNo;

    private String bizSysAccountNo;

    private String zengdaiAccountNo;

    private String bizSysNo;

    private String receiverAccountNo;

    private String reveiverAccountName;

    private String payerName;

    private String payerBankCardNo;

    private String payerBankCardType;

    private String payerIdType;

    private String payerId;

    private String payerBankCode;

    private String payerSubBankCode;

    private String currency;

    private BigDecimal amount;

    private BigDecimal fee;

    private String bizRemark;

    private String bizFlow;

    private Long priority;

    private String status;

    private String remark;

    private String creater;

    private Date createTime;

    private Date updateTime;

    private String spare1;

    private String spare2;

    private String tradeFlow;

    private String payTransFlow;

    private String failReason;

    private String payerAccountNo;

    private String bizType;

    private Long isNeedPush;

    private String infoCategoryCode;

    private String transRepCode;
    
    private Long isNeedSpilt;
    
    private Date thirdReturnTime;
    
    private Long notifyQueryStatus;
    
    private Long notifyMergeStatus;
    
    private String payerMobile;
    
    private String settleDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }

    public String getPaySysNo() {
        return paySysNo;
    }

    public void setPaySysNo(String paySysNo) {
        this.paySysNo = paySysNo == null ? null : paySysNo.trim();
    }

    public String getBizSysAccountNo() {
        return bizSysAccountNo;
    }

    public void setBizSysAccountNo(String bizSysAccountNo) {
        this.bizSysAccountNo = bizSysAccountNo == null ? null : bizSysAccountNo.trim();
    }

    public String getZengdaiAccountNo() {
        return zengdaiAccountNo;
    }

    public void setZengdaiAccountNo(String zengdaiAccountNo) {
        this.zengdaiAccountNo = zengdaiAccountNo == null ? null : zengdaiAccountNo.trim();
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getReceiverAccountNo() {
        return receiverAccountNo;
    }

    public void setReceiverAccountNo(String receiverAccountNo) {
        this.receiverAccountNo = receiverAccountNo == null ? null : receiverAccountNo.trim();
    }

    public String getReveiverAccountName() {
        return reveiverAccountName;
    }

    public void setReveiverAccountName(String reveiverAccountName) {
        this.reveiverAccountName = reveiverAccountName == null ? null : reveiverAccountName.trim();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public String getPayerBankCardNo() {
        return payerBankCardNo;
    }

    public void setPayerBankCardNo(String payerBankCardNo) {
        this.payerBankCardNo = payerBankCardNo == null ? null : payerBankCardNo.trim();
    }

    public String getPayerBankCardType() {
        return payerBankCardType;
    }

    public void setPayerBankCardType(String payerBankCardType) {
        this.payerBankCardType = payerBankCardType == null ? null : payerBankCardType.trim();
    }

    public String getPayerIdType() {
        return payerIdType;
    }

    public void setPayerIdType(String payerIdType) {
        this.payerIdType = payerIdType == null ? null : payerIdType.trim();
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId == null ? null : payerId.trim();
    }

    public String getPayerBankCode() {
        return payerBankCode;
    }

    public void setPayerBankCode(String payerBankCode) {
        this.payerBankCode = payerBankCode == null ? null : payerBankCode.trim();
    }

    public String getPayerSubBankCode() {
        return payerSubBankCode;
    }

    public void setPayerSubBankCode(String payerSubBankCode) {
        this.payerSubBankCode = payerSubBankCode == null ? null : payerSubBankCode.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getBizRemark() {
        return bizRemark;
    }

    public void setBizRemark(String bizRemark) {
        this.bizRemark = bizRemark == null ? null : bizRemark.trim();
    }

    public String getBizFlow() {
        return bizFlow;
    }

    public void setBizFlow(String bizFlow) {
        this.bizFlow = bizFlow == null ? null : bizFlow.trim();
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
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

    public String getTradeFlow() {
        return tradeFlow;
    }

    public void setTradeFlow(String tradeFlow) {
        this.tradeFlow = tradeFlow == null ? null : tradeFlow.trim();
    }

    public String getPayTransFlow() {
        return payTransFlow;
    }

    public void setPayTransFlow(String payTransFlow) {
        this.payTransFlow = payTransFlow == null ? null : payTransFlow.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo == null ? null : payerAccountNo.trim();
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public Long getIsNeedPush() {
        return isNeedPush;
    }

    public void setIsNeedPush(Long isNeedPush) {
        this.isNeedPush = isNeedPush;
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode == null ? null : infoCategoryCode.trim();
    }

    public String getTransRepCode() {
        return transRepCode;
    }

    public void setTransRepCode(String transRepCode) {
        this.transRepCode = transRepCode == null ? null : transRepCode.trim();
    }

	public Long getIsNeedSpilt() {
		return isNeedSpilt;
	}

	public void setIsNeedSpilt(Long isNeedSpilt) {
		this.isNeedSpilt = isNeedSpilt;
	}

	public Date getThirdReturnTime() {
		return thirdReturnTime;
	}

	public void setThirdReturnTime(Date thirdReturnTime) {
		this.thirdReturnTime = thirdReturnTime;
	}

	public Long getNotifyQueryStatus() {
		return notifyQueryStatus;
	}

	public void setNotifyQueryStatus(Long notifyQueryStatus) {
		this.notifyQueryStatus = notifyQueryStatus;
	}

	public Long getNotifyMergeStatus() {
		return notifyMergeStatus;
	}

	public void setNotifyMergeStatus(Long notifyMergeStatus) {
		this.notifyMergeStatus = notifyMergeStatus;
	}

	public String getPayerMobile() {
		return payerMobile;
	}

	public void setPayerMobile(String payerMobile) {
		this.payerMobile = payerMobile;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
    
}
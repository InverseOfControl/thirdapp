package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TppTradeTPayInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8306243656496764476L;

	private Long id;

    private Long taskId;

    private String reqId;

    private String paySysNo;

    private String bizSysAccountNo;

    private String zengdaiAccountNo;

    private String bizSysNo;

    private String payerAccountNo;

    private String payerAccountName;

    private String receiverName;

    private String receiverBankCardNo;

    private String receiverBankCardType;

    private String receiverIdType;

    private String receiverId;
    
    private String receiverType;

    private String receiverBankCode;

    private String receiverSubBankCode;

    private String currency;

    private BigDecimal amount;

    private BigDecimal fee;

    private String bizRemark;

    private String bizFlow;

    private String transferFlow;

    private String payTransFlow;

    private Long priority;

    private String status;

    private String remark;

    private String creater;

    private Date createTime;

    private Date updateTime;

    private String spare1;

    private String spare2;

    private String tradeFlow;

    private String failReason;

    private String receiverAccountNo;

    private String bizType;

    private Long isNeedPush;

    private String infoCategoryCode;

    private String transRepCode;
    
    private Date thirdReturnTime;
    
    private Long notifyQueryStatus;
    
    private Long notifyMergeStatus;
    
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

    public String getPayerAccountNo() {
        return payerAccountNo;
    }

    public void setPayerAccountNo(String payerAccountNo) {
        this.payerAccountNo = payerAccountNo == null ? null : payerAccountNo.trim();
    }

    public String getPayerAccountName() {
        return payerAccountName;
    }

    public void setPayerAccountName(String payerAccountName) {
        this.payerAccountName = payerAccountName == null ? null : payerAccountName.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getReceiverBankCardNo() {
        return receiverBankCardNo;
    }

    public void setReceiverBankCardNo(String receiverBankCardNo) {
        this.receiverBankCardNo = receiverBankCardNo == null ? null : receiverBankCardNo.trim();
    }

    public String getReceiverBankCardType() {
        return receiverBankCardType;
    }

    public void setReceiverBankCardType(String receiverBankCardType) {
        this.receiverBankCardType = receiverBankCardType == null ? null : receiverBankCardType.trim();
    }

    public String getReceiverIdType() {
        return receiverIdType;
    }

    public void setReceiverIdType(String receiverIdType) {
        this.receiverIdType = receiverIdType == null ? null : receiverIdType.trim();
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId == null ? null : receiverId.trim();
    }

    public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getReceiverBankCode() {
        return receiverBankCode;
    }

    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode == null ? null : receiverBankCode.trim();
    }

    public String getReceiverSubBankCode() {
        return receiverSubBankCode;
    }

    public void setReceiverSubBankCode(String receiverSubBankCode) {
        this.receiverSubBankCode = receiverSubBankCode == null ? null : receiverSubBankCode.trim();
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

    public String getTransferFlow() {
        return transferFlow;
    }

    public void setTransferFlow(String transferFlow) {
        this.transferFlow = transferFlow == null ? null : transferFlow.trim();
    }

    public String getPayTransFlow() {
        return payTransFlow;
    }

    public void setPayTransFlow(String payTransFlow) {
        this.payTransFlow = payTransFlow == null ? null : payTransFlow.trim();
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

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getReceiverAccountNo() {
        return receiverAccountNo;
    }

    public void setReceiverAccountNo(String receiverAccountNo) {
        this.receiverAccountNo = receiverAccountNo == null ? null : receiverAccountNo.trim();
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

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate == null ? null : settleDate.trim();
	}
    
    
}
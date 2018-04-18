package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TppTradeTPayTask implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3836885996030951866L;

	private Long id;

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

    private Long priority;

    private Long status;

    private String remark;

    private String creater;

    private Date createTime;

    private Date updateTime;

    private String sendThreadName;

    private Long isSeparate;

    private Long separateCount;

    private String spare1;

    private String spare2;

    private Long sendNum;

    private String receiverAccountNo;

    private String bizType;

    private Long isNeedPush;

    private String tradeStatus;

    private String tradeResultInfo;

    private BigDecimal tradeSuccessAmount;

    private String infoCategoryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
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

    public String getSendThreadName() {
        return sendThreadName;
    }

    public void setSendThreadName(String sendThreadName) {
        this.sendThreadName = sendThreadName == null ? null : sendThreadName.trim();
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

    public Long getSendNum() {
        return sendNum;
    }

    public void setSendNum(Long sendNum) {
        this.sendNum = sendNum;
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

    public BigDecimal getTradeSuccessAmount() {
        return tradeSuccessAmount;
    }

    public void setTradeSuccessAmount(BigDecimal tradeSuccessAmount) {
        this.tradeSuccessAmount = tradeSuccessAmount;
    }

    public String getInfoCategoryCode() {
        return infoCategoryCode;
    }

    public void setInfoCategoryCode(String infoCategoryCode) {
        this.infoCategoryCode = infoCategoryCode == null ? null : infoCategoryCode.trim();
    }
}
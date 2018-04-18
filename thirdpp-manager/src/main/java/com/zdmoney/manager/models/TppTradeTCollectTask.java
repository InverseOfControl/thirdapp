package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TppTradeTCollectTask implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1927596229085492787L;

	private Long id;

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

    private String payerAccountNo;

    private String bizType;

    private Long isNeedPush;

    private String tradeStatus;

    private String tradeResultInfo;

    private BigDecimal tradeSuccessAmount;

    private String infoCategoryCode;
    
    private Long isNeedSpilt;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(Long isSeparate) {
		this.isSeparate = isSeparate;
	}

	public Long getSendNum() {
		return sendNum;
	}

	public void setSendNum(Long sendNum) {
		this.sendNum = sendNum;
	}

	public Long getIsNeedPush() {
		return isNeedPush;
	}

	public void setIsNeedPush(Long isNeedPush) {
		this.isNeedPush = isNeedPush;
	}

	public Long getIsNeedSpilt() {
		return isNeedSpilt;
	}

	public void setIsNeedSpilt(Long isNeedSpilt) {
		this.isNeedSpilt = isNeedSpilt;
	}
    
    
}
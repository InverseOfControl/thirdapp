package com.zendaimoney.thirdpp.notify.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

/**
 * 代收/还款业务任务对象
 * 
 * @author 00231257
 *
 */
public class CollectTask implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6837468772057505938L;

	// 主键ID
	private long id;

	// 请求ID
	private String reqId;

	// 业务系统客户号
	private String bizSysAccountNo;

	// 证大客户号
	private String zengdaiAccountNo;

	// 收款人账户号
	private String receiverAccountNo;

	// 收款人账户姓名
	private String reveiverAccountName;

	// 付款人姓名
	private String payerName;

	// 付款人银行卡号
	private String payerBankCardNo;

	// 付款人银行卡类型
	private String payerBankCardType;

	// 付款方证件类型
	// 付款人银行卡类型(0=身份证1=户口簿2=护照3=军官证4=士兵证5=港澳居民来往内地通行证6=台湾同胞来往内地通行证7=临时身份证8=外国人居留证9=警官证X=其他证件)
	private String payerIdType;

	// 付款方证件号
	private String payerId;

	// 付款方银行编码
	private String payerBankCode;

	// 付款方银行支行行号
	private String payerSubBankCode;

	/**
	 * 付款方账户编号
	 */
	@Length(max = 32, message = "000002,过长")
	private String payerAccountNo;

	// 币种(0人民币)
	private String currency;

	// 金额
	private BigDecimal amount;

	// 手续费
	private BigDecimal fee;

	// 业务备注
	@Length(max = 128, message = "000002,过长")
	private String bizRemark;

	// 业务流水号
	@Length(max = 32, message = "000002,过长")
	private String bizFlow;

	// 优先级(3最高 2高 1中 0普通)
	private Integer priority;

	// 0待发送、1发送中、2已发送
	private String status;

	// 备注
	private String remark;

	// 创建人
	private String creater;

	// 创建时间
	private String createTime;

	// 更新时间
	private String updateTime;

	/**
	 * 业务类型
	 */
	private String bizTypeNo;

	/**
	 * 第三方通道编码
	 */
	private String paySysNo;

	/**
	 * 业务系统编码
	 */
	private String bizSysNo;

	// 备用1
	private String spare1;

	// 备用2
	private String spare2;

	// 应用程序名称
	private String sendThreadName;

	// 是否拆单(1:拆单、0不拆单)该字段由转发程序判断进行修改。
	private Integer isSeparate;

	// 拆单数
	private Integer separateCount;

	/**
	 * 发送次数
	 */
	private Integer sendNum;

	// 是否需要推送 0不需要推送1需要推送
	private Integer isNeedPush;

	// 交易结果状态：000000交易成功 111111交易失败222222 交易处理中444444交易部分成功
	private String tradeStatus;

	// 交易结果描述
	private String tradeResultInfo;

	// 交易成功金额
	private BigDecimal tradeSuccessAmount;

	// 信息类别编码
	private String infoCategoryCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getBizSysAccountNo() {
		return bizSysAccountNo;
	}

	public void setBizSysAccountNo(String bizSysAccountNo) {
		this.bizSysAccountNo = bizSysAccountNo;
	}

	public String getZengdaiAccountNo() {
		return zengdaiAccountNo;
	}

	public void setZengdaiAccountNo(String zengdaiAccountNo) {
		this.zengdaiAccountNo = zengdaiAccountNo;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}

	public String getReveiverAccountName() {
		return reveiverAccountName;
	}

	public void setReveiverAccountName(String reveiverAccountName) {
		this.reveiverAccountName = reveiverAccountName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerBankCardNo() {
		return payerBankCardNo;
	}

	public void setPayerBankCardNo(String payerBankCardNo) {
		this.payerBankCardNo = payerBankCardNo;
	}

	public String getPayerBankCardType() {
		return payerBankCardType;
	}

	public void setPayerBankCardType(String payerBankCardType) {
		this.payerBankCardType = payerBankCardType;
	}

	public String getPayerIdType() {
		return payerIdType;
	}

	public void setPayerIdType(String payerIdType) {
		this.payerIdType = payerIdType;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerBankCode() {
		return payerBankCode;
	}

	public void setPayerBankCode(String payerBankCode) {
		this.payerBankCode = payerBankCode;
	}

	public String getPayerSubBankCode() {
		return payerSubBankCode;
	}

	public void setPayerSubBankCode(String payerSubBankCode) {
		this.payerSubBankCode = payerSubBankCode;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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
		this.bizRemark = bizRemark;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
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

	public String getSendThreadName() {
		return sendThreadName;
	}

	public void setSendThreadName(String sendThreadName) {
		this.sendThreadName = sendThreadName;
	}

	public Integer getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(Integer isSeparate) {
		this.isSeparate = isSeparate;
	}

	public Integer getSeparateCount() {
		return separateCount;
	}

	public void setSeparateCount(Integer separateCount) {
		this.separateCount = separateCount;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	public Integer getIsNeedPush() {
		return isNeedPush;
	}

	public void setIsNeedPush(Integer isNeedPush) {
		this.isNeedPush = isNeedPush;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeResultInfo() {
		return tradeResultInfo;
	}

	public void setTradeResultInfo(String tradeResultInfo) {
		this.tradeResultInfo = tradeResultInfo;
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
		this.infoCategoryCode = infoCategoryCode;
	}

	
}
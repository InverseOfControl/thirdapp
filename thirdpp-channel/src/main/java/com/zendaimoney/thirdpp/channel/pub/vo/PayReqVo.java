package com.zendaimoney.thirdpp.channel.pub.vo;

import java.math.BigDecimal;

public class PayReqVo extends BizReqVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3223072323658241321L;


	// 付款人账户号
	private String payerAccountNo;

	// 付款人账户姓名
	private String payerAccountName;

	// 收款人姓名
	private String receiverName;

	// 收款人银行卡号
	private String receiverBankCardNo;

	// 收款人银行卡类型
	private String receiverBankCardType;

	// 收款方证件类型(0=身份证1=户口簿2=护照3=军官证4=士兵证5=港澳居民来往内地通行证6=台湾同胞来往内地通行证7=临时身份证8=外国人居留证9=警官证X=其他证件)
	private String receiverIdType;

	// 收款方证件号
	private String receiverId;

	// 收款方银行编码
	private String receiverBankCode;

	// 收款方银行支行行号
	private String receiverSubBankCode;

	/**
	 * 收款方账户编号
	 */
	private String receiverAccountNo;

	// 币种(0人民币)
	private String currency;

	// 金额
	private BigDecimal amount;

	// 手续费
	private BigDecimal fee;

	// 业务备注
	private String bizRemark;

	// 业务流水号
	private String bizFlow;

	//交易流水号
	private String tradeFlow;
	
	// 收款人用户类型
	private String receiverType;


	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getPayerAccountName() {
		return payerAccountName;
	}

	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverBankCardNo() {
		return receiverBankCardNo;
	}

	public void setReceiverBankCardNo(String receiverBankCardNo) {
		this.receiverBankCardNo = receiverBankCardNo;
	}

	public String getReceiverBankCardType() {
		return receiverBankCardType;
	}

	public void setReceiverBankCardType(String receiverBankCardType) {
		this.receiverBankCardType = receiverBankCardType;
	}

	public String getReceiverIdType() {
		return receiverIdType;
	}

	public void setReceiverIdType(String receiverIdType) {
		this.receiverIdType = receiverIdType;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverBankCode() {
		return receiverBankCode;
	}

	public void setReceiverBankCode(String receiverBankCode) {
		this.receiverBankCode = receiverBankCode;
	}

	public String getReceiverSubBankCode() {
		return receiverSubBankCode;
	}

	public void setReceiverSubBankCode(String receiverSubBankCode) {
		this.receiverSubBankCode = receiverSubBankCode;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
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

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}
	
	public String getReceiverType() {
		return receiverType;
	}
	
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	
}

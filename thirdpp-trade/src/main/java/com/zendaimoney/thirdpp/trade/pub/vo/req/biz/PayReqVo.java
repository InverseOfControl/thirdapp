package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

import java.math.BigDecimal;

/**
 * 代付业务vo
 * 
 * @author 00237489
 *
 */
public class PayReqVo extends BizReqVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8548693786619625654L;

	/**
	 * 
	 */

	// 业务系统客户号
	private String bizSysAccountNo;

	// 证大客户号
	private String zengdaiAccountNo;

	// 付款人账户号
	private String payerAccountNo;

	// 付款人账户姓名
	private String payerAccountName;

	// 收款人姓名
	private String receiverName;

	// 收款人银行卡号
	private String receiverBankCardNo;

	// 收款人银行卡类型
	private String receiverBankCardType = "1";

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
	private String currency = "0";

	// 金额
	private BigDecimal amount;

	// 手续费
	private BigDecimal fee;

	// 业务备注
	private String bizRemark;

	// 业务流水号
	private String bizFlow;

	// 备注
	private String remark;

	// 创建人
	private String creater;

	// 是否需要推通知0不需要通知1需要通知
	private Integer isNeedPush = 1;

	// 报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单
	private Integer isNeedSpilt = 0;
	
	// 收款人用户类型
	private String receiverType;

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

	public Integer getIsNeedPush() {
		return isNeedPush;
	}

	public void setIsNeedPush(Integer isNeedPush) {
		this.isNeedPush = isNeedPush;
	}

	public Integer getIsNeedSpilt() {
		return isNeedSpilt;
	}

	public void setIsNeedSpilt(Integer isNeedSpilt) {
		this.isNeedSpilt = isNeedSpilt;
	}

	public String getReceiverType() {
		return receiverType;
	}
	
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
}

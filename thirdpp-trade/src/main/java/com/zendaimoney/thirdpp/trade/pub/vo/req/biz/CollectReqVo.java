package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

import java.math.BigDecimal;

/**
 * 代收业务vo
 * 
 * @author 00229019
 *
 */
public class CollectReqVo extends BizReqVo {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3461904239232520077L;

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
	private String payerBankCardType="1";

	// 付款方证件类型(0=身份证1=户口簿2=护照3=军官证4=士兵证5=港澳居民来往内地通行证6=台湾同胞来往内地通行证7=临时身份证8=外国人居留证9=警官证X=其他证件)
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
	private String payerAccountNo;
	
	/**
	 * 付款方手机号码
	 */
	private String payerMobile;

	// 币种(0人民币)
	private String currency="0";

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
	private Integer isNeedPush=1;
	
	//报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单
	private Integer isNeedSpilt=1;

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

	public String getPayerMobile() {
		return payerMobile;
	}

	public void setPayerMobile(String payerMobile) {
		this.payerMobile = payerMobile;
	}
	
	

}

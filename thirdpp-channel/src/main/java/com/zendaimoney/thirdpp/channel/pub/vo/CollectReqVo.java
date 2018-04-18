package com.zendaimoney.thirdpp.channel.pub.vo;

import java.math.BigDecimal;

/**
 * 代收业务vo
 * 
 * @author 00231257
 *
 */
public class CollectReqVo extends BizReqVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3461904239232520077L;

	// tpp系统付款方银行编码
	private String payerBankCode;

	// 付款人姓名
	private String payerName;

	// 付款人银行卡号
	private String payerBankCardNo;

	// 付款人银行卡类型
	private String payerBankCardType;

	// 付款方证件类型
	private String payerIdType;

	// 付款方证件号
	private String payerId;

	// 付款方银行支行行号
	private String payerSubBankCode;

	/**
	 * 付款方手机号码
	 */
	private String payerMobile;

	// 币种(0人民币)
	private String currency;

	// 金额
	private BigDecimal amount;

	// 手续费
	private BigDecimal fee;

	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 *  收款人账户号
	 */
	private String receiverAccountNo;
	
	/**
	 * 付款方账户编号
	 */
	private String payerAccountNo;
	
	/**
	 * 第三方支付平台接口编号
	 */
	private String funcId;

	public String getPayerBankCode() {
		return payerBankCode;
	}

	public void setPayerBankCode(String payerBankCode) {
		this.payerBankCode = payerBankCode;
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

	public String getPayerSubBankCode() {
		return payerSubBankCode;
	}

	public void setPayerSubBankCode(String payerSubBankCode) {
		this.payerSubBankCode = payerSubBankCode;
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

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getPayerMobile() {
		return payerMobile;
	}

	public void setPayerMobile(String payerMobile) {
		this.payerMobile = payerMobile;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	
}

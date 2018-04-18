package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 批量明细记录
 * @author 00233197
 *
 */
public class TranDetailReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 商户交易流水号
	 */
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String tradeFlow;
	
	
	/**
	 * 银行交易流水号
	 */
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankTradeFlow;
	
	/**
	 * 用户号
	 */
	@CmbAnnotation(index = 3, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String userNo;
	
	
	/**
	 * 虚拟子账户号
	 */
	@CmbAnnotation(index = 4, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String virtualSubNo;
	
	/**
	 * 交易金额
	 */
	@CmbAnnotation(index = 5, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amount;
	
	/**
	 * 交易类别
	 */
	@CmbAnnotation(index = 6, length = 2, rightFill = true, filler = Constants.BLANK, hex = false)
	private String tradeType;
	
	/**
	 * 交易摘要
	 */
	@CmbAnnotation(index = 7, length = 40, rightFill = true, filler = Constants.BLANK, hex = false)
	private String summary;
	
	/**
	 * 涉及本金
	 */
	@CmbAnnotation(index = 8, length = 15, rightFill = true, filler = Constants.BLANK, hex = false)
	private String principal;
	
	/**
	 * 关联信息
	 */
	@CmbAnnotation(index = 9, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String associatedInfo;
	
	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 10, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 银行账务日期
	 */
	@CmbAnnotation(index = 11, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankAccountDate;
	
	
	/**
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 12, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String msg;
	
	/**
	 * 备用1
	 */
	@CmbAnnotation(index = 13, length = 50, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare1;
	
	/**
	 * 备用2
	 */
	@CmbAnnotation(index = 14, length = 100, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare2;
	
	/**
	 * 备用3
	 */
	@CmbAnnotation(index = 15, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare3;

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getBankTradeFlow() {
		return bankTradeFlow;
	}

	public void setBankTradeFlow(String bankTradeFlow) {
		this.bankTradeFlow = bankTradeFlow;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getVirtualSubNo() {
		return virtualSubNo;
	}

	public void setVirtualSubNo(String virtualSubNo) {
		this.virtualSubNo = virtualSubNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getAssociatedInfo() {
		return associatedInfo;
	}

	public void setAssociatedInfo(String associatedInfo) {
		this.associatedInfo = associatedInfo;
	}


	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getBankAccountDate() {
		return bankAccountDate;
	}

	public void setBankAccountDate(String bankAccountDate) {
		this.bankAccountDate = bankAccountDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public String getSpare3() {
		return spare3;
	}

	public void setSpare3(String spare3) {
		this.spare3 = spare3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}

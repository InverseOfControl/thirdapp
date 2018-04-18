package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 批量冻结明细记录
 * @author 00233197
 *
 */
public class FrozDetailReq implements Serializable {

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
	 * 交易摘要
	 */
	@CmbAnnotation(index = 6, length = 40, rightFill = true, filler = Constants.BLANK, hex = false)
	private String summary;

	
	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 7, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 银行账务日期
	 */
	@CmbAnnotation(index = 8, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankAccountDate;
	
	
	/**
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 9, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String msg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 10, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}

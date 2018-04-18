package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;
import java.util.List;


/**
 * 批量转账
 * @author 00233197
 *
 */
public class TranBatchReq extends AttachmentReqVo<List<TranDetailReq>> {

	private static final long serialVersionUID = 1L;

	
	/**
	 * 银行交易流水号
	 */
	private String bankTradeFlow;
	
	/**
	 * 用户号
	 */
	private String userNo;
	
	
	/**
	 * 虚拟子账户号
	 */
	private String virtualSubNo;
	
	/**
	 * 交易金额
	 */
	private BigDecimal amount;
	
	/**
	 * 交易类别
	 */
	private String tradeType;
	
	/**
	 * 交易摘要
	 */
	private String summary;
	
	/**
	 * 涉及本金
	 */
	private String principal;
	
	/**
	 * 关联信息
	 */
	private String associatedInfo;
	
	/**
	 * 明细总笔数
	 */
	private int sum;
	
	/**
	 * 明细总金额
	 */
	private BigDecimal countAmount;
	
	
	/**
	 * 备用1
	 */
	private String spare1;
	
	/**
	 * 备用2
	 */
	private String spare2;
	
	/**
	 * 备用3
	 */
	private String spare3;


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

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public BigDecimal getCountAmount() {
		return countAmount;
	}

	public void setCountAmount(BigDecimal countAmount) {
		this.countAmount = countAmount;
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

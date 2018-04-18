package com.zendaimoney.thirdpp.channel.pub.vo;

import java.math.BigDecimal;

public class QueryReqVo extends BizReqVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8006129892776373296L;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 订单日期
	 */
	private String orderDate;
	
	/**
	 * 付款方账户编号
	 */
	private String payerAccountNo;

	/**
	 * 金额
	 */
	private BigDecimal amount;


	public String getTradeFlow() {
		return tradeFlow;
	}


	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}

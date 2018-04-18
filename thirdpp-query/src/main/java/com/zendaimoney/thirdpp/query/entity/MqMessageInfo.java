package com.zendaimoney.thirdpp.query.entity;

import java.io.Serializable;

public class MqMessageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 业务类型
	 */
	private String bizType;

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	@Override
	public String toString() {
		return "[MqMessageInfo]";
	}
	
}

package com.zendaimoney.thirdpp.notify.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 消息实体
 * @author 00225642
 *
 */
public class MqMessage {
	
	
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
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}

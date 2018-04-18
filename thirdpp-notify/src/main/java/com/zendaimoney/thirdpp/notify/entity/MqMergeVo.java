package com.zendaimoney.thirdpp.notify.entity;

/**
 * 任务合并业务对象
 * 
 * @author 00231257
 *
 */
public class MqMergeVo {

	/**
	 * 交易流水号
	 */
	private String tradeFlow;

	/**
	 * 业务类型
	 */
	private String bizType;

	public MqMergeVo() {

	}

	public MqMergeVo(String tradeFlow, String bizType) {
		this.tradeFlow = tradeFlow;
		this.bizType = bizType;
	}

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

}

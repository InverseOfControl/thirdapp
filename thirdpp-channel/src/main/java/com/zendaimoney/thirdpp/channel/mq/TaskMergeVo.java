package com.zendaimoney.thirdpp.channel.mq;

/**
 * 任务合并业务对象
 * 
 * @author 00231257
 *
 */
public class TaskMergeVo {

	/**
	 * 交易流水号
	 */
	private String tradeFlow;

	/**
	 * 业务类型
	 */
	private String bizType;

	public TaskMergeVo() {

	}

	public TaskMergeVo(String tradeFlow, String bizType) {
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

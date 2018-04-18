package com.zendaimoney.thirdpp.query.entity;

import java.math.BigDecimal;

public class PayInfo {
	
	/**
	 * 主键(对应SEQ_TPP_TRADE_INFO序列)
	 */
	private long id;
	
	/**
	 * 任务ID,对应代付业务任务表TPP_TRADE_T_PAY_TASK主键ID
	 */
	private String taskId;
	
	/**
	 * 请求ID,对应TPP_TRADE_T_REQUEST字段REQ_ID
	 */
	private String reqId;
	
	/**
	 * 支付渠道编号
	 */
	private String paySysNo;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 业务流水号
	 */
	private String bizFlow;
	
	/**
	 * 000000交易成功 111111交易失败 222222交易处理中  333333交易异常(交易在提交到支付平台前所发生错误都定义成交易异常)
	 */
	private String status;
	
	/**
	 * 更新时间
	 */
	private String updateTime;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 业务类型(002居间人模式代付003资金托管融资)
	 */
	private String bizType;
	
	/**
	 * 失败原因
	 */
	private String failReason;
	
	/**
	 * 渠道返回状态
	 */
	private String transRepCode;
	
	public String getTransRepCode() {
		return transRepCode;
	}
	
	public void setTransRepCode(String transRepCode) {
		this.transRepCode = transRepCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	
	
	public String getFailReason() {
		return failReason;
	}
	
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
}

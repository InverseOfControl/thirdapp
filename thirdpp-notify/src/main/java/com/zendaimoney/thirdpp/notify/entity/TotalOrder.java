package com.zendaimoney.thirdpp.notify.entity;

import java.math.BigDecimal;

/**
 * 汇总订单 ,非持久化
 * @author 00225642
 *
 */
public class TotalOrder {
	
	/**
	 * 任务ID
	 */
	private long taskId;
	
	/**
	 * 拆单数
	 */
	private int separateCount;
	
	/**
	 * 订单总笔数
	 */
	private int totalCount;
	
	/**
	 * 订单总金额
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 汇总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 交易成功笔数
	 */
	private int successCount;
	
	/**
	 * 交易成功金额
	 */
	private BigDecimal successAmount;
	
	/**
	 * 交易失败笔数
	 */
	private int failCount;
	
	/**
	 * 交易失败金额
	 */
	private BigDecimal failAmount;
	
	/**
	 * 交易中笔数
	 */
	private int tradingCount;
	
	/**
	 * 交易中金额
	 */
	private BigDecimal tradingAmount;
	
	/**
	 * 通知交易状态
	 */
	private String notifyStatus;
	
	/**
	 * 通知成功金额
	 */
	private BigDecimal notifySuccessAmt;
	
	/**
	 * 交易描述
	 */
	private String notifyDesc;
	
	/**
	 * 业务流水号
	 */
	private String bizFlow;
	
	/**
	 * 业务系统编码
	 */
	private String bizSysNo;
	
	/**
	 * 业务类型
	 */
	private String bizType;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 失败原因
	 */
	private String failReason;
	
	/**
	 * 是否需要推送
	 */
	private long isNeedPush;
	
	/**
	 * 优先级(3最高 2高 1中 0普通)
	 */
	private long priority;
	
	/**
	 * 通知URL
	 */
	private String notifyUrl;

	/**
	 * 第三方通道编码
	 */
	private String paySysNo;
	
	/**
	 * 商户号
	 */
	private String merId;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public BigDecimal getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(BigDecimal failAmount) {
		this.failAmount = failAmount;
	}


	public int getTradingCount() {
		return tradingCount;
	}

	public void setTradingCount(int tradingCount) {
		this.tradingCount = tradingCount;
	}

	public BigDecimal getTradingAmount() {
		return tradingAmount;
	}

	public void setTradingAmount(BigDecimal tradingAmount) {
		this.tradingAmount = tradingAmount;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getNotifyDesc() {
		return notifyDesc;
	}

	public void setNotifyDesc(String notifyDesc) {
		this.notifyDesc = notifyDesc;
	}

	public BigDecimal getNotifySuccessAmt() {
		return notifySuccessAmt;
	}

	public void setNotifySuccessAmt(BigDecimal notifySuccessAmt) {
		this.notifySuccessAmt = notifySuccessAmt;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
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

	public long getIsNeedPush() {
		return isNeedPush;
	}

	public void setIsNeedPush(long isNeedPush) {
		this.isNeedPush = isNeedPush;
	}

	public int getSeparateCount() {
		return separateCount;
	}

	public void setSeparateCount(int separateCount) {
		this.separateCount = separateCount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public long getPriority() {
		return priority;
	}
	
	public void setPriority(long priority) {
		this.priority = priority;
	}
	
	public String getNotifyUrl() {
		return notifyUrl;
	}
	
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	
	
}
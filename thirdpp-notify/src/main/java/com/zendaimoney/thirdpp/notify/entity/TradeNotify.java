package com.zendaimoney.thirdpp.notify.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易通知类
 * @author 00225642
 *
 */
public class TradeNotify implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long id;
	
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
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 通知状态:1待通知2通知中3已通知
	 */
	private String notifyStatus;
	
	/**
	 * 交易结果状态：000000交易成功 111111交易失败 444444交易部分成功
	 */
	private String tradeStatus;
	
	/**
	 * 交易结果描述
	 */
	private String tradeResultInfo;
	
	/**
	 * 任务ID,对应业务任务表主键ID
	 */
	private long taskId;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 交易成功金额
	 */
	private BigDecimal tradeSuccessAmount;
	
	/**
	 * 执行进程名称
	 */
	private String appName;
	
	/**
	 * 通知次数
	 */
	private long notifyCount;
	
	/**
	 * 失败原因
	 */
	private String failReason;
	
	/**
	 * 优先级(3最高 2高 1中 0普通)
	 */
	private long priority;
	
	/**
	 * 运营方式(0线下运营1线上运营)
	 */
	private String opMode;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeResultInfo() {
		return tradeResultInfo;
	}

	public void setTradeResultInfo(String tradeResultInfo) {
		this.tradeResultInfo = tradeResultInfo;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public BigDecimal getTradeSuccessAmount() {
		return tradeSuccessAmount;
	}

	public void setTradeSuccessAmount(BigDecimal tradeSuccessAmount) {
		this.tradeSuccessAmount = tradeSuccessAmount;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(long notifyCount) {
		this.notifyCount = notifyCount;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public long getPriority() {
		return priority;
	}
	
	public void setPriority(long priority) {
		this.priority = priority;
	}

	public String getOpMode() {
		return opMode;
	}

	public void setOpMode(String opMode) {
		this.opMode = opMode;
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

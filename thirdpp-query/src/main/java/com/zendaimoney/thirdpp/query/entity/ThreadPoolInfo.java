package com.zendaimoney.thirdpp.query.entity;

import java.io.Serializable;

public class ThreadPoolInfo implements Serializable {

	/**
	 * 默认UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 业务类型(001居间人模式代收002居间人模式代付003资金托管融资004资金托管还款005资金托管开户006资金托管绑卡007资金托管开户绑卡-008资金托管充值009资金托管提现010实名认证011线上充值012线上提现013线上支付014线上退款015资金托管转账)
	 */
	private String bizType;
	
	/**
	 * 系统编码
	 */
	private String infType;
	
	/**
	 * 支付通道编码
	 */
	private String paySysNo;
	
	/**
	 * 最小线程数
	 */
	private int minSize;
	
	/**
	 * 最大线程数
	 */
	private int maxSize;
	
	/**
	 * 队列数
	 */
	private int queueSize;
	
	/**
	 * 是否生效( 0不生效 1生效）
	 */
	private String isActive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getInfType() {
		return infType;
	}

	public void setInfType(String infType) {
		this.infType = infType;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}
	
	

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}

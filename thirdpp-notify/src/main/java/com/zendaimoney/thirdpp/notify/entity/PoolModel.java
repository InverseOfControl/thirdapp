package com.zendaimoney.thirdpp.notify.entity;

import java.io.Serializable;

public class PoolModel implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4374634992957563645L;

	/**
	 * 主键Id
	 */
	private long id;

	/**
	 * 业务类型
	 */
	private String bizType;

	/**
	 * 接口类型
	 */
	private String infType;

	/**
	 * 支付通道编码
	 */
	private String paySysNo;

	/**
	 * 最小线程数量
	 */
	private int minSize;

	/**
	 * 最大线程数量
	 */
	private int maxSize;

	/**
	 * 队列大小
	 */
	private int queueSize;

	/**
	 * 是否生效(0-失效1-生效)
	 */
	private String isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

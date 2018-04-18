package com.zendaimoney.thirdpp.query.entity;

import java.io.Serializable;

import com.google.common.collect.Sets;

public class TppsysQueryInfo implements Serializable {

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
	 * 支付通道编码
	 */
	private String paySysNo;
	
	/**
	 * 是否生效( 0不生效 1生效）
	 */
	private String isActive;
	
	/**
	 * 休眠时间
	 */
	private Long sleepTime;
	
	/**
	 * 进程名称
	 */
	private String AppName;

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


	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getSleepTime() {
		return sleepTime;
	}
	
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public String getAppName() {
		return AppName;
	}
	
	public void setAppName(String appName) {
		AppName = appName;
	}
}

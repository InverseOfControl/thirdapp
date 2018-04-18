package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 * 查询模块信息
 * 
 * @author 00231257
 *
 */
public class SysQueryInfo implements Serializable {

	// 是否生效-不生效
	public static final String IS_ACTIVE_CLOSED = "0";

	// 是否生效-生效
	public static final String IS_ACTIVE_OPENED = "1";

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9046066181779877562L;

	// 主键
	private Long id;

	// 业务类型编码
	private String bizTypeNo;

	// 第三方支付平台编码
	private String paySysNo;

	// 是否生效( 0不生效 1生效）
	private String isActive;

	// 应用程序名称
	private String appName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}

package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 * 业务系统对象
 * 
 * @author 00231257
 *
 */
public class SysApp implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3545662395864266816L;

	// 主键
	private long id;
	// 业务系统名称
	private String appName;
	// 业务系统编号
	private String appCode;
	// 线下代扣通知URL
	private String collectNotifyUrl;
	// 线下代付通知URL
	private String payNotifyUrl;
	// 线上退款通知URL
	private String cashBackNotifyUrl;
	// 线上提现通知URL
	private String cashDrawNotifyUrl;
	// 线上订单支付通知URL
	private String orderPayNotifyUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getCollectNotifyUrl() {
		return collectNotifyUrl;
	}

	public void setCollectNotifyUrl(String collectNotifyUrl) {
		this.collectNotifyUrl = collectNotifyUrl;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getCashBackNotifyUrl() {
		return cashBackNotifyUrl;
	}

	public void setCashBackNotifyUrl(String cashBackNotifyUrl) {
		this.cashBackNotifyUrl = cashBackNotifyUrl;
	}

	public String getCashDrawNotifyUrl() {
		return cashDrawNotifyUrl;
	}

	public void setCashDrawNotifyUrl(String cashDrawNotifyUrl) {
		this.cashDrawNotifyUrl = cashDrawNotifyUrl;
	}

	public String getOrderPayNotifyUrl() {
		return orderPayNotifyUrl;
	}

	public void setOrderPayNotifyUrl(String orderPayNotifyUrl) {
		this.orderPayNotifyUrl = orderPayNotifyUrl;
	}

}

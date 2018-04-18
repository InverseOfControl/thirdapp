package com.zendaimoney.thirdpp.alarm.common;

import java.io.Serializable;

public class UcSmsReceive implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8708041148942387486L;

	/**
	 * 发送人手机号码
	 */
	private String mobileFrom;

	/**
	 * 服务号码.
	 */
	private String mobileTo;

	/**
	 * 内容。
	 */
	private String body;

	/**
	 * 运营商
	 */
	private String cp;

	public String getMobileFrom() {
		return mobileFrom;
	}

	public void setMobileFrom(String mobileFrom) {
		this.mobileFrom = mobileFrom;
	}

	public String getMobileTo() {
		return mobileTo;
	}

	public void setMobileTo(String mobileTo) {
		this.mobileTo = mobileTo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

}

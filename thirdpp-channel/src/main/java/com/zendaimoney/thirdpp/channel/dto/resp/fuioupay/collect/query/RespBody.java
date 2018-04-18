package com.zendaimoney.thirdpp.channel.dto.resp.fuioupay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RespBody {
	/**
	 * 原请求日期
	 */
	@XmlElement(name = "merdt")
	private String merdt;

	/**
	 * 原请求流水
	 */
	@XmlElement(name = "orderno")
	private String orderno;

	/**
	 * 账号
	 */
	@XmlElement(name = "accntno")
	private String accntno;

	/**
	 * 账户名称
	 */
	@XmlElement(name = "accntnm")
	private String accntnm;

	/**
	 * 交易金额
	 */
	@XmlElement(name = "amt")
	private String amt;

	/**
	 * 交易状态
	 */
	@XmlElement(name = "state")
	private String state;
	
	/**
	 * 交易结果
	 */
	@XmlElement(name = "result")
	private String result;
	
	/**
	 * reason
	 */
	@XmlElement(name = "reason")
	private String reason;

	public String getMerdt() {
		return merdt;
	}

	public void setMerdt(String merdt) {
		this.merdt = merdt;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getAccntno() {
		return accntno;
	}

	public void setAccntno(String accntno) {
		this.accntno = accntno;
	}

	public String getAccntnm() {
		return accntnm;
	}

	public void setAccntnm(String accntnm) {
		this.accntnm = accntnm;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
}

package com.zendaimoney.thirdpp.channel.conf.allinpay;

import java.io.Serializable;

public class AllinpayConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4851855986667594999L;

	// 版本号.
	private String version;

	//证书路径
	private String certPath;
	// 级别
	private String level;

	// 报文提交url
	private String url;

	// 报文是否需要入库(1-需要 0-不需要)
	private String msgInStorage;
	
	//代收业务编码
	private String incomeBusinessCode;
	
	// 代收业务编码
	private String payforBusinessCode;
	
	// 通联新代扣接口地址
	private String url2;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getMsgInStorage() {
		return msgInStorage;
	}

	public void setMsgInStorage(String msgInStorage) {
		this.msgInStorage = msgInStorage;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}
	
	public String getIncomeBusinessCode() {
		return incomeBusinessCode;
	}
	
	public void setIncomeBusinessCode(String incomeBusinessCode) {
		this.incomeBusinessCode = incomeBusinessCode;
	}
	
	public String getPayforBusinessCode() {
		return payforBusinessCode;
	}
	
	public void setPayforBusinessCode(String payforBusinessCode) {
		this.payforBusinessCode = payforBusinessCode;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

}

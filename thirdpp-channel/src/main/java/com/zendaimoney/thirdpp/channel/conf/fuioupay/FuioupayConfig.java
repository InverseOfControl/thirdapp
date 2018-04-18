package com.zendaimoney.thirdpp.channel.conf.fuioupay;

import java.io.Serializable;

public class FuioupayConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4851855986667594999L;

	// 版本号.
	private String version;

	private String secretKey;

	// 报文是否需要入库(1-需要 0-不需要)
	private String msgInStorage;

	// 报文提交url
	private String url;

	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getMsgInStorage() {
		return msgInStorage;
	}

	public void setMsgInStorage(String msgInStorage) {
		this.msgInStorage = msgInStorage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



}

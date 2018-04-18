package com.zendaimoney.thirdpp.channel.conf.kjtpay;

import java.io.Serializable;

/**
 * 快捷通配置
 * @author 00233197
 *
 */
public class KjtpayConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6265252996930478246L;


	/**
	 * 代收URL
	 */
	private String collectingUrl;
	
	/**
	 * 代付URL
	 */
	private String payUrl;
	
	/**
	 * 代付查询URL
	 */
	private String payQueryUrl;
	
	/**
	 * 版本号
	 */
	private String version;
	

	/**
	 * 报文是否需要入库(1-需要 0-不需要)
	 */
	private String msgInStorage;
	
	/**
	 * key文件路径
	 */
	private String keyPath;


	public String getCollectingUrl() {
		return collectingUrl;
	}

	public void setCollectingUrl(String collectingUrl) {
		this.collectingUrl = collectingUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsgInStorage() {
		return msgInStorage;
	}

	public void setMsgInStorage(String msgInStorage) {
		this.msgInStorage = msgInStorage;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getPayQueryUrl() {
		return payQueryUrl;
	}

	public void setPayQueryUrl(String payQueryUrl) {
		this.payQueryUrl = payQueryUrl;
	}
	
	

}

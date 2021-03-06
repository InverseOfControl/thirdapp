package com.zendaimoney.thirdpp.channel.conf.unspay;

import java.io.Serializable;

/**
 * 银生宝配置
 * 
 * @author 00233197
 *
 */
public class UnspayConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6265252996930478246L;


	/**
	 * 代收URL
	 */
	private String collectingUrl;

	/**
	 * 代收查询URL
	 */
	private String collectingQrytransUrl;


	// 报文是否需要入库(1-需要 0-不需要)
	private String msgInStorage;

	/**
	 * 版本号
	 */
	private String version;



	public String getCollectingUrl() {
		return collectingUrl;
	}

	public void setCollectingUrl(String collectingUrl) {
		this.collectingUrl = collectingUrl;
	}

	public String getCollectingQrytransUrl() {
		return collectingQrytransUrl;
	}

	public void setCollectingQrytransUrl(String collectingQrytransUrl) {
		this.collectingQrytransUrl = collectingQrytransUrl;
	}

	public String getMsgInStorage() {
		return msgInStorage;
	}

	public void setMsgInStorage(String msgInStorage) {
		this.msgInStorage = msgInStorage;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}

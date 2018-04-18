package com.zendaimoney.thirdpp.channel.conf.baofoopay;

import java.io.Serializable;

/**
 * 银联配置
 * 
 * @author mencius
 *
 */
public class BaofoopayConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6265252996930478246L;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 终端号
	 */
	private String gateId;

	/**
	 * 银联代收URL
	 */
	private String collectingUrl;

	/**
	 * 银联代收查询URL
	 */
	private String collectingQrytransUrl;

	/**
	 * 公钥文件路径
	 */
	private String publicKey;

	/**
	 * key文件路径
	 */
	private String keyPath;

	// 报文是否需要入库(1-需要 0-不需要)
	private String msgInStorage;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

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

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getMsgInStorage() {
		return msgInStorage;
	}

	public void setMsgInStorage(String msgInStorage) {
		this.msgInStorage = msgInStorage;
	}


}

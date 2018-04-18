package com.zendaimoney.thirdpp.channel.conf.shunionpay;

import java.io.Serializable;

/**
 * 银联配置
 * 
 * @author mencius
 *
 */
public class ShunionpayConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6265252996930478246L;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 网关号
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

	// 账户验证需要，系统号
	private String appSysId;

	// 账户验证需要，密钥
	private String secretKey;
	
	// 实名认证第三方地址
	private String authUrl;
	
	// 卡bin查询第三方地址
	private String cardBinUrl;
	
	// 代付版本号
	private String payVersion;
	
	// 代付第三方地址
	private String payingUrl;

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

	public String getAppSysId() {
		return appSysId;
	}

	public void setAppSysId(String appSysId) {
		this.appSysId = appSysId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getCardBinUrl() {
		return cardBinUrl;
	}

	public void setCardBinUrl(String cardBinUrl) {
		this.cardBinUrl = cardBinUrl;
	}

	public String getPayVersion() {
		return payVersion;
	}

	public void setPayVersion(String payVersion) {
		this.payVersion = payVersion;
	}

	public String getPayingUrl() {
		return payingUrl;
	}

	public void setPayingUrl(String payingUrl) {
		this.payingUrl = payingUrl;
	}

	

}

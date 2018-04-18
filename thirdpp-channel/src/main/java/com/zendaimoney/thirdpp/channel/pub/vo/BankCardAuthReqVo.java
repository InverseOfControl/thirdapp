package com.zendaimoney.thirdpp.channel.pub.vo;

/**
 * 银行卡认证业务对象
 * 
 * @author 00231257
 *
 */
public class BankCardAuthReqVo extends BizReqVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6252773084003959151L;

	// 银行卡号
	private String cardNo;

	// 银行卡类型
	private String cardType;

	// 证件类型
	private String certType;

	// 证件号
	private String certNo;

	// 姓名
	private String userName;

	// 预留手机号码
	private String mobile;
	
	/**
	 * 认证流水号
	 */
	private String authFlow;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getAuthFlow() {
		return authFlow;
	}
	
	public void setAuthFlow(String authFlow) {
		this.authFlow = authFlow;
	}

}

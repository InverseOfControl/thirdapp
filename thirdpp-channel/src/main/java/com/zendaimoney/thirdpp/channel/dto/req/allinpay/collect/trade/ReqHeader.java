package com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqHeader {

	/**
	 * 交易代碼
	 */
	@XmlElement(name = "TRX_CODE")
	private String trxCode = "100011";

	/**
	 * 版本
	 */
	@XmlElement(name = "VERSION")
	private String version = "";

	/**
	 * 数据格式,2：xml格式
	 */
	@XmlElement(name = "DATA_TYPE")
	private String dataType = "2";

	/**
	 * 处理级别,0-9 0优先级最低
	 */
	@XmlElement(name = "LEVEL")
	private String level = "";

	/**
	 * 用户名
	 */
	@XmlElement(name = "USER_NAME")
	private String userName = "";

	/**
	 * 用户密码
	 */
	@XmlElement(name = "USER_PASS")
	private String userPass = "";

	/**
	 * 交易批次号
	 */
	@XmlElement(name = "REQ_SN")
	private String reqSn = "";

	/**
	 * 签名信息
	 */
	@XmlElement(name = "SIGNED_MSG")
	private String signedMsg = "";

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		if (trxCode != null) {
			this.trxCode = trxCode;
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		if (version != null) {
			this.version = version;
		}
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		if (dataType != null) {
			this.dataType = dataType;
		}
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		if (level != null) {
			this.level = level;
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (userName != null) {
			this.userName = userName;
		}
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		if (userPass != null) {
			this.userPass = userPass;
		}
	}

	public String getReqSn() {
		return reqSn;
	}

	public void setReqSn(String reqSn) {
		if (reqSn != null) {
			this.reqSn = reqSn;
		}
	}

	public String getSignedMsg() {
		return signedMsg;
	}

	public void setSignedMsg(String signedMsg) {
		if (signedMsg != null) {
			this.signedMsg = signedMsg;
		}
	}

}

package com.zendaimoney.thirdpp.channel.dto.req.allinpay.pay.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqBody {
	/**
	 * 业务代码
	 */
	@XmlElement(name = "BUSINESS_CODE")
	private String businessCode = "";

	/**
	 * 商户代码
	 */
	@XmlElement(name = "MERCHANT_ID")
	private String merchantId = "";

	/**
	 * 提交时间,YYYYMMDDHHMMSS
	 */
	@XmlElement(name = "SUBMIT_TIME")
	private String submitTime = "";

	/**
	 * 用户编号, 客户编号,开发人员可当作备注字段
	 */
	@XmlElement(name = "E_USER_CODE")
	private String eUserCode = "";

	/**
	 * 有效期,YYYYMMDD，用于信用卡
	 */
	@XmlElement(name = "VALIDATE")
	private String validate = "";

	/**
	 * 信用卡CVV2,仅用于信用卡
	 */
	@XmlElement(name = "CVV2")
	private String cvv2 = "";

	/**
	 * 银行代码,强烈建议填写银行代码
	 */
	@XmlElement(name = "BANK_CODE")
	private String bankCode = "";

	/**
	 * 账号类型,00银行卡，01存折，02信用卡。不填默认为银行卡00。
	 */
	@XmlElement(name = "ACCOUNT_TYPE")
	private String accountType = "00";

	/**
	 * 账号,银行卡或存折号码
	 */
	@XmlElement(name = "ACCOUNT_NO")
	private String accountNo = "";

	/**
	 * 账号名
	 */
	@XmlElement(name = "ACCOUNT_NAME")
	private String accountName = "";

	/**
	 * 账号属性,0私人，1公司。不填时，默认为私人0
	 */
	@XmlElement(name = "ACCOUNT_PROP")
	private String accountProp = "0";

	/**
	 * 金额,整数，单位分
	 */
	@XmlElement(name = "AMOUNT")
	private String amount = "";

	/**
	 * 货币类型,人民币：CNY, 港元：HKD，美元：USD。不填时，默认为人民币。
	 */
	@XmlElement(name = "CURRENCY")
	private String currency = "CNY";

	/**
	 * 开户证件类型 0：身份证,1: 户口簿，2：护照,3.军官证,4.士兵证，5. 港澳居民来往内地通行证,6. 台湾同胞来往内地通行证,7.
	 * 临时身份证,8. 外国人居留证,9. 警官证, X.其他证件
	 */
	@XmlElement(name = "ID_TYPE")
	private String idType = "";

	/**
	 * 证件号
	 */
	@XmlElement(name = "ID")
	private String id = "";

	/**
	 * 本交易结算户,结算到商户的账户，不需分别清算时不需填写。
	 */
	@XmlElement(name = "SETTACCT")
	private String settacct = "";

	/**
	 * 手机号/小灵通
	 */
	@XmlElement(name = "TEL")
	private String tel = "";

	/**
	 * 自定义用户号,商户自定义的用户号，开发人员可当作备注字段使用
	 */
	@XmlElement(name = "CUST_USERID")
	private String custUserId = "";

	/**
	 * 分组清算标志,特殊商户使用，普通商户不要填
	 */
	@XmlElement(name = "SETTGROUPFLAG")
	private String settGroupFlag = "";

	/**
	 * 交易附言,填入网银的交易备注
	 */
	@XmlElement(name = "SUMMARY")
	private String summary = "";

	/**
	 * 备注
	 */
	@XmlElement(name = "REMARK")
	private String remark = "";

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		if (businessCode != null) {
			this.businessCode = businessCode;
		}
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		if (merchantId != null) {
			this.merchantId = merchantId;
		}
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		if (submitTime != null) {
			this.submitTime = submitTime;
		}
	}

	public String geteUserCode() {
		return eUserCode;
	}

	public void seteUserCode(String eUserCode) {
		if (eUserCode != null) {
			this.eUserCode = eUserCode;
		}
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		if (validate != null) {
			this.validate = validate;
		}
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		if (cvv2 != null) {
			this.cvv2 = cvv2;
		}
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		if (bankCode != null) {
			this.bankCode = bankCode;
		}
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		if (accountType != null) {
			this.accountType = accountType;
		}
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		if (accountNo != null) {
			this.accountNo = accountNo;
		}
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		if (accountName != null) {
			this.accountName = accountName;
		}
	}

	public String getAccountProp() {
		return accountProp;
	}

	public void setAccountProp(String accountProp) {
		if (accountProp != null) {
			this.accountProp = accountProp;
		}
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		if (amount != null) {
			this.amount = amount;
		}
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		if (currency != null) {
			this.currency = currency;
		}
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		if (idType != null) {
			this.idType = idType;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id != null) {
			this.id = id;
		}
	}

	public String getSettacct() {
		return settacct;
	}

	public void setSettacct(String settacct) {
		if (settacct != null) {
			this.settacct = settacct;
		}
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		if (tel != null) {
			this.tel = tel;
		}
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		if (custUserId != null) {
			this.custUserId = custUserId;
		}
	}

	public String getSettGroupFlag() {
		return settGroupFlag;
	}

	public void setSettGroupFlag(String settGroupFlag) {
		if (settGroupFlag != null) {
			this.settGroupFlag = settGroupFlag;
		}
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		if (summary != null) {
			this.summary = summary;
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		if (remark != null) {
			this.remark = remark;
		}
	}

}

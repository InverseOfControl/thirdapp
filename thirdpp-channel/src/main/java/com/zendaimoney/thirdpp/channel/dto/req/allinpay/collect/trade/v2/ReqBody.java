package com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 通联代扣接口310011请求体数据对象
 * 
 * @author gaohx
 */
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
	 * 协议号-签约时返回的协议号
	 */
	@XmlElement(name = "AGRMNO")
	private String agrmno = "";

	/**
	 * 账号-借记卡或信用卡
	 */
	@XmlElement(name = "ACCOUNT_NO")
	private String accountNo = "";
	
	/**
	 * 账号名
	 */
	@XmlElement(name = "ACCOUNT_NAME")
	private String accountName = "";
	
	/**
	 * 金额
	 */
	@XmlElement(name = "AMOUNT")
	private String amount = "";
	
	/**
	 * 货币类型
	 */
	@XmlElement(name = "CURRENCY")
	private String currency = "CNY";
	
	/**
	 * 开户证件类型
	 */
	@XmlElement(name = "ID_TYPE")
	private String idType = "";
	
	/**
	 * 证件号
	 */
	@XmlElement(name = "ID")
	private String id = "";
	
	/**
	 * 手机号/小灵通
	 */
	@XmlElement(name = "TEL")
	private String tel = "";
	
	/**
	 * 信用卡CVV2,仅用于信用卡
	 */
	@XmlElement(name = "CVV2")
	private String cvv2 = "";
	
	/**
	 * 有效期,YYYYMMDD，用于信用卡
	 */
	@XmlElement(name = "VALIDATE")
	private String validate = "";
	
	/**
	 * 自定义用户号,商户自定义的用户号，开发人员可当作备注字段使用
	 */
	@XmlElement(name = "CUST_USERID")
	private String custUserId = "";
	
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
		if(businessCode != null){
			this.businessCode = businessCode;
		}
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		if(merchantId != null){
			this.merchantId = merchantId;
		}
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		if(submitTime != null){
			this.submitTime = submitTime;
		}
	}

	public String getAgrmno() {
		return agrmno;
	}

	public void setAgrmno(String agrmno) {
		if(agrmno != null){
			this.agrmno = agrmno;
		}
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		if(accountNo != null){
			this.accountNo = accountNo;
		}
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		if(accountName != null){
			this.accountName = accountName;
		}
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		if(amount != null){
			this.amount = amount;
		}
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		if(currency != null){
			this.currency = currency;
		}
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		if(idType != null){
			this.idType = idType;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id != null){
			this.id = id;
		}
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		if(tel != null){
			this.tel = tel;
		}
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		if(cvv2 != null){
			this.cvv2 = cvv2;
		}
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		if(validate != null){
			this.validate = validate;
		}
	}

	public String getCustUserId() {
		return custUserId;
	}

	public void setCustUserId(String custUserId) {
		if(custUserId != null){
			this.custUserId = custUserId;
		}
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		if(summary != null){
			this.summary = summary;
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		if(remark != null){
			this.remark = remark;
		}
	}
	
}

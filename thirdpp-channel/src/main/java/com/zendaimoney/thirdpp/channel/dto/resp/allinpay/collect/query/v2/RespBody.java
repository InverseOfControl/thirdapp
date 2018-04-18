package com.zendaimoney.thirdpp.channel.dto.resp.allinpay.collect.query.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RespBody {

	/**
	 * 交易批次号
	 */
	@XmlElement(name = "BATCHID")
	private String batchId;

	/**
	 * 记录序号
	 */
	@XmlElement(name = "SN")
	private String sn;

	/**
	 * 交易方向 0 付 1收
	 */
	@XmlElement(name = "TRXDIR")
	private String trxdIr;

	/**
	 * 清算日期
	 */
	@XmlElement(name = "SETTDAY")
	private String settday;

	/**
	 * 完成时间
	 */
	@XmlElement(name = "FINTIME")
	private String finTime;

	/**
	 * 提交时间
	 */
	@XmlElement(name = "SUBMITTIME")
	private String submitTime;

	/**
	 * 账号
	 */
	@XmlElement(name = "ACCOUNT_NO")
	private String accountNo;

	/**
	 * 账号名
	 */
	@XmlElement(name = "ACCOUNT_NAME")
	private String accountName;

	/**
	 * 金额
	 */
	@XmlElement(name = "AMOUNT")
	private String amount;

	/**
	 * 自定义用户号
	 */
	@XmlElement(name = "CUST_USERID")
	private String custUserid;

	/**
	 * 备注
	 */
	@XmlElement(name = "REMARK")
	private String remark;

	/**
	 * 交易附言
	 */
	@XmlElement(name = "SUMMARY")
	private String summary;

	/**
	 * 返回码
	 */
	@XmlElement(name = "RET_CODE")
	private String retCode;

	/**
	 * 错误文本
	 */
	@XmlElement(name = "ERR_MSG")
	private String errMsg;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getTrxdIr() {
		return trxdIr;
	}

	public void setTrxdIr(String trxdIr) {
		this.trxdIr = trxdIr;
	}

	public String getSettday() {
		return settday;
	}

	public void setSettday(String settday) {
		this.settday = settday;
	}

	public String getFinTime() {
		return finTime;
	}

	public void setFinTime(String finTime) {
		this.finTime = finTime;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCustUserid() {
		return custUserid;
	}

	public void setCustUserid(String custUserid) {
		this.custUserid = custUserid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}






}

package com.zdmoney.manager.models;
import java.io.Serializable;
import java.util.Date;
public class TPPOpenAccountCards implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1836505590613187666L;
	private Integer id; 
	private String thirdAccountNo;
	private String bankCardNo;
	private String bankCardName;
	private String bankCardType;
	private String bankCode;
	private String bankCodeName;
	private String status;
	private String reserveMobile;	 
	private Date createTime;
	private Date updateTime;
	private String paySysNo;
	private String reqId;
	private String spare1;
	private String spare2;
	private String openBankCode;
	private String openBankName;
	private String paySysName;
	private String bankName;
	private String zengdaiAccountNo;
	private String bizSysNo;
	private String bizSysAccountNo;
	public String getPaySysName() {
		return paySysName;
	}
	public void setPaySysName(String paySysName) {
		this.paySysName = paySysName;
	}
	public String getBankCodeName() {
		return bankCodeName;
	}
	public void setBankCodeName(String bankCodeName) {
		this.bankCodeName = bankCodeName;
	}
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getThirdAccountNo() {
		return thirdAccountNo;
	}
	public void setThirdAccountNo(String thirdAccountNo) {
		this.thirdAccountNo = thirdAccountNo;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getBankCardType() {
		return bankCardType;
	}
	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReserveMobile() {
		return reserveMobile;
	}
	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getPaySysNo() {
		return paySysNo;
	}
	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getSpare1() {
		return spare1;
	}
	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}
	public String getSpare2() {
		return spare2;
	}
	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}
	public String getOpenBankCode() {
		return openBankCode;
	}
	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getZengdaiAccountNo() {
		return zengdaiAccountNo;
	}
	public void setZengdaiAccountNo(String zengdaiAccountNo) {
		this.zengdaiAccountNo = zengdaiAccountNo;
	}
	public String getBizSysNo() {
		return bizSysNo;
	}
	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}
	public String getBizSysAccountNo() {
		return bizSysAccountNo;
	}
	public void setBizSysAccountNo(String bizSysAccountNo) {
		this.bizSysAccountNo = bizSysAccountNo;
	}
	
}

package com.zdmoney.manager.models;
import java.io.Serializable;
import java.util.Date;
public class TPPOperationRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7049534665587800909L;
	private Integer reqId;
	private String bizTypeNo;
	private String bizSysNo;
	private String bizSysName;
	private String bizSysAccountNo;
	private String zengDaiAccountNo;
	private String realName;
	private String gender;
	private String nation;
	private String mobile;
	private String reserveMobile;
	private String bankCardNo;
	private String bankCardName;
	private String bankCardBType;
	private String bankCode;
	private String bankName;
	private String paySysNo;
	private String paySysName;
	private String status;
	private String idType;
	private String idNo;
	private Date updateTime;
	private String ip;
	private String creater;
	private Date createTime;
	private String bizFlow;
	private String isSync;
	private String spare1;
	private String spare2;
	private String openBankCode;
	private String payTransFlow;
	private String transferFlow;
	private Date respTime;
	private String respInfo;
	private String respCode;
	private String thirdAccountNo;
	private String mac;
	private String infoCategoryCode;
	private String infoCategoryName;
	public String getBankCardName() {
		return bankCardName;
	}
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getBizSysName() {
		return bizSysName;
	}
	public void setBizSysName(String bizSysName) {
		this.bizSysName = bizSysName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getPaySysName() {
		return paySysName;
	}
	public void setPaySysName(String paySysName) {
		this.paySysName = paySysName;
	}
	public String getInfoCategoryName() {
		return infoCategoryName;
	}
	public void setInfoCategoryName(String infoCategoryName) {
		this.infoCategoryName = infoCategoryName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Integer getReqId() {
		return reqId;
	}
	public void setReqId(Integer reqId) {
		this.reqId = reqId;
	}
	public String getBizTypeNo() {
		return bizTypeNo;
	}
	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
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
	public String getZengDaiAccountNo() {
		return zengDaiAccountNo;
	}
	public void setZengDaiAccountNo(String zengDaiAccountNo) {
		this.zengDaiAccountNo = zengDaiAccountNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReserveMobile() {
		return reserveMobile;
	}
	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardBType() {
		return bankCardBType;
	}
	public void setBankCardBType(String bankCardBType) {
		this.bankCardBType = bankCardBType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPaySysNo() {
		return paySysNo;
	}
	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBizFlow() {
		return bizFlow;
	}
	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}
	public String getIsSync() {
		return isSync;
	}
	public void setIsSync(String isSync) {
		this.isSync = isSync;
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
	public String getPayTransFlow() {
		return payTransFlow;
	}
	public void setPayTransFlow(String payTransFlow) {
		this.payTransFlow = payTransFlow;
	}
	public String getTransferFlow() {
		return transferFlow;
	}
	public void setTransferFlow(String transferFlow) {
		this.transferFlow = transferFlow;
	}
	public Date getRespTime() {
		return respTime;
	}
	public void setRespTime(Date respTime) {
		this.respTime = respTime;
	}
	public String getRespInfo() {
		return respInfo;
	}
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getThirdAccountNo() {
		return thirdAccountNo;
	}
	public void setThirdAccountNo(String thirdAccountNo) {
		this.thirdAccountNo = thirdAccountNo;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}
	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
}

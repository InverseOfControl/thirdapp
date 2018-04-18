package com.zdmoney.manager.models;
import java.io.Serializable;
import java.util.Date;
public class TPPOpenAccount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4810679545601686991L;
	private Integer id; 
	private String zengDaiAccountNo;
	private String bizSysNo;
	private String bizSysName;
	private String bizSysAccountNo;
	private String thirdAccountNo;
	private String userName;
	private String userPwd;
	private String realName;
	private String gender;
	private String nation;
	private String mobile;
	private String paySysNo;
	private String paySysName;
	private String status;
	private String idType;
	private String idNo;
	private Date openTime;
	private Date createTime;
	private String reqId;
	private Date updateTime;
	private String spare1;
	private String spare2;
	private String openTime1;
	private String createTime1;
	private String updateTime1;
	public String getPaySysName() {
		return paySysName;
	}
	public void setPaySysName(String paySysName) {
		this.paySysName = paySysName;
	}
	public String getOpenTime1() {
		return openTime1;
	}
	public void setOpenTime1(String openTime1) {
		this.openTime1 = openTime1;
	}
	public String getCreateTime1() {
		return createTime1;
	}
	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}
	public String getUpdateTime1() {
		return updateTime1;
	}
	public void setUpdateTime1(String updateTime1) {
		this.updateTime1 = updateTime1;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZengDaiAccountNo() {
		return zengDaiAccountNo;
	}
	public void setZengDaiAccountNo(String zengDaiAccountNo) {
		this.zengDaiAccountNo = zengDaiAccountNo;
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
	public String getThirdAccountNo() {
		return thirdAccountNo;
	}
	public void setThirdAccountNo(String thirdAccountNo) {
		this.thirdAccountNo = thirdAccountNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
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
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	public String getBizSysName() {
		return bizSysName;
	}
	public void setBizSysName(String bizSysName) {
		this.bizSysName = bizSysName;
	}
	
}

package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TChannelInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4690042117713647221L;
	private Integer id; 
	@NotBlank(message = "通道名称不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 100, message = "通道名称超过100个字节") 	
	private String channelName;
	@NotBlank(message = "第三方通道不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 10, message = "第三方通道超过10个字节") 	
	private String thirdTypeNo;
	@NotBlank(message = "请选择商户类型", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 5, message = "商户类型超过5个字节") 	
	private String merchantType;
	@Length(max = 100, message = "用户名超过100个字节") 	
	private String userName;
	@Length(max = 100, message = "用户密码超过100个字节") 	
	private String userPwd;
	@Length(max = 100, message = "证书名称超过100个字节") 
	private String certificateName;
	@Length(max = 100, message = "证书密码超过100个字节") 
	private String certificatePwd;
	@Length(max = 100, message = "商户号超过100个字节") 
	private String certificateNo;
	@NotBlank(message = "请选择状态", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	private String status;
	private Date createTime;
	private String creater;
	private Date updateTime;
	private String updater;
	private String noteNo;
 
	private String noText;
	
	private String thirdTypeName;
	public String getThirdTypeName() {
		return thirdTypeName;
	}
	public void setThirdTypeName(String thirdTypeName) {
		this.thirdTypeName = thirdTypeName;
	}
	public String getNoText() {
		return noText;
	}
	public void setNoText(String noText) {
		this.noText = noText;
	}
	public String getNoteNo() {
		return noteNo;
	}
	public void setNoteNo(String noteNo) {
		this.noteNo = noteNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getThirdTypeNo() {
		return thirdTypeNo;
	}
	public void setThirdTypeNo(String thirdTypeNo) {
		this.thirdTypeNo = thirdTypeNo;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
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
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public String getCertificatePwd() {
		return certificatePwd;
	}
	public void setCertificatePwd(String certificatePwd) {
		this.certificatePwd = certificatePwd;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	 
	

}

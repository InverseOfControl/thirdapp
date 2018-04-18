package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TSysApp implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8507511880571142224L;
	private Integer id;
	@NotBlank(message = "业务系统名称不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 100, message = "业务系统名称超过64个字节") 
	private String appName;
	@NotBlank(message = "业务系统编号不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 4, message = "业务系统编号超过4个字节") 	
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="业务系统编号只能输入数字和字母") 
	private String appCode;
	@NotBlank(message = "请选择状态", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	private String isFlag;
	
	private String contactName;
	
	private String contactMobile;
	
	private String remark;
	
	private Date createTime;
	
	private String creater;
	
	private Date updateTime;
	
	private String updater;
	
	private String spare1;
	
	private String spare2;
	
	private String  collectNotifyUrl;
	
	private String  payNotifyUrl;
	private String  cashBackNotifyUrl;
	private String  cashDrawNotifyUrl;
	private String  orderPayNotifyUrl;
	
	 
	
	
	
	public String getCollectNotifyUrl() {
		return collectNotifyUrl;
	}

	public void setCollectNotifyUrl(String collectNotifyUrl) {
		this.collectNotifyUrl = collectNotifyUrl;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getCashBackNotifyUrl() {
		return cashBackNotifyUrl;
	}

	public void setCashBackNotifyUrl(String cashBackNotifyUrl) {
		this.cashBackNotifyUrl = cashBackNotifyUrl;
	}

	public String getCashDrawNotifyUrl() {
		return cashDrawNotifyUrl;
	}

	public void setCashDrawNotifyUrl(String cashDrawNotifyUrl) {
		this.cashDrawNotifyUrl = cashDrawNotifyUrl;
	}

	public String getOrderPayNotifyUrl() {
		return orderPayNotifyUrl;
	}

	public void setOrderPayNotifyUrl(String orderPayNotifyUrl) {
		this.orderPayNotifyUrl = orderPayNotifyUrl;
	}

	private String noteNo;
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

 


}

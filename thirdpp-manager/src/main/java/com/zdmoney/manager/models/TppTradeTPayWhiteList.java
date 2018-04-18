package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

public class TppTradeTPayWhiteList implements Serializable {

	private static final long serialVersionUID = -5482636629444201143L;
	
	// 主键 ID
	private long id;
	// 信息类别编码
	private String infoCategoryCode;
	// 银行卡号
	private String bankCardNo;
	// 账号
	private String accountNo;
	// 状态:0无效1生效
	private int status;
	// 业务系统号
	private String bizSysNo;
	// 备注
	private String note;
	// 创建人
	private String creater;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
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

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}
	
}

package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 *业务系统和IP对应关系对象
 * 
 * @author 00231257
 *
 */
public class TppWhiteList implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8020001936781255579L;

	// 主键
	private long id;

	// 信息类别
	private String infoCategoryCode;
	
	// 银行卡号
	private String bankCardNo;

	// 账号
	private String accountNo;
	
	// 状态:0无效1生效
	private String status;
	
	// 备注
	private String note;
	
	// 业务系统号
	private String bizSysNo;
	
	// 创建时间
	private String createTime;

	// 修改时间
	private String updateTime;

	// 创建人
	private String creater;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	

}

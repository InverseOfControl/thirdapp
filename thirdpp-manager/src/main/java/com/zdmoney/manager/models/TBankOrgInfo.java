package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TBankOrgInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5738963970491087990L;
	private Integer id;
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="银行机构编码只能输入数字和字母") 
 	@NotBlank(message = "银行机构行号为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 15, message = "银行机构行号超过4个字节") 
	private String bankOrgNo;
	@NotBlank(message = "银行机构名称为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 100, message = "银行机构名称超过100个字节")
	private String bankOrgName;
	private Date dtTime;
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="银行编码只能输入数字和字母") 
	@NotBlank(message = "银行编码为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 15, message = "银行编码超过10个字节")
	private String bankCode;
	private String bankName; 
	@NotBlank(message = "请选择机构所在省份", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	private String bankOrgProvinceNo;
	private String bankOrgProvinceCityNo;
	private String bankOrgProvinceName;
	private String bankOrgProvinceCityName;
	private String note;
	@Pattern(regexp="[0-9]+",message="支付联行号含非数字")
 	@NotBlank(message = "支付联行号为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 20, message = "支付联行号超过20个字节") 
	private String bankLineNo;
	public String getBankLineNo() {
		return bankLineNo;
	}
	public void setBankLineNo(String bankLineNo) {
		this.bankLineNo = bankLineNo;
	}
	//备用字段1 自用
	private String noteOrgNo;
	//备用字段2 自用
	private String noteOrgNo2;
	public String getNoteOrgNo2() {
		return noteOrgNo2;
	}
	public void setNoteOrgNo2(String noteOrgNo2) {
		this.noteOrgNo2 = noteOrgNo2;
	}
	public String getNoteOrgNo() {
		return noteOrgNo;
	}
	public void setNoteOrgNo(String noteOrgNo) {
		this.noteOrgNo = noteOrgNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBankOrgNo() {
		return bankOrgNo;
	}
	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}
	public String getBankOrgName() {
		return bankOrgName;
	}
	public void setBankOrgName(String bankOrgName) {
		this.bankOrgName = bankOrgName;
	}
	public Date getDtTime() {
		return dtTime;
	}
	public void setDtTime(Date dtTime) {
		this.dtTime = dtTime;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankOrgProvinceNo() {
		return bankOrgProvinceNo;
	}
	public void setBankOrgProvinceNo(String bankOrgProvinceNo) {
		this.bankOrgProvinceNo = bankOrgProvinceNo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankOrgProvinceName() {
		return bankOrgProvinceName;
	}
	public void setBankOrgProvinceName(String bankOrgProvinceName) {
		this.bankOrgProvinceName = bankOrgProvinceName;
	}
	public String getBankOrgProvinceCityName() {
		return bankOrgProvinceCityName;
	}
	public void setBankOrgProvinceCityName(String bankOrgProvinceCityName) {
		this.bankOrgProvinceCityName = bankOrgProvinceCityName;
	}
	public String getBankOrgProvinceCityNo() {
		return bankOrgProvinceCityNo;
	}
	public void setBankOrgProvinceCityNo(String bankOrgProvinceCityNo) {
		this.bankOrgProvinceCityNo = bankOrgProvinceCityNo;
	}
}

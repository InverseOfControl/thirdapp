package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TBankInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1698202884027832460L;

	private Integer id;
	
	@Length(max = 10, message = "银行编码超过4个字节")
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="银行编码只能输入数字和字母") 
	@NotBlank(message = "银行编码不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	private String bankCode;
 	@NotBlank(message = "银行名称不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 100, message = "银行名称超过100个字节") 
	private String bankName;
	private Date dtTime;
	@Length(max = 200, message = "备注超过200个字节")
	private String note;
	/**
	 * 自用字段
	 */
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Date getDtTime() {
		return dtTime;
	}

	public void setDtTime(Date dtTime) {
		this.dtTime = dtTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


}

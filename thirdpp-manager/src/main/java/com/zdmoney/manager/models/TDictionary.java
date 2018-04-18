package com.zdmoney.manager.models;

 

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TDictionary implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6520632243799082704L;
	private Integer id;
	@Length(max = 10, message = "编码超过10个字节")
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="编码只能输入数字和字母") 
	@NotBlank(message = "编码不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	private String dicCode;
	@Length(max = 50, message = "名称超过50个字节")
	@NotBlank(message = "名称不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	private String dicName;
	private String parentName;
 
	 
	private String dicType;
	private String parentId;
	private String dicDesc;
	private String noteNo;	
	private String noteNo2;	
	
	public String getNoteNo2() {
		return noteNo2;
	}
	public void setNoteNo2(String noteNo2) {
		this.noteNo2 = noteNo2;
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
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getDicType() {
		return dicType;
	}
	public void setDicType(String dicType) {
		this.dicType = dicType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDicDesc() {
		return dicDesc;
	}
	public void setDicDesc(String dicDesc) {
		this.dicDesc = dicDesc;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

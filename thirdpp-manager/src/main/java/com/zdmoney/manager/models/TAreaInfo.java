package com.zdmoney.manager.models;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TAreaInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -530252948730709076L;
	
	private Integer id;
	@NotBlank(message = "区域编码不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 10, message = "区域编码超过10个字节") 	
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="区域编码只能输入数字和字母") 
	private String areaCode;
	@NotBlank(message = "区域名称不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 50, message = "区域名称超过50个字节") 	
	private String areaName;
	private String parentId;
	private String noteNo;
	private String noteText;
	
	public String getNoteText() {
		return noteText;
	}
	public void setNoteText(String noteText) {
		this.noteText = noteText;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}

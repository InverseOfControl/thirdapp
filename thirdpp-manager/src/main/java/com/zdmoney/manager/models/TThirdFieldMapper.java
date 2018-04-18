package com.zdmoney.manager.models;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TThirdFieldMapper implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7210544097596631988L;
	
	private Integer id;
	@NotBlank(message = "第三方平台编码为空" )
	@Length(max = 4, message = "第三方平台编码超过4个字节")	
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="第三方平台编码只能输入数字和字母") 
	private String thirdPartyType;
	@NotBlank(message = "tpp系统字段编码为空"  )
	@Length(max = 10, message = "tpp系统字段编码超过10个字节")
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="tpp系统字段只能输入数字和字母") 
	private String tppFieldCode;
	@NotBlank(message = "第三方平台字段编码编码为空" )
	@Length(max = 10, message = "第三方平台字段编码超过10个字节")
	@Pattern(regexp="^[a-z0-9A-Z]+$",message="第三方平台字段编码只能输入数字和字母") 
	private String  thirdFieldCode;
	@NotBlank(message = "字段名称为空")
	@Length(max = 100, message = "字段名称超过100个字节")
	private String  fieldName;
	@NotBlank(message = "请选择字段类型" )
	private String  fieldType;
	/*@Pattern(regexp="^[0-9]{1,15}+(.[0-9]{0,2})?$",message="金额格式有误")*/
	private String  collectMaxMoney; 
/*@Pattern(regexp="^[0-9]{1,15}+(.[0-9]{0,2})?$",message="金额格式有误")*/
	private String  payMaxMoney;
	/*@Pattern(regexp="^[0-9]{1,15}+(.[0-9]{0,2})?$",message="金额格式有误") */
	private String  quickPayMaxMoney;
	private String dtTime;
	private String dicName;
	private String status;//FIELD_TYPE=0该值有效，状态(0银行通道关闭1银行通道开启)
	private BigDecimal  collectMaxMoney1; 
	private BigDecimal  payMaxMoney1; 
	private BigDecimal  quickPayMaxMoney1; 
	public BigDecimal getPayMaxMoney1() {
		return payMaxMoney1;
	}
	public void setPayMaxMoney1(BigDecimal payMaxMoney1) {
		this.payMaxMoney1 = payMaxMoney1;
	}
	public BigDecimal getQuickPayMaxMoney1() {
		return quickPayMaxMoney1;
	}
	public void setQuickPayMaxMoney1(BigDecimal quickPayMaxMoney1) {
		this.quickPayMaxMoney1 = quickPayMaxMoney1;
	}
	
	public BigDecimal getCollectMaxMoney1() {
		return collectMaxMoney1;
	}
	public void setCollectMaxMoney1(BigDecimal collectMaxMoney1) {
		this.collectMaxMoney1 = collectMaxMoney1;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getThirdPartyType() {
		return thirdPartyType;
	}
	public void setThirdPartyType(String thirdPartyType) {
		this.thirdPartyType = thirdPartyType;
	}
	public String getTppFieldCode() {
		return tppFieldCode;
	}
	public void setTppFieldCode(String tppFieldCode) {
		this.tppFieldCode = tppFieldCode;
	}
	public String getThirdFieldCode() {
		return thirdFieldCode;
	}
	public void setThirdFieldCode(String thirdFieldCode) {
		this.thirdFieldCode = thirdFieldCode;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	 
	 
	public String getDtTime() {
		return dtTime;
	}
	public void setDtTime(String dtTime) {
		this.dtTime = dtTime;
	}
	public String getCollectMaxMoney() {
		return collectMaxMoney;
	}
	public void setCollectMaxMoney(String collectMaxMoney) {
		this.collectMaxMoney = collectMaxMoney;
	}
	public String getPayMaxMoney() {
		return payMaxMoney;
	}
	public void setPayMaxMoney(String payMaxMoney) {
		this.payMaxMoney = payMaxMoney;
	}
	public String getQuickPayMaxMoney() {
		return quickPayMaxMoney;
	}
	public void setQuickPayMaxMoney(String quickPayMaxMoney) {
		this.quickPayMaxMoney = quickPayMaxMoney;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

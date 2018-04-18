package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.zendaimoney.thirdpp.account.annotation.TranslateAnnotation;

public class BizsysAccountInfo implements Serializable {

	private static final long serialVersionUID = -6239039459142029264L;
	
	// 任务号
	@TranslateAnnotation(value= "任务号")
	private Long taskId;
	// 业务流水号
	@TranslateAnnotation(value= "业务流水号")
	private String bizFlow;
	// 业务系统号
	@TranslateAnnotation(value= "业务系统号")
	private String bizSysNo;
	// 业务类型
	@TranslateAnnotation(value= "业务类型")
	private String bizType;
	// 通道编码
	@TranslateAnnotation(value= "通道编码")
	private String paySysNo;
	// 对账成功金额
	@TranslateAnnotation(value= "成功金额")
	private BigDecimal successAmount;
	// 总金额
	@TranslateAnnotation(value= "总金额")
	private BigDecimal totalAmount;
	// 对账日期
	@TranslateAnnotation(value= "对账日期")
	private String accountDay;
	// 创建时间
	private Date createDate;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}

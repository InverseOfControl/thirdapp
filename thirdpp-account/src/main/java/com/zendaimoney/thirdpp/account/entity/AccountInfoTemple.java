package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 业务系统对账抽象父类，包含对账涉及的属性
 * 
 * @author 00237071
 * 
 */
public class AccountInfoTemple implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键ID
	private Long id;

	// 任务号
	private Long taskId;

	// 金额
	private BigDecimal amount;

	// 业务流水号
	private String bizFlow;

	// 000000交易成功 111111交易失败 222222交易处理中 333333交易异常
	private String status;

	/**
	 * 业务类型
	 */
	private String bizType;

	/**
	 * 业务系统编码
	 */
	private String bizSysNo;

	/**
	 * 交易流水号
	 */
	private String tradeFlow;

	// 清算日期
	private String settleDate;
	
	private boolean isFromHis;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public boolean isFromHis() {
		return isFromHis;
	}

	public void setFromHis(boolean isFromHis) {
		this.isFromHis = isFromHis;
	}
	
}

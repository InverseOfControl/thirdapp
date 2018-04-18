package com.zendaimoney.thirdpp.query.entity;

import java.io.Serializable;

public class TradeWaitingQuery implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键ID
	 */
	private String id;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 业务类型
	 */
	private String bizTypeNo;
	
	/**
	 * 业务系统号
	 */
	private String bizSysNo;
	
	/**
	 * 支付渠道编码
	 */
	private String paySysNo;
	
	/**
	 * 信息类别编码
	 */
	private String infoCategoryCode;
	
	/**
	 * 查询模块名称
	 */
	private String queryModuleName;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 修改时间
	 */
	private String updateTime;
	
	/**
	 * 状态:0 待处理 1处理成功
	 */
	private String status;
	
	/**
	 * 付款方账户
	 */
	private String payerAccountNo;
	
	/**
	 * 运营方式(0线下运营1线上运营)
	 */
	private String opMode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}

	public String getQueryModuleName() {
		return queryModuleName;
	}

	public void setQueryModuleName(String queryModuleName) {
		this.queryModuleName = queryModuleName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPayerAccountNo() {
		return payerAccountNo;
	}
	
	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}
	
	public String getOpMode() {
		return opMode;
	}
	
	public void setOpMode(String opMode) {
		this.opMode = opMode;
	}
}

package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;

public class ChannelRequest implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8827674647775801073L;
	/**
	 * TPP通道請求id
	 */
	private String reqId;
	/**
	 * 交易流水号
	 */
	private String transferFlow;
	/**
	 * 支付渠道交易流水号
	 */
	private String payTransFlow;
	/**
	 * 创建时间
	 */
	private String create_time;
	/**
	 * 修改时间
	 */
	private String update_time;
	/**
	 * 报文状态
	 */
	private String status;
	/**
	 * 支付渠道编码
	 */
	private String paySysNo;
	/**
	 * 业务类型
	 */
	private String bizType;
	/**
	 * 业务系统编码
	 */
	private String bizSysNo;

	/**
	 * TPP通道請求id
	 */
	private String spare1;
	/**
	 * TPP通道請求id
	 */
	private String spare2;
	
	
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getTransferFlow() {
		return transferFlow;
	}
	public void setTransferFlow(String transferFlow) {
		this.transferFlow = transferFlow;
	}
	public String getPayTransFlow() {
		return payTransFlow;
	}
	public void setPayTransFlow(String payTransFlow) {
		this.payTransFlow = payTransFlow;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaySysNo() {
		return paySysNo;
	}
	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getSpare1() {
		return spare1;
	}
	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}
	public String getSpare2() {
		return spare2;
	}
	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBizSysNo() {
		return bizSysNo;
	}
	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}


}

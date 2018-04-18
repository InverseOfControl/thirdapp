package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;

/**
 * 交易结果对象
 * 
 * @author 00231257
 *
 */
public class TradeResult implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8916530329419728396L;

	// 主键
	private long id;

	/**
	 * 支付渠道编码
	 */
	private String paySysNo;

	/**
	 * 交易流水号
	 */
	private String transferFlow;

	/**
	 * 支付通道流水号
	 */
	private String payTransFlow;

	/**
	 * 业务类型
	 */
	private String bizType;

	// 渠道返回状态
	private String transRepCode;

	// 渠道返回信息
	private String transRepInfo;

	// 000000交易成功 111111交易失败 222222交易处理中
	private String status;

	/**
	 * 创建时间
	 */
	private String createTime;



	// TPP通道请求ID
	private String reqId;

	public TradeResult() {
	}

	public TradeResult(String paySysNo, String transferFlow,
			String payTransFlow, String bizType, String transRepCode,
			String transRepInfo, String status, String reqId) {

		this.paySysNo = paySysNo;
		this.transferFlow = transferFlow;
		this.payTransFlow = payTransFlow;
		this.bizType = bizType;
		this.transRepCode = transRepCode;
		this.transRepInfo = transRepInfo;
		this.status = status;
		this.reqId = reqId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
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

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getTransRepCode() {
		return transRepCode;
	}

	public void setTransRepCode(String transRepCode) {
		this.transRepCode = transRepCode;
	}

	public String getTransRepInfo() {
		return transRepInfo;
	}

	public void setTransRepInfo(String transRepInfo) {
		this.transRepInfo = transRepInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	@Override
	public String toString() {
		return "TradeResult [paySysNo=" + paySysNo + ", transferFlow="
				+ transferFlow + ", payTransFlow=" + payTransFlow
				+ ", bizType=" + bizType + ", transRepCode=" + transRepCode
				+ ", transRepInfo=" + transRepInfo + ", status=" + status
				+ ", reqId=" + reqId + "]";
	}

}

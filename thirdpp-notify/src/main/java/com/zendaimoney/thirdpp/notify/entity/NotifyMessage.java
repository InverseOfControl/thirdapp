package com.zendaimoney.thirdpp.notify.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 通知实体类
 * 
 * @author 00225642
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NotifyMessage {
	public NotifyMessage() {

	}
	
	/**
	 * 请求流水，对应notify表的主键id
	 */
	@XmlElement(name = "requestId")
	private long requestId;

	/**
	 * 任务ID
	 */
	@XmlElement(name = "taskId")
	private long taskId;

	/**
	 * 业务流水号
	 */
	@XmlElement(name = "orderNo")
	private String orderNo;
	
	/**
	 * 交易流水
	 */
	@XmlElement(name = "tradeFlow")
	private String tradeFlow;

	@XmlElement(name = "returnCode")
	private String returnCode;

	@XmlElement(name = "returnInfo")
	private String returnInfo;

	@XmlElement(name = "successAmount")
	private String successAmount;
	
	@XmlElement(name = "failReason")
	private String failReason;

	@XmlElement(name = "paySysNo")
	private String paySysNo;
	
	@XmlElement(name = "merId")
	private String merId;

	public long getRequestId() {
		return requestId;
	}

	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	
}

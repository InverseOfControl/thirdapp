package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CollectInfo implements Serializable {

	// 通知查询状态-待通知查询
	public static final Integer NOTIFY_QUERY_STATUS_WAITING = 0;

	// 通知查询状态-不需要通知查询
	public static final Integer NOTIFY_QUERY_STATUS_NO = 1;

	// 通知查询状态-已通知查询
	public static final Integer NOTIFY_QUERY_STATUS_YES = 2;

	// 通知合并状态-待通知合并
	public static final Integer NOTIFY_MERGE_STATUS_WAITING = 0;

	// 通知合并状态-不需要通知合并
	public static final Integer NOTIFY_MERGE_STATUS_NO = 1;

	// 通知查询状态-已通知合并
	public static final Integer NOTIFY_MERGE_STATUS_YES = 2;
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6837468772057505938L;

	// 主键ID
	private long id;

	// 任务号
	private Long taskId;

	// 请求ID
	private String reqId;

	// 业务系统客户号
	private String bizSysAccountNo;

	// 证大客户号
	private String zengdaiAccountNo;

	// 收款人账户号
	private String receiverAccountNo;

	// 收款人账户姓名
	private String reveiverAccountName;

	// 付款人姓名
	private String payerName;

	// 付款人银行卡号
	private String payerBankCardNo;

	// 付款人银行卡类型
	private String payerBankCardType;

	// 付款方证件类型
	private String payerIdType;

	// 付款方证件号
	private String payerId;

	// 付款方银行编码
	private String payerBankCode;

	// 付款方银行支行行号
	private String payerSubBankCode;

	/**
	 * 付款方账户编号
	 */
	private String payerAccountNo;

	// 币种(0人民币)
	private String currency;

	// 金额
	private BigDecimal amount;

	// 手续费
	private BigDecimal fee;

	// 业务备注
	private String bizRemark;

	// 业务流水号
	private String bizFlow;

	// 优先级(3最高 2高 1中 0普通)
	private Integer priority;

	// 000000交易成功 111111交易失败 222222交易处理中 333333交易异常
	private String status;

	// 备注
	private String remark;

	// 创建人
	private String creater;

	// 创建时间
	private String createTime;

	// 更新时间
	private String updateTime;

	// 备用1
	private String spare1;

	// 备用2
	private String spare2;

	/**
	 * 业务类型
	 */
	private String bizTypeNo;

	/**
	 * 第三方通道编码
	 */
	private String paySysNo;

	/**
	 * 业务系统编码
	 */
	private String bizSysNo;

	/**
	 * 支付渠道流水号
	 */
	private String payTransFlow;

	/**
	 * 失败原因
	 */
	private String failReason;

	// 渠道返回状态
	private String transRepCode;

	/**
	 * 第三方回盘时间
	 * 
	 */
	private String thirdReturnTime;

	/**
	 * 通知查询状态(0待通知查询、1不需要通知查询、2已通知查询)
	 */
	private Integer notifyQueryStatus;

	/**
	 * 通知合并状态(0待通知合并、1不需要通知合并、2已通知合并)
	 */
	private Integer notifyMergeStatus;

	public CollectInfo() {

	}

	public CollectInfo(String tradeFlow, String status, String payTransFlow,
			String failReason, String transRepCode, String thirdReturnTime,
			Integer notifyQueryStatus,Integer notifyMergeStatus) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.payTransFlow = payTransFlow;
		this.failReason = failReason;
		this.transRepCode = transRepCode;
		this.thirdReturnTime = thirdReturnTime;
		this.notifyQueryStatus = notifyQueryStatus;
		this.notifyMergeStatus = notifyMergeStatus;
	}
	
	public CollectInfo(String tradeFlow,String merId) {
		this.tradeFlow = tradeFlow;
		this.spare2 = merId;
	}

	/**
	 * 交易流水号
	 */
	private String tradeFlow;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getBizSysAccountNo() {
		return bizSysAccountNo;
	}

	public void setBizSysAccountNo(String bizSysAccountNo) {
		this.bizSysAccountNo = bizSysAccountNo;
	}

	public String getZengdaiAccountNo() {
		return zengdaiAccountNo;
	}

	public void setZengdaiAccountNo(String zengdaiAccountNo) {
		this.zengdaiAccountNo = zengdaiAccountNo;
	}

	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}

	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}

	public String getReveiverAccountName() {
		return reveiverAccountName;
	}

	public void setReveiverAccountName(String reveiverAccountName) {
		this.reveiverAccountName = reveiverAccountName;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerBankCardNo() {
		return payerBankCardNo;
	}

	public void setPayerBankCardNo(String payerBankCardNo) {
		this.payerBankCardNo = payerBankCardNo;
	}

	public String getPayerBankCardType() {
		return payerBankCardType;
	}

	public void setPayerBankCardType(String payerBankCardType) {
		this.payerBankCardType = payerBankCardType;
	}

	public String getPayerIdType() {
		return payerIdType;
	}

	public void setPayerIdType(String payerIdType) {
		this.payerIdType = payerIdType;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPayerBankCode() {
		return payerBankCode;
	}

	public void setPayerBankCode(String payerBankCode) {
		this.payerBankCode = payerBankCode;
	}

	public String getPayerSubBankCode() {
		return payerSubBankCode;
	}

	public void setPayerSubBankCode(String payerSubBankCode) {
		this.payerSubBankCode = payerSubBankCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getBizRemark() {
		return bizRemark;
	}

	public void setBizRemark(String bizRemark) {
		this.bizRemark = bizRemark;
	}

	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
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

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
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

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}

	public String getPayTransFlow() {
		return payTransFlow;
	}

	public void setPayTransFlow(String payTransFlow) {
		this.payTransFlow = payTransFlow;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getTransRepCode() {
		return transRepCode;
	}

	public void setTransRepCode(String transRepCode) {
		this.transRepCode = transRepCode;
	}

	public String getThirdReturnTime() {
		return thirdReturnTime;
	}

	public void setThirdReturnTime(String thirdReturnTime) {
		this.thirdReturnTime = thirdReturnTime;
	}

	public Integer getNotifyQueryStatus() {
		return notifyQueryStatus;
	}

	public void setNotifyQueryStatus(Integer notifyQueryStatus) {
		this.notifyQueryStatus = notifyQueryStatus;
	}

	public Integer getNotifyMergeStatus() {
		return notifyMergeStatus;
	}

	public void setNotifyMergeStatus(Integer notifyMergeStatus) {
		this.notifyMergeStatus = notifyMergeStatus;
	}

}
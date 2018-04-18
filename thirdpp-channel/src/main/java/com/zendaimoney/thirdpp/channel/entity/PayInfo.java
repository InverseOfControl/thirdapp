package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 代付/融资业务交易明细对象
 * 
 * @author 00237489
 *
 */
public class PayInfo  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1005154045419131064L;

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

	//主键(对应SEQ_TPP_TRADE_INFO序列)
	private Long id;
	
	//任务ID,对应代付业务任务表TPP_TRADE_T_PAY_TASK主键ID
    private Long taskId;
    
    //请求ID,对应TPP_TRADE_T_REQUEST字段REQ_ID
    private String reqId;
    
    //支付渠道编号
    private String paySysNo;
    
    //业务系统客户编号
    private String bizSysAccountNo;
    
    //证大客户编号
    private String zengdaiAccountNo;
    
    //业务系统号
    private String bizSysNo;
    
    //付款方账户编号
    private String payerAccountNo;
    
    //付款方姓名
    private String payerAccountName;
    
    //收款方姓名
    private String receiverName;
    
    //收款方银行卡号
    private String receiverBankCardNo;

    //收款方银行卡类型 1.借记卡，2信用卡
    private String receiverBankCardType;

    //收款方证件类型0=身份证  1=户口簿  2=护照 3=军官证 4=士兵证 5=港澳居民来往内地通行证  6=台湾同胞来往内地通行证  7=临时身份证	8=外国人居留证  9=警官证  X=其他证件
    private String receiverIdType;

    //收款方证件号
    private String receiverId;
    
    //收款方银行编码
    private String receiverBankCode;
    
    //收款方银行支行行号
    private String receiverSubBankCode;
    
    //币种(0人民币)
    private String currency;
    
    //金额
    private BigDecimal amount;

    //手续费
    private BigDecimal fee;

    //业务流水号
    private String bizRemark;

    //业务流水号
    private String bizFlow;

    //交易流水号,由TPP系统生成,由TPP发送程序生成，生成规则待定。
    private String transferFlow;

    //支付渠道交易返回流水号
    private String payTransFlow;

    //优先级(3最高 2高 1中 0普通)
    private Integer priority;

    //000000交易成功 111111交易失败 222222交易处理中  333333交易异常(交易在提交到支付平台前所发生错误都定义成交易异常)
    private String status;

    //备注
    private String remark;

    //创建人
    private String creater;

    //创建时间
    private String createTime;
    
    //更新时间
    private String updateTime;

    //备用1
    private String spare1;

    //备用2
    private String spare2;

    //交易流水号
    private String tradeFlow;

    //失败原因
    private String failReason;

    //收款方账户编号
    private String receiverAccountNo;

    //业务类型(002居间人模式代付003资金托管融资)
    private String bizType;

    //是否需要推送 0不需要推送1需要推送
    private Integer isNeedPush;

    //信息类别编码
    private String infoCategoryCode;

    //渠道返回状态
    private String transRepCode;

    //第三方回盘时间
    private String thirdReturnTime;

    //通知查询状态(0待通知查询、1不需要通知查询、2已通知查询)
    private Integer notifyQueryStatus;

    //通知合并状态(0待通知合并、1不需要通知合并、2已通知合并)
    private Integer notifyMergeStatus;

    //清算日期
    private String settleDate;
    
    
    public PayInfo(String tradeFlow, String status, String payTransFlow,
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
    

    public PayInfo() {
    	
	}


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


	public String getReqId() {
		return reqId;
	}


	public void setReqId(String reqId) {
		this.reqId = reqId;
	}


	public String getPaySysNo() {
		return paySysNo;
	}


	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
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


	public String getBizSysNo() {
		return bizSysNo;
	}


	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}


	public String getPayerAccountNo() {
		return payerAccountNo;
	}


	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}


	public String getPayerAccountName() {
		return payerAccountName;
	}


	public void setPayerAccountName(String payerAccountName) {
		this.payerAccountName = payerAccountName;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	public String getReceiverBankCardNo() {
		return receiverBankCardNo;
	}


	public void setReceiverBankCardNo(String receiverBankCardNo) {
		this.receiverBankCardNo = receiverBankCardNo;
	}


	public String getReceiverBankCardType() {
		return receiverBankCardType;
	}


	public void setReceiverBankCardType(String receiverBankCardType) {
		this.receiverBankCardType = receiverBankCardType;
	}


	public String getReceiverIdType() {
		return receiverIdType;
	}


	public void setReceiverIdType(String receiverIdType) {
		this.receiverIdType = receiverIdType;
	}


	public String getReceiverId() {
		return receiverId;
	}


	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}


	public String getReceiverBankCode() {
		return receiverBankCode;
	}


	public void setReceiverBankCode(String receiverBankCode) {
		this.receiverBankCode = receiverBankCode;
	}


	public String getReceiverSubBankCode() {
		return receiverSubBankCode;
	}


	public void setReceiverSubBankCode(String receiverSubBankCode) {
		this.receiverSubBankCode = receiverSubBankCode;
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


	public String getTradeFlow() {
		return tradeFlow;
	}


	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}


	public String getFailReason() {
		return failReason;
	}


	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}


	public String getReceiverAccountNo() {
		return receiverAccountNo;
	}


	public void setReceiverAccountNo(String receiverAccountNo) {
		this.receiverAccountNo = receiverAccountNo;
	}


	public String getBizType() {
		return bizType;
	}


	public void setBizType(String bizType) {
		this.bizType = bizType;
	}


	public Integer getIsNeedPush() {
		return isNeedPush;
	}


	public void setIsNeedPush(Integer isNeedPush) {
		this.isNeedPush = isNeedPush;
	}


	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}


	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
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


	public String getSettleDate() {
		return settleDate;
	}


	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

}
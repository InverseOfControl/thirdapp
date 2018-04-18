package com.zendaimoney.thirdpp.transfer.entity.collect;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author 00231257
 *
 */
public class CollectTask implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5412554215468678008L;

	// 报盘是否需要拆单 1需要
	public static int IS_NEED_SPLIT_YES = 1;

	// 报盘是否需要拆单 0不需要
	public static int IS_NEED_SPLIT_NO = 0;

	// 主键
	private long id;

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

	/**
	 * 付款方账户编号
	 */
	private String payerAccountNo;

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
	 * 付款方手机号码
	 */
	private String payerMobile;

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

	// 0待发送、1发送中、2已发送
	private Integer status;

	// 备注
	private String remark;

	// 创建人
	private String creater;

	// 创建时间
	private String createTime;

	// 更新时间
	private String updateTime;

	// 应用程序名称
	private String sendThreadName;

	// 是否拆单(1:拆单、0不拆单)该字段由转发程序判断进行修改。
	private Integer isSeparate;

	// 拆单数
	private Integer separateCount;

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
	 * 
	 */
	private Integer oldStatus;

	/**
	 * 发送次数
	 */
	private Integer sendNum;

	// 是否需要推送 0不需要推送1需要推送
	private Integer isNeedPush;

	// 信息类别编码
	private String infoCategoryCode;

	// 报盘是否需要拆单(限额超过后)0不需要拆单1需要拆单
	private Integer isNeedSpilt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public String getSendThreadName() {
		return sendThreadName;
	}

	public void setSendThreadName(String sendThreadName) {
		this.sendThreadName = sendThreadName;
	}

	public Integer getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(Integer isSeparate) {
		this.isSeparate = isSeparate;
	}

	public Integer getSeparateCount() {
		return separateCount;
	}

	public void setSeparateCount(Integer separateCount) {
		this.separateCount = separateCount;
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

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	public String getPayerAccountNo() {
		return payerAccountNo;
	}

	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
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

	public Integer getIsNeedSpilt() {
		return isNeedSpilt;
	}

	public void setIsNeedSpilt(Integer isNeedSpilt) {
		this.isNeedSpilt = isNeedSpilt;
	}

	public String getPayerMobile() {
		return payerMobile;
	}

	public void setPayerMobile(String payerMobile) {
		this.payerMobile = payerMobile;
	}

	
}
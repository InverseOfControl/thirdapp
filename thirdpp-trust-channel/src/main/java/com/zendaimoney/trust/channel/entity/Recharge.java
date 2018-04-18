package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易充值
 * @author mencius
 *
 */
public class Recharge implements Serializable {

	private static final long serialVersionUID = 1L;

	//是否需要推送(0-不需要；1-需要)
	private String isNeedPush;
	//是否批量操作(0-否，1-是)
	private String isBatch;
	//批次号(对应TPP_TRUST_OPER_BATCH表中的BATCH_NO)
	private String batchNo;
	//交易流水号(3位业务类型编码 + 13位随机数)，业务类型编码固定为008
	private String tradeFlow;
	//交易类型:1对私2对工
	private String tradeType;
	//状态：1 请求处理中, 2 成功, 3 失败
	private int status;
	//付款方银行卡号
	private String fromBankCardNo;
	//付款方银行卡类型 1.借记卡，2信用卡
	private String fromBankCardType;
	//付款方银行编码
	private String fromBankCode;
	//付款方银行名称
	private String fromBankName;
	//收款方账户号
	private String toAccountNo;
	//收款方客户号
	private String toClientNo;
	//银行卡支付金额(单位：元)
	private BigDecimal amount;
	//红包，单位:元
	private BigDecimal redPacketAmount;
	//实际充值金额，账户收入金额(单位：元)，公式:AMOUNT+RED_PACKET_AMOUNT
	private BigDecimal actualAmount;
	//币种(0人民币)
	private String currency;
	//创建时间
	private String createTime;
	//创建人
	private String creater;
	//付款类型(1第三方快捷支付2网银支付3第三方线上支付4第三方线下代扣5自助转账)
	private String payType;
	//IP
	private String ip;
	//移动设备信息或者PC机MAC地址
	private String mac;
	//返回时间
	private String respTime;
	//返回时间，外部系统时间
	private String respTimeExt;
	//返回操作流水号
	private String respFlow;
	//失败原因
	private String failReason;
	//支付渠道返回操作流水号
	private String payTransFlow;
	//业务系统流水号
	private String bizFlow;
	//支付渠道编码
	private String paySysNo;
	//业务系统编号
	private String bizSysNo;
	//信息类别编码
	private String infoCategoryCode;
	//通知URL
	private String notifyUrl;
	//清算日期
	private String settleDate;
	//交易摘要
	private String note;
	//预留字段1
	private String spare1;
	//预留字段2
	private String spare2;
	
	/**
	 * 默认构造器
	 */
	public Recharge() {
	}

	/**
	 * 传值构造器
	 */
	public Recharge(String tradeFlow, int status, String failReason, String settleDate, String respTimeExt) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.failReason = failReason;
		this.settleDate = settleDate;
		this.respTimeExt = respTimeExt;
	}

	
	public String getIsNeedPush() {
		return isNeedPush;
	}
	public void setIsNeedPush(String isNeedPush) {
		this.isNeedPush = isNeedPush;
	}
	public String getIsBatch() {
		return isBatch;
	}
	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getTradeFlow() {
		return tradeFlow;
	}
	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getFromBankCardNo() {
		return fromBankCardNo;
	}
	public void setFromBankCardNo(String fromBankCardNo) {
		this.fromBankCardNo = fromBankCardNo;
	}
	public String getFromBankCardType() {
		return fromBankCardType;
	}
	public void setFromBankCardType(String fromBankCardType) {
		this.fromBankCardType = fromBankCardType;
	}
	public String getFromBankCode() {
		return fromBankCode;
	}
	public void setFromBankCode(String fromBankCode) {
		this.fromBankCode = fromBankCode;
	}
	public String getFromBankName() {
		return fromBankName;
	}
	public void setFromBankName(String fromBankName) {
		this.fromBankName = fromBankName;
	}
	public String getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getToClientNo() {
		return toClientNo;
	}
	public void setToClientNo(String toClientNo) {
		this.toClientNo = toClientNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getRedPacketAmount() {
		return redPacketAmount;
	}
	public void setRedPacketAmount(BigDecimal redPacketAmount) {
		this.redPacketAmount = redPacketAmount;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getRespTimeExt() {
		return respTimeExt;
	}
	public void setRespTimeExt(String respTimeExt) {
		this.respTimeExt = respTimeExt;
	}
	public String getRespFlow() {
		return respFlow;
	}
	public void setRespFlow(String respFlow) {
		this.respFlow = respFlow;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getPayTransFlow() {
		return payTransFlow;
	}
	public void setPayTransFlow(String payTransFlow) {
		this.payTransFlow = payTransFlow;
	}
	public String getBizFlow() {
		return bizFlow;
	}
	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
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
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}
	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	
	
	
}

package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 交易提现
 * @author mencius
 *
 */
public class CashDraw implements Serializable {

	private static final long serialVersionUID = 1L;

	//是否需要推送，0-不需要；1-需要
	private String isNeedPush;
	//是否批量操作，0-否；1-是
	private String isBatch;
	//对应TPP_TRUST_OPER_BATCH表中的BATCH_NO
	private String batchNo;
	//交易流水号(3位业务类型编码 + 13位随机数)，业务类型编码固定为009
	private String tradeFlow;
	//交易类型:WTDR-个人提现请求;MCHG-平台提现
	private String tradeType;
	//付款方账户号
	private String fromAccountNo;
	//付款方客户号
	private String fromClientNo;
	//付款方垫付账户号
	private String fromAdvancedAccountNo;
	//付款方垫付客户号
	private String fromAdvancedClientNo;
	//收款方银行卡号
	private String toBankCardNo;
	//收款方银行卡类型 1.借记卡，2信用卡
	private String toBankCardType;
	//收款方银行编码
	private String toBankCode;
	//收款方银行支行行号
	private String toSubBankCode;
	//收款方银行名称
	private String toBankName;
	//收款方银行支行名称
	private String toSubBankName;
	//收款人姓名
	private String toName;
	//金额,单位:元
	private BigDecimal amount;
	//手续费金额(单位：元)
	private BigDecimal feeAmount;
	//实际提现金额，收款方银行卡实际入账金额(单位：元)，公式:AMOUNT-FEE_AMOUNT
	private BigDecimal actualAmount;
	//币种(0人民币)
	private String currency;
	//状态:1待处理  2 提现处理中 3 提现成功 4 提现失败
	private int status;
	//创建时间
	private String createTime;
	//创建人
	private String creater;
	//IP
	private String ip;
	//移动设备信息或者PC机MAC地址
	private String mac;
	//业务系统号
	private String bizSysNo;
	//业务流水号
	private String bizFlow;
	//支付渠道编码
	private String paySysNo;
	//信息类别编码
	private String infoCategoryCode;
	//返回时间
	private String respTime;
	//返回时间，外部系统时间
	private String respTimeExt;
	//返回操作流水号
	private String respFlow;
	//返回垫付账户垫付金额
	private BigDecimal respAdvancedAmount;
	//失败原因
	private String failReason;
	//记账时间
	private String payTime;
	//记账时间，外部系统时间
	private String payTimeExt;
	//支付渠道流水号
	private String payTransFlow;
	//通知URL
	private String notifyUrl;
	//清算日期
	private String settleDate;
	//交易摘要
	private String note;
	//备用字段1
	private String spare1;
	//备用字段2
	private String spare2;
	
	public CashDraw(String tradeFlow, int status, String failReason, String settleDate, String respFlow, String payTimeExt,String respTimeExt,BigDecimal respAdvancedAmount) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.failReason = failReason;
		this.settleDate = settleDate;
		this.respFlow = respFlow;
		this.payTimeExt = payTimeExt;
		this.respTimeExt = respTimeExt;
		this.respAdvancedAmount = respAdvancedAmount;
		
	}
	public CashDraw(String tradeFlow, int status, String failReason, String settleDate, String respFlow, String payTimeExt,String respTimeExt) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.failReason = failReason;
		this.settleDate = settleDate;
		this.respFlow = respFlow;
		this.payTimeExt = payTimeExt;
		this.respTimeExt = respTimeExt;
		
	}
	public CashDraw(String tradeFlow, int status, String failReason) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.failReason = failReason;
	}
	public CashDraw() {
		// TODO Auto-generated constructor stub
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
	public String getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(String fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromClientNo() {
		return fromClientNo;
	}
	public void setFromClientNo(String fromClientNo) {
		this.fromClientNo = fromClientNo;
	}
	public String getFromAdvancedAccountNo() {
		return fromAdvancedAccountNo;
	}
	public void setFromAdvancedAccountNo(String fromAdvancedAccountNo) {
		this.fromAdvancedAccountNo = fromAdvancedAccountNo;
	}
	public String getFromAdvancedClientNo() {
		return fromAdvancedClientNo;
	}
	public void setFromAdvancedClientNo(String fromAdvancedClientNo) {
		this.fromAdvancedClientNo = fromAdvancedClientNo;
	}
	public String getToBankCardNo() {
		return toBankCardNo;
	}
	public void setToBankCardNo(String toBankCardNo) {
		this.toBankCardNo = toBankCardNo;
	}
	public String getToBankCardType() {
		return toBankCardType;
	}
	public void setToBankCardType(String toBankCardType) {
		this.toBankCardType = toBankCardType;
	}
	public String getToBankCode() {
		return toBankCode;
	}
	public void setToBankCode(String toBankCode) {
		this.toBankCode = toBankCode;
	}
	public String getToSubBankCode() {
		return toSubBankCode;
	}
	public void setToSubBankCode(String toSubBankCode) {
		this.toSubBankCode = toSubBankCode;
	}
	public String getToBankName() {
		return toBankName;
	}
	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}
	public String getToSubBankName() {
		return toSubBankName;
	}
	public void setToSubBankName(String toSubBankName) {
		this.toSubBankName = toSubBankName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	public String getBizSysNo() {
		return bizSysNo;
	}
	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
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
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}
	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
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
	public BigDecimal getRespAdvancedAmount() {
		return respAdvancedAmount;
	}
	public void setRespAdvancedAmount(BigDecimal respAdvancedAmount) {
		this.respAdvancedAmount = respAdvancedAmount;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayTimeExt() {
		return payTimeExt;
	}
	public void setPayTimeExt(String payTimeExt) {
		this.payTimeExt = payTimeExt;
	}
	public String getPayTransFlow() {
		return payTransFlow;
	}
	public void setPayTransFlow(String payTransFlow) {
		this.payTransFlow = payTransFlow;
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

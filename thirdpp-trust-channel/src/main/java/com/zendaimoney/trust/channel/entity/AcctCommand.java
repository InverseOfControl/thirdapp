package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户资金
 * @author mencius
 *
 */
public class AcctCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	//交易流水号(主键，3位业务类型编码 + 13位随机数，冻结:020，解冻:021)
	private	String	tradeFlow;
	//客户号
	private	String	clientNo;
	//账户号
	private	String	accountNo;
	//交易金额
	private	BigDecimal	amount;
	//FROZ:冻结,UNFR:解冻
	private	String	tradeType;
	//业务系统编号
	private	String	bizSysNo;
	//支付渠道编码
	private	String	paySysNo;
	//请求状态(0处理中1操作成功2操作失败)
	private	int	status;
	//更新时间
	private	String	updateTime;
	//请求IP
	private	String	ip;
	//移动设备信息或者PC机MAC地址
	private	String	mac;
	//创建人
	private	String	creater;
	//创建时间
	private	String	createTime;
	//业务系统流水号
	private	String	bizFlow;
	//返回操作流水号
	private	String	respFlow;
	//返回时间
	private	String	respTime;
	//返回信息
	private	String	respInfo;
	//信息类别编码
	private	String	infoCategoryCode;
	//返回时间，外部系统时间
	private	String	respTimeExt;
	//原交易流水号
	private	String	oldTradeFlow;
	//剩余冻结金额
	private String  remainFrozAmount;
	//记账符号(1-流入;-1-流出;0-资金未流动)
	private	String	accountSymbol;
	//摘要
	private	String	note;
	//备用字段1
	private	String	spare1;
	//备用字段2
	private	String	spare2;
	//清算日期
	private	String	settleDate;
	//批次号(对应TPP_TRUST_TRADE_BATCH表中的BATCH_NO)
	private	String	batchNo;
	
	public AcctCommand(String tradeFlow, int status,String settleDate, String respInfo, String respTime,String respTimeExt,String remainFrozAmount) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.respInfo = respInfo;
		this.settleDate = settleDate;
		this.respTime = respTime;
		this.respTimeExt = respTimeExt;
		this.remainFrozAmount = remainFrozAmount;
	}
	
	public String getTradeFlow() {
		return tradeFlow;
	}
	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}
	public String getClientNo() {
		return clientNo;
	}
	public void setClientNo(String clientNo) {
		this.clientNo = clientNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	public String getBizFlow() {
		return bizFlow;
	}
	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}
	public String getRespFlow() {
		return respFlow;
	}
	public void setRespFlow(String respFlow) {
		this.respFlow = respFlow;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getRespInfo() {
		return respInfo;
	}
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}
	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
	public String getRespTimeExt() {
		return respTimeExt;
	}
	public void setRespTimeExt(String respTimeExt) {
		this.respTimeExt = respTimeExt;
	}
	public String getOldTradeFlow() {
		return oldTradeFlow;
	}
	public void setOldTradeFlow(String oldTradeFlow) {
		this.oldTradeFlow = oldTradeFlow;
	}
	
	public String getRemainFrozAmount() {
		return remainFrozAmount;
	}

	public void setRemainFrozAmount(String remainFrozAmount) {
		this.remainFrozAmount = remainFrozAmount;
	}

	public String getAccountSymbol() {
		return accountSymbol;
	}
	public void setAccountSymbol(String accountSymbol) {
		this.accountSymbol = accountSymbol;
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
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
}

package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;

/**
 * 
 * @author mencius
 *
 */
public class CashTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	//收款方客户号
	private	String	toClientNo;
	//收款方用户类型(P:个人，C:公司，O:合作机构，T:团账户，G：团备付金账户，S：平台自有)
	private	String	toUserType;
	//金额,单位:元
	private	int	amount;
	//手续费金额(单位：元)
	private	int	feeAmount;
	//实际转账金额，收款方实际入账金额(单位：元)，公式:AMOUNT-FEE_AMOUNT
	private	int	actualAmount;
	//币种(0人民币)
	private	String	currency;
	//创建时间
	private	String	createTime;
	//创建人
	private	String	creater;
	//IP
	private	String	ip;
	//移动设备信息或者PC机MAC地址
	private	String	mac;
	//业务系统号
	private	String	bizSysNo;
	//业务流水号
	private	String	bizFlow;
	//支付渠道编码
	private	String	paySysNo;
	//信息类别编码
	private	String	infoCategoryCode;
	//返回时间
	private	String	respTime;
	//返回时间，外部系统时间
	private	String	respTimeExt;
	//返回操作流水号
	private	String	respFlow;
	//返回信息
	private	String	respInfo;
	//通知URL
	private	String	notifyUrl;
	//清算日期
	private	String	settleDate;
	//交易摘要
	private	String	note;
	//备用字段1
	private	String	spare1;
	//备用字段2
	private	String	spare2;
	//交易流水号(3位业务类型编码 + 13位随机数)，业务类型编码固定为015
	private	String	tradeFlow;
	//转账类型(IVNS:投资,REPY:还款,CASM:债权转让,MFEE:平台收费,MPAY:平台付款,MADY:平台垫资,MREG:垫资收回,GINN:客户入团,GOUT:客户出团,GCHG:团收取风险金,GDRW:团使用风险金)
	private	String	tradeType;
	//付款方账户号
	private	String	fromAccountNo;
	//付款方客户号
	private	String	fromClientNo;
	//付款方用户类型(P:个人，C:公司，O:合作机构，T:团账户，G：团备付金账户，S：平台自有)
	private	String	fromUserType;
	//状态:1 待处理 2转账处理中 3 转账成功 4 转账失败
	private	int	status;
	//收款方账户号
	private	String	toAccountNo;
	//是否需要推送(0-不需要；1-需要)
	private	String	isNeedPush;
	//批次号(对应TPP_TRUST_TRADE_BATCH表中的BATCH_NO)
	private	String	batchNo;
	
	public CashTransfer(String tradeFlow, int status,String respInfo, String respTimeExt) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.respInfo = respInfo;
		this.respTimeExt = respTimeExt;
	}
	public String getToClientNo() {
		return toClientNo;
	}
	public void setToClientNo(String toClientNo) {
		this.toClientNo = toClientNo;
	}
	public String getToUserType() {
		return toUserType;
	}
	public void setToUserType(String toUserType) {
		this.toUserType = toUserType;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(int feeAmount) {
		this.feeAmount = feeAmount;
	}
	public int getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(int actualAmount) {
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
	public String getRespInfo() {
		return respInfo;
	}
	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
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
	public String getFromUserType() {
		return fromUserType;
	}
	public void setFromUserType(String fromUserType) {
		this.fromUserType = fromUserType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(String toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getIsNeedPush() {
		return isNeedPush;
	}
	public void setIsNeedPush(String isNeedPush) {
		this.isNeedPush = isNeedPush;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
}

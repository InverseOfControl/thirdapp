package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;

public class OpenBound implements Serializable {

	private static final long serialVersionUID = -2964620450002313871L;

	//操作流水号(主键，3位业务类型编码 + 13位随机数)
	private	String	tradeFlow;
	//交易类型(USRA-开户,USRB-用户绑定银行卡)
	private	String	tradeType;
	//业务系统编号
	private	String	bizSysNo;
	//支付渠道编码
	private	String	paySysNo;
	//状态(0处理中,1操作成功,2操作失败)
	private	int	status;
	//更新时间
	private	String	updateTime;
	//客户编号
	private	String	clientNo;
	//客户账户号(第三方账户号)
	private	String	accountNo;
	//用户姓名
	private	String	userName;
	//用户类型(P:个人，C:公司，O:合作机构，T:团账户，G：团备付金账户，S：平台自有，F：平台风险金，W：（部分平台）)
	private	String	userType;
	//证件类型(证件类型0=身份证 1=户口簿2=护照	3=军官证	4=士兵证	5=港澳居民来往内地通行证	6=台湾同胞来往内地通行证	7=临时身份证	8=外国人居留证	9=警官证	X=其他证件)
	private	String	idType;
	//证件号码
	private	String	idNo;
	//手机号码
	private	String	mobile;
	//银行账户类型(P：个人账户 C：对公账户)
	private	String	bankAccountType;
	//银行账户号
	private	String	bankCardNo;
	//银行账户名
	private	String	bankAccoutName;
	//账户开户银行
	private	String	openBankCode;
	//账户开户分支行
	private	String	openSubBank;
	//请求IP
	private	String	ip;
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
	//移动设备信息或者PC机MAC地址
	private	String	mac;
	//信息类别编码
	private	String	infoCategoryCode;
	//返回时间，外部系统时间
	private	String	respTimeExt;
	//摘要
	private	String	note;
	//是否批量操作(0-否，1-是)
	private	String	isBatch;
	//批次号(对应TPP_TRUST_OPER_BATCH表中的BATCH_NO)
	private	String	batchNo;
	//备用字段1
	private	String	spare1;
	//备用字段2
	private	String	spare2;
	
	public OpenBound(String tradeFlow, int status, String accountNo, String respInfo, String respTime, String respTimeExt) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.accountNo = accountNo;
		this.respInfo = respInfo;
		this.respTime = respTime;
		this.respTimeExt = respTimeExt;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankAccoutName() {
		return bankAccoutName;
	}

	public void setBankAccoutName(String bankAccoutName) {
		this.bankAccoutName = bankAccoutName;
	}

	public String getOpenBankCode() {
		return openBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}

	public String getOpenSubBank() {
		return openSubBank;
	}

	public void setOpenSubBank(String openSubBank) {
		this.openSubBank = openSubBank;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

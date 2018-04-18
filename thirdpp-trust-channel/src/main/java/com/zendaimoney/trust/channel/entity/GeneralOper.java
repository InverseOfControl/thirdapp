package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;

/**
 * 普通操作表映射对象
 * @author mencius
 *
 */
public class GeneralOper implements Serializable {

	private static final long serialVersionUID = -8500715143795559582L;

	//操作流水号(主键，3位业务类型编码 + 13位随机数)
	private	String tradeFlow;
	//交易类型(USRE-用户修改信息,USRJ-用户解绑银行卡,USRD-用户关户,PROA-登记散标,PINV-散标投资计划书,FROA-登记团信息,PROC-关闭散标,GROC-关闭团,SCQY-查询自助转账到账记录,WDRS-提现结果查询,BAQY-余额查询,TRQY-虚拟户交易查询)
	private	String tradeType;
	//业务系统编号
	private	String bizSysNo;
	//支付渠道编码
	private	String paySysNo;
	//0处理中1操作成功2操作失败
	private	int status;
	//更新时间
	private	String updateTime;
	//请求IP
	private	String ip;
	//创建人
	private	String creater;
	//创建时间
	private	String createTime;
	//业务系统流水号
	private	String bizFlow;
	//返回操作流水号
	private	String respFlow;
	//返回时间
	private	String respTime;
	//返回信息
	private	String respInfo;
	//移动设备信息或者PC机MAC地址
	private	String mac;
	//信息类别编码
	private	String infoCategoryCode;
	//返回时间，外部系统时间
	private	String respTimeExt;
	//摘要
	private	String note;
	//备用1
	private	String spare1;
	//备用2
	private	String spare2;
	
	/**
	 * 构造器
	 * @param tradeFlow
	 * @param status
	 * @param respTime
	 * @param respInfo
	 */
	public GeneralOper(String tradeFlow, int status, String respTime, String respInfo) {
		this.tradeFlow = tradeFlow;
		this.status = status;
		this.respTime = respTime;
		this.respInfo = respInfo;
	}
	
	public GeneralOper() {
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

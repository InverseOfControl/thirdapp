package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;

/**
 * 批量操作请求对象
 * @author mencius
 *
 */
public class BatchOper implements Serializable {

	private static final long serialVersionUID = 1L;

	//信息类别编码
	private String infoCategoryCode;
	//业务系统编号
	private String bizSysNo;
	//失败次数
	private String failCount;
	//失败原因
	private String failReason;
	//银行响应文件名
	private String respFileName;
	//批次号
	private String batchNo;
	//银行返回操作流水号
	private String respFlow;
	//请求银行文件名
	private String reqFileName;
	//返回时间
	private String respTime;
	//返回信息
	private String respInfo;
	//文件处理状态(0-待发送；1-发送中;2-发送成功;3.发送失败；)
	private String status;
	//业务类型编码(USRA-批量开户；	USRB-批量绑卡;	CHRG-批量充值；	FROZ-批量冻结；	UNFR-批量解冻；	TACC-批量转账)
	private String busiType;
	//交易描述信息
	private String tradeDesc;
	//返回时间，外部系统时间
	private String respTimeExt;
	//支付渠道编码
	private String paySysNo;
	//通知业务系统文件名
	private String notifyFileName;
	//通知业务系统时间
	private String notifyTime;
	//预留域1
	private String spare1;
	//预留域2
	private String spare2;
	//创建时间
	private String creatTime;
	//修改时间
	private String updateTime;
	
	/**
	 * 构造器
	 * @param batchNo
	 * @param status
	 * @param respTime
	 * @param respInfo
	 * @param respTimeExt
	 */
	public BatchOper(String batchNo, String status, String respTime, String respInfo, String respTimeExt) {
		this.batchNo = batchNo;
		this.status = status;
		this.respTime = respTime;
		this.respInfo = respInfo;
		this.respTimeExt = respTimeExt;
	}
	
	public BatchOper(String batchNo) {
		this.batchNo = batchNo;
	}

	public BatchOper() {
		
	}
	
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}
	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
	public String getBizSysNo() {
		return bizSysNo;
	}
	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}
	public String getFailCount() {
		return failCount;
	}
	public void setFailCount(String failCount) {
		this.failCount = failCount;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getRespFileName() {
		return respFileName;
	}
	public void setRespFileName(String respFileName) {
		this.respFileName = respFileName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRespFlow() {
		return respFlow;
	}
	public void setRespFlow(String respFlow) {
		this.respFlow = respFlow;
	}
	public String getReqFileName() {
		return reqFileName;
	}
	public void setReqFileName(String reqFileName) {
		this.reqFileName = reqFileName;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getTradeDesc() {
		return tradeDesc;
	}
	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}
	public String getRespTimeExt() {
		return respTimeExt;
	}
	public void setRespTimeExt(String respTimeExt) {
		this.respTimeExt = respTimeExt;
	}
	public String getPaySysNo() {
		return paySysNo;
	}
	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}
	public String getNotifyFileName() {
		return notifyFileName;
	}
	public void setNotifyFileName(String notifyFileName) {
		this.notifyFileName = notifyFileName;
	}
	public String getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
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
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
}

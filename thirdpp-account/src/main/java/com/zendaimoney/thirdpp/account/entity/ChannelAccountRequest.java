package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.util.Date;

public class ChannelAccountRequest implements Serializable{

	private static final long serialVersionUID = -6128515571441695707L;
	
	// 请求 id
	private String reqId;
	// 处理进程
	private String appName;
	// 对账渠道编码
	private String thirdTypeNo;
	// 商户号
	private String merchantNo;
	// 业务类型【来自于config 配置的业务类型】
	private String bizType;
	
	// 对账日期【根据当前时间进行计算，去当前日期（年月日）格式如 20151101】
	private String accountDay;
	// 配置 id
	private Long configId;
	// 下载文件开始时间
	private Date downloadStartTime;
	// 下载文件失败次数
	private int downloadFailedTimes;
	// 下载文件的名称
	private String downloadFileName;
	// 下载文件的大小 单位 KB
	private long downloadFileSize;
	// 下载文件的存放路径
	private String downloadFilePath;
	// 下载文件结束时间
	private Date downloadEndTime;
	
	// 总记录条数
	private String totalCountAmountDesc;
	
	// 入明细表开始时间
	private Date insertStartTime;
	// 入明细表失败次数
	private int insertFailedTimes;
	// 入明细表结束时间
	private Date insertEndTime;

	private int accountFailedTimes;

	// 备份开始时间
	private Date backupStartTime;
	
	private int backupFailedTimes;
	// 备份结束时间
	private Date backupEndTime;
	
	/**
	 * 0=初始状态 1=下载文件失败 2=下载文件成功 3=入流水表失败 4=入流水表成功 5=对账失败
	 * 6=对账成功 7=备份操作失败 8=备份操作成功
	 */
	private int status;
	// 手工对账状态 0：未处理，1：处理中，2：处理失败，3：处理成功
	private int handleAccountStatus;
	
	// 失败原因
	private String failedReason;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 预留字段
	private String spare1;
	// 预留字段
	private String spare2;

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getThirdTypeNo() {
		return thirdTypeNo;
	}

	public void setThirdTypeNo(String thirdTypeNo) {
		this.thirdTypeNo = thirdTypeNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getAccountDay() {
		return accountDay;
	}

	public void setAccountDay(String accountDay) {
		this.accountDay = accountDay;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Date getDownloadStartTime() {
		return downloadStartTime;
	}

	public void setDownloadStartTime(Date downloadStartTime) {
		this.downloadStartTime = downloadStartTime;
	}

	public int getDownloadFailedTimes() {
		return downloadFailedTimes;
	}

	public void setDownloadFailedTimes(int downloadFailedTimes) {
		this.downloadFailedTimes = downloadFailedTimes;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public long getDownloadFileSize() {
		return downloadFileSize;
	}

	public void setDownloadFileSize(long downloadFileSize) {
		this.downloadFileSize = downloadFileSize;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public Date getDownloadEndTime() {
		return downloadEndTime;
	}

	public void setDownloadEndTime(Date downloadEndTime) {
		this.downloadEndTime = downloadEndTime;
	}

	public Date getInsertStartTime() {
		return insertStartTime;
	}

	public void setInsertStartTime(Date insertStartTime) {
		this.insertStartTime = insertStartTime;
	}

	public int getInsertFailedTimes() {
		return insertFailedTimes;
	}

	public void setInsertFailedTimes(int insertFailedTimes) {
		this.insertFailedTimes = insertFailedTimes;
	}

	public Date getInsertEndTime() {
		return insertEndTime;
	}

	public void setInsertEndTime(Date insertEndTime) {
		this.insertEndTime = insertEndTime;
	}

	public int getAccountFailedTimes() {
		return accountFailedTimes;
	}

	public void setAccountFailedTimes(int accountFailedTimes) {
		this.accountFailedTimes = accountFailedTimes;
	}

	public Date getBackupStartTime() {
		return backupStartTime;
	}

	public void setBackupStartTime(Date backupStartTime) {
		this.backupStartTime = backupStartTime;
	}

	public int getBackupFailedTimes() {
		return backupFailedTimes;
	}

	public void setBackupFailedTimes(int backupFailedTimes) {
		this.backupFailedTimes = backupFailedTimes;
	}

	public Date getBackupEndTime() {
		return backupEndTime;
	}

	public void setBackupEndTime(Date backupEndTime) {
		this.backupEndTime = backupEndTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTotalCountAmountDesc() {
		return totalCountAmountDesc;
	}

	public void setTotalCountAmountDesc(String totalCountAmountDesc) {
		this.totalCountAmountDesc = totalCountAmountDesc;
	}

	public int getHandleAccountStatus() {
		return handleAccountStatus;
	}

	public void setHandleAccountStatus(int handleAccountStatus) {
		this.handleAccountStatus = handleAccountStatus;
	}

}

package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.util.Date;

public class BizsysAccountConfig implements Serializable {

	private static final long serialVersionUID = -2030214924809463564L;
	// 主键
	private Long id;
	
	private String appName;
	// 业务系统号
	private String bizSysNo;
	// 业务类型
	private String bizType;
	
	// ftp 服务器地址
	private String ftpServer;
	// ftp 服务器端口
	private String ftpPort;
	// FTP 登录帐号
	private String ftpUsername;
	// FTP 登录密码
	private String ftpPwd;
	// FTP 文件存放目录
	private String ftpPath;
	// 本地存放对账文件根目录
	private String localAccountRootPath;
	// 业务系统获取对账文件时间
	private String accountTime;
	// 对账文件名称格式
	private String fileNameFormat;
	// 对账文件后缀
	private String fileSuffix;
	// 对账文件编码
	private String fileEncoding;
	// 对账文件属性定义
	private String attrsDefinition;
	// 对账文件属性分隔符
	private String attrsSplit;
	// 最大推送失败次数
	private int maxPushFailedTimes;
	// 最大本地存储失败次数
	private int maxLocalizeFailedTimes;
	// 对账文件中使用的货币单位 0=元 1=分
	private String currencyUnit;
	// 状态 0= 关闭 1= 开启
	private int status;
	// 创建时间
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public String getFtpPath() {
		return ftpPath;
	}

	public void setFtpPath(String ftpPath) {
		this.ftpPath = ftpPath;
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public String getLocalAccountRootPath() {
		return localAccountRootPath;
	}

	public void setLocalAccountRootPath(String localAccountRootPath) {
		this.localAccountRootPath = localAccountRootPath;
	}

	public String getFileNameFormat() {
		return fileNameFormat;
	}

	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getAttrsDefinition() {
		return attrsDefinition;
	}

	public void setAttrsDefinition(String attrsDefinition) {
		this.attrsDefinition = attrsDefinition;
	}

	public String getAttrsSplit() {
		return attrsSplit;
	}

	public void setAttrsSplit(String attrsSplit) {
		this.attrsSplit = attrsSplit;
	}

	public int getMaxPushFailedTimes() {
		return maxPushFailedTimes;
	}

	public void setMaxPushFailedTimes(int maxPushFailedTimes) {
		this.maxPushFailedTimes = maxPushFailedTimes;
	}
	
	public int getMaxLocalizeFailedTimes() {
		return maxLocalizeFailedTimes;
	}

	public void setMaxLocalizeFailedTimes(int maxLocalizeFailedTimes) {
		this.maxLocalizeFailedTimes = maxLocalizeFailedTimes;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}

}

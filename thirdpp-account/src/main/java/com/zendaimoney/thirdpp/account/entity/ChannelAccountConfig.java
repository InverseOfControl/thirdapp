package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.util.Date;

public class ChannelAccountConfig implements Serializable {

	private static final long serialVersionUID = 4537487698431932649L;

	// 主键
	private Long id;
	
	private String appName;
	
	// 对账通道名称
	private String channelName;
	// 对账渠道编码
	private String thirdTypeNo;
	// 业务类型
	private String bizType;
	// 商户号
	private String merchantNo;
	// 对账文件下载方式 FTP/HTTP/HTTPS/SFTP
	private String fetchMethod;
	/**
	 * 获得对账文件方式 0=表示采用主动请求的方式 1=表示采用推送的方式 2=手动下载
	 */
	private int fetchType;

	// HTTP 请求下载地址 FETCH_METHOD=HTTP 时有效
	private String downloadUrl;
	// FTP 请求主机 FETCH_METHOD=FTP时有效
	private String server;
	// FTP 请求端口号 FETCH_METHOD=FTP时有效
	private int port;
	// FTP 请求登录用户名 FETCH_METHOD=FTP时有效
	private String loginUsername;

	// FTP 请求登录密码 FETCH_METHOD=FTP时有效
	private String loginPwd;
	// FTP 请求文件存放目录 FETCH_METHOD=FTP时有效
	private String filePath;
	// FTP 请求文件存放目录子目录 FETCH_METHOD=FTP时有效
	private String filePathSub;
	
	// 文件编码
	private String fileEncoding;
	// 对账文件名格式
	private String fileNameFormat;
	// 对账文件后缀名
	private String fileSuffix;
	// 从文件第几行开始读取对账记录
	private int fileStartIndex;
	// 对账文件头属性所在的行，跨行则行之间以中划线间隔
	private String fileHeaderAttrsIndex;
	// 对账时间 时分秒
	private String accountTime;
	// 对账文件属性之间的分隔符
	private String attrSplit;
	// 对账文件属性定义
	private String attrsDefinition;
	
	// 币种(0人民币)
	private String currency;
	// 对账文件中使用的单位 0 = 分 1 =元
	private String currencyUnit;
	
	// 下载操作最多失败次数
	private int maxDownloadFailedTimes;
	// 入库操作最多失败次数
	private int maxInsertFailedTimes;
	// 对账操作最多失败次数
	private int maxAccountFailedTimes;
	// 备份操作最大失败次数
	private int maxBackupFailedTimes;
	
	// 状态 0=关闭 1=开启
	private int status;
	
	public static final int CHANNEL_ACCOUNT_CONFIG_STATUS_CLOSE = 0;
	public static final int CHANNEL_ACCOUNT_CONFIG_STATUS_OPEN = 1;
	
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
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

	public String getFetchMethod() {
		return fetchMethod;
	}

	public void setFetchMethod(String fetchMethod) {
		this.fetchMethod = fetchMethod;
	}

	public int getFetchType() {
		return fetchType;
	}

	public void setFetchType(int fetchType) {
		this.fetchType = fetchType;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
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

	public int getFileStartIndex() {
		return fileStartIndex;
	}

	public void setFileStartIndex(int fileStartIndex) {
		this.fileStartIndex = fileStartIndex;
	}

	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}

	public String getAttrSplit() {
		return attrSplit;
	}

	public void setAttrSplit(String attrSplit) {
		this.attrSplit = attrSplit;
	}

	public String getAttrsDefinition() {
		return attrsDefinition;
	}

	public void setAttrsDefinition(String attrsDefinition) {
		this.attrsDefinition = attrsDefinition;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public int getMaxDownloadFailedTimes() {
		return maxDownloadFailedTimes;
	}

	public void setMaxDownloadFailedTimes(int maxDownloadFailedTimes) {
		this.maxDownloadFailedTimes = maxDownloadFailedTimes;
	}

	public int getMaxInsertFailedTimes() {
		return maxInsertFailedTimes;
	}

	public void setMaxInsertFailedTimes(int maxInsertFailedTimes) {
		this.maxInsertFailedTimes = maxInsertFailedTimes;
	}
	
	public int getMaxAccountFailedTimes() {
		return maxAccountFailedTimes;
	}

	public void setMaxAccountFailedTimes(int maxAccountFailedTimes) {
		this.maxAccountFailedTimes = maxAccountFailedTimes;
	}

	public int getMaxBackupFailedTimes() {
		return maxBackupFailedTimes;
	}

	public void setMaxBackupFailedTimes(int maxBackupFailedTimes) {
		this.maxBackupFailedTimes = maxBackupFailedTimes;
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

	public String getFilePathSub() {
		return filePathSub;
	}

	public void setFilePathSub(String filePathSub) {
		this.filePathSub = filePathSub;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getFileHeaderAttrsIndex() {
		return fileHeaderAttrsIndex;
	}

	public void setFileHeaderAttrsIndex(String fileHeaderAttrsIndex) {
		this.fileHeaderAttrsIndex = fileHeaderAttrsIndex;
	}
	
}

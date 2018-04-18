package com.zdmoney.manager.models;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zdmoney.manager.Validate.InsertCheck;

public class TppAccountChannelConfig {
	
    private Long id;
    
    @NotBlank(message = "请选择处理进程", groups = InsertCheck.class)
    @Length(max = 20, message = "处理进程名过长")
    private String appName;
    
    @NotBlank(message = "请选择第三方支付平台", groups = InsertCheck.class)
	@Length(max = 100, message = "第三方支付平台名称过长")
    private String channelName;
    
    @NotBlank(message = "请选择第三方支付平台", groups = InsertCheck.class)
	@Length(max = 4, message = "第三方支付平台编码过长")
    private String thirdTypeNo;

    @NotBlank(message = "商户号不能为空", groups = InsertCheck.class)
	@Length(max = 100, message = "商户号过长")
    private String merchantNo;

    @NotBlank(message = "请选择业务类型", groups = InsertCheck.class)
   	@Length(max = 100, message = "业务类型过长")
    private String bizType;
    
    @NotBlank(message = "文件下载方式不能为空", groups = InsertCheck.class)
	@Length(max = 20, message = "文件下载方式过长")
    private String fetchMethod;

    @NotBlank(message = "请选择获得文件方式", groups = InsertCheck.class)
    @Length(max = 1, message = "获得文件方式过长")
    private String fetchType;

    @Length(max = 100, message = "HTTP请求下载地址过长")
    private String downloadUrl;
    
    @Length(max = 100, message = "FTP主机名过长")
    private String server;
    
    @Length(max = 5, message = "FTP端口过长")
    private String port;
    
    @Length(max = 100, message = "FTP请求登录用户名过长")
    private String loginUsername;

    @Length(max = 100, message = "FTP请求登录密码过长")
    private String loginPwd;

    @Length(max = 200, message = "FTP请求文件存放目录过长")
    private String filePath;
    
    @Length(max = 200, message = "FTP请求文件存放目录子目录过长")
    private String filePathSub;

    @Length(max = 100, message = "对账文件名格式过长")
    private String fileNameFormat;

    @Length(max = 10, message = "对账文件后缀名过长")
    private String fileSuffix;

    @NotBlank(message = "第几行开始读取对账记录不能为空", groups = InsertCheck.class)
    @Length(max = 2, message = "第几行开始读取对账记录过长")
    @Pattern(regexp="[0-9]+",message="第几行开始读取对账记录含非数字")
    private String fileStartIndex;
    
    @Length(max = 10, message = "汇总信息所在行过长")
    private String fileHeaderAttrsIndex;
    
    @NotBlank(message = "文件编码不能为空", groups = InsertCheck.class)
  	@Length(max = 20, message = "文件编码过长")
    private String fileEncoding;
    
    @NotBlank(message = "对账时间不能为空", groups = InsertCheck.class)
	@Length(max = 10, message = "对账时间过长")
    private String accountTime;

    @Length(max = 2, message = "文件内容分隔符过长")
    private String attrSplit;

    @NotBlank(message = "文件属性定义不能为空", groups = InsertCheck.class)
	@Length(max = 200, message = "文件属性定义过长")
    private String attrsDefinition;

    @NotBlank(message = "请选择币种", groups = InsertCheck.class)
    @Length(max = 2, message = "币种过长")
    private String currency;
    
    @NotBlank(message = "请选择货币单位", groups = InsertCheck.class)
    @Length(max = 1, message = "货币单位过长")
    private String currencyUnit;
    
    @Pattern(regexp="[0-9]+",message="下载操作最多失败次数含非数字")
    @Length(max = 3, message = "下载操作最多失败次数过长")
    private String maxDownloadFailedTimes;

    @Pattern(regexp="[0-9]+",message="入库操作最多失败次数含非数字")
    @Length(max = 3, message = "入库操作最多失败次数过长")
    private String maxInsertFailedTimes;

    @Pattern(regexp="[0-9]+",message="最大对账失败次数过长含非数字")
    @Length(max = 3, message = "最大对账失败次数过长")
    private String maxAccountFailedTimes;
    
    @Length(max = 3, message = "备份操作最多失败次数过长")
    @Pattern(regexp="[0-9]+",message="备份操作最多失败次数含非数字")
    private String maxBackupFailedTimes;
    
    @NotBlank(message = "请选择对账通道状态", groups = InsertCheck.class)
    @Length(max = 1, message = "对账通道状态过长")
    private String status;
    
    private Date createTime;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getThirdTypeNo() {
        return thirdTypeNo;
    }

    public void setThirdTypeNo(String thirdTypeNo) {
        this.thirdTypeNo = thirdTypeNo == null ? null : thirdTypeNo.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getFetchMethod() {
        return fetchMethod;
    }

    public void setFetchMethod(String fetchMethod) {
        this.fetchMethod = fetchMethod == null ? null : fetchMethod.trim();
    }

    public String getFetchType() {
        return fetchType;
    }

    public void setFetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername == null ? null : loginUsername.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileNameFormat() {
        return fileNameFormat;
    }

    public void setFileNameFormat(String fileNameFormat) {
        this.fileNameFormat = fileNameFormat == null ? null : fileNameFormat.trim();
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getFileStartIndex() {
        return fileStartIndex;
    }

    public void setFileStartIndex(String fileStartIndex) {
        this.fileStartIndex = fileStartIndex;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime == null ? null : accountTime.trim();
    }

    public String getAttrSplit() {
        return attrSplit;
    }

    public void setAttrSplit(String attrSplit) {
        this.attrSplit = attrSplit == null ? null : attrSplit.trim();
    }

    public String getAttrsDefinition() {
        return attrsDefinition;
    }

    public void setAttrsDefinition(String attrsDefinition) {
        this.attrsDefinition = attrsDefinition == null ? null : attrsDefinition.trim();
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit == null ? null : currencyUnit.trim();
    }

    public String getMaxDownloadFailedTimes() {
        return maxDownloadFailedTimes;
    }

    public void setMaxDownloadFailedTimes(String maxDownloadFailedTimes) {
        this.maxDownloadFailedTimes = maxDownloadFailedTimes;
    }

    public String getMaxInsertFailedTimes() {
        return maxInsertFailedTimes;
    }

    public void setMaxInsertFailedTimes(String maxInsertFailedTimes) {
        this.maxInsertFailedTimes = maxInsertFailedTimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType == null ? "" : bizType.trim();
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName == null ? "" : appName.trim();
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding == null ? "" : fileEncoding.trim();
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server == null ? "" : server.trim();
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port == null ? "" : port.trim();
	}

	public String getFilePathSub() {
		return filePathSub;
	}

	public void setFilePathSub(String filePathSub) {
		this.filePathSub = filePathSub == null ? "" : filePathSub.trim();
	}

	public String getMaxBackupFailedTimes() {
		return maxBackupFailedTimes;
	}

	public void setMaxBackupFailedTimes(String maxBackupFailedTimes) {
		this.maxBackupFailedTimes = maxBackupFailedTimes == null ? "" : maxBackupFailedTimes.trim();
	}

	public String getMaxAccountFailedTimes() {
		return maxAccountFailedTimes;
	}

	public void setMaxAccountFailedTimes(String maxAccountFailedTimes) {
		this.maxAccountFailedTimes = maxAccountFailedTimes == null ? "" : maxAccountFailedTimes.trim();
	}

	public String getFileHeaderAttrsIndex() {
		return fileHeaderAttrsIndex;
	}

	public void setFileHeaderAttrsIndex(String fileHeaderAttrsIndex) {
		this.fileHeaderAttrsIndex = fileHeaderAttrsIndex;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
    
}
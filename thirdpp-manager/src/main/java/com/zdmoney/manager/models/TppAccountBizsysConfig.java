package com.zdmoney.manager.models;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zdmoney.manager.Validate.InsertCheck;

public class TppAccountBizsysConfig {
    private Long id;
    
    @NotBlank(message = "业务系统不能为空", groups = InsertCheck.class)
	@Length(max = 4, message = "业务系统过长")
    private String bizSysNo;
    
    @NotBlank(message = "业务类型不能为空", groups = InsertCheck.class)
	@Length(max = 100, message = "业务类型过长")
    private String bizType;
    
    @NotBlank(message = "FTP服务不能为空", groups = InsertCheck.class)
	@Length(max = 100, message = "FTP服务过长")
    private String ftpServer;
    
    @NotBlank(message = "FTP端口不能为空", groups = InsertCheck.class)
	@Length(max = 100, message = "FTP端口过长")
    private String ftpPort;

    @NotBlank(message = "FTP登录帐号不能为空", groups = InsertCheck.class)
	@Length(max = 100, message = "FTP登录帐号过长")
    private String ftpUsername;

    @NotBlank(message = "FTP登录密码不能为空", groups = InsertCheck.class)
   	@Length(max = 100, message = "FTP登录密码过长")
    private String ftpPwd;

    @NotBlank(message = "FTP文件存放目录不能为空", groups = InsertCheck.class)
   	@Length(max = 200, message = "FTP文件存放目录过长")
    private String ftpPath;

    @NotBlank(message = "本地存放对账文件根目录不能为空", groups = InsertCheck.class)
   	@Length(max = 200, message = "本地存放对账文件根目录过长")
    private String localAccountRootPath;

    @NotBlank(message = "对账文件名称格式不能为空", groups = InsertCheck.class)
   	@Length(max = 100, message = "对账文件名称格式过长")
    private String fileNameFormat;

    @NotBlank(message = "对账文件后缀不能为空", groups = InsertCheck.class)
   	@Length(max = 10, message = "对账文件后缀过长")
    private String fileSuffix;

    @NotBlank(message = "对账文件属性定义不能为空", groups = InsertCheck.class)
   	@Length(max = 200, message = "对账文件属性定义过长")
    private String attrsDefinition;

   	@Length(max = 100, message = "对账文件文件头属性定义过长")
    private String headerAttrsDefinition;

   	@NotBlank(message = "请选择文件内容分隔符", groups = InsertCheck.class)
    @Length(max = 2, message = "文件内容分隔符过长")
    private String attrsSplit;

    @Pattern(regexp="[0-9]+",message="最大推送失败次数含非数字")
    private String maxPushFailedTimes;

    @NotBlank(message = "对账文件中使用的货币单位不能为空", groups = InsertCheck.class)
   	@Length(max = 10, message = "对账文件中使用的货币单位过长")
    private String currencyUnit;

    @NotBlank(message = "请选择状态", groups = InsertCheck.class)
    @Length(max = 1, message = "状态过长")
    private String status;

    private Date createTime;
    
    @NotBlank(message = "请选择处理进程", groups = InsertCheck.class)
    @Length(max = 20, message = "处理进程过长")
    private String appName;
    
    @Length(max = 3, message = "最大本地存储失败次数过长")
    @Pattern(regexp="[0-9]+",message="最大本地存储失败次数含非数字")
    private String maxLocalizeFailedTimes;
    
    @NotBlank(message = "请选择对账文件编码", groups = InsertCheck.class)
    @Length(max = 15, message = "对账文件编码过长")
    private String fileEncoding;

    @Length(max = 20, message = "对账时间过长")
    private String accountTime;
    
    @NotBlank(message = "请选择货种", groups = InsertCheck.class)
    @Length(max = 20, message = "币种过长")
    private String currency;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBizSysNo() {
        return bizSysNo;
    }

    public void setBizSysNo(String bizSysNo) {
        this.bizSysNo = bizSysNo == null ? null : bizSysNo.trim();
    }

    public String getFtpUsername() {
        return ftpUsername;
    }

    public void setFtpUsername(String ftpUsername) {
        this.ftpUsername = ftpUsername == null ? null : ftpUsername.trim();
    }

    public String getFtpPwd() {
        return ftpPwd;
    }

    public void setFtpPwd(String ftpPwd) {
        this.ftpPwd = ftpPwd == null ? null : ftpPwd.trim();
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath == null ? null : ftpPath.trim();
    }

    public String getLocalAccountRootPath() {
        return localAccountRootPath;
    }

    public void setLocalAccountRootPath(String localAccountRootPath) {
        this.localAccountRootPath = localAccountRootPath == null ? null : localAccountRootPath.trim();
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

    public String getAttrsDefinition() {
        return attrsDefinition;
    }

    public void setAttrsDefinition(String attrsDefinition) {
        this.attrsDefinition = attrsDefinition == null ? null : attrsDefinition.trim();
    }

    public String getHeaderAttrsDefinition() {
        return headerAttrsDefinition;
    }

    public void setHeaderAttrsDefinition(String headerAttrsDefinition) {
        this.headerAttrsDefinition = headerAttrsDefinition == null ? null : headerAttrsDefinition.trim();
    }

    public String getAttrsSplit() {
        return attrsSplit;
    }

    public void setAttrsSplit(String attrsSplit) {
        this.attrsSplit = attrsSplit == null ? null : attrsSplit.trim();
    }

    public String getMaxPushFailedTimes() {
        return maxPushFailedTimes;
    }

    public void setMaxPushFailedTimes(String maxPushFailedTimes) {
        this.maxPushFailedTimes = maxPushFailedTimes;
    }

    public String getCurrencyUnit() {
        return currencyUnit;
    }

    public void setCurrencyUnit(String currencyUnit) {
        this.currencyUnit = currencyUnit == null ? null : currencyUnit.trim();
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
		this.bizType = bizType;
	}

	public String getFtpServer() {
		return ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer == null ? null : ftpServer.trim();
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort == null ? null : ftpPort.trim();
	}
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName == null ? "" : appName.trim();
	}

	public String getMaxLocalizeFailedTimes() {
		return maxLocalizeFailedTimes;
	}

	public void setMaxLocalizeFailedTimes(String maxLocalizeFailedTimes) {
		this.maxLocalizeFailedTimes = maxLocalizeFailedTimes  == null ? "" : maxLocalizeFailedTimes.trim();
	}

	public String getFileEncoding() {
		return fileEncoding;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding  == null ? "" : fileEncoding.trim();
	}

	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
    
}
package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;







public class TThreadPool implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6350715184018786168L;
	
	private Integer id;
 	@NotBlank(message = "请选择业务类型", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 4, message = "业务类型超过4个字符") 
	private String bizType;
	private String bizTypeName;
	private String infType;
	// 业务系统编码
	@NotBlank(message = "请选择支付通道", groups = com.zdmoney.manager.Validate.InsertCheck.class)	 
	@Length(max = 4, message = "支付通道编码超过4个字符")
	private String paySysNo;
	private String paySysName;
	
	private String minSize;
	private String maxSize;
	private String queueSize;
	@NotBlank(message = "请选择是否生效", groups = com.zdmoney.manager.Validate.InsertCheck.class)	 
	private String isActive;
	private Date createTime;
	private String creater;
	private Date updateTime;
	private String updater;
	@NotBlank(message = "请输入睡眠时间", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Pattern(regexp="[0-9]+",message="睡眠时间含非数字")
	private String sleepTime;
	@NotBlank(message = "请输入应用程序名称", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 100, message = "应用程序名称100个字符") 
	private String appName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getInfType() {
		return infType;
	}
	public void setInfType(String infType) {
		this.infType = infType;
	}
	public String getPaySysNo() {
		return paySysNo;
	}
	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}
	public String getMinSize() {
		return minSize;
	}
	public void setMinSize(String minSize) {
		this.minSize = minSize;
	}
	public String getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(String maxSize) {
		this.maxSize = maxSize;
	}
	public String getQueueSize() {
		return queueSize;
	}
	public void setQueueSize(String queueSize) {
		this.queueSize = queueSize;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	
	public String getSleepTime() {
		return sleepTime;
	}
	public void setSleepTime(String sleepTime) {
		this.sleepTime = sleepTime;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getBizTypeName() {
		return bizTypeName;
	}
	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	public String getPaySysName() {
		return paySysName;
	}
	public void setPaySysName(String paySysName) {
		this.paySysName = paySysName;
	}
	

}

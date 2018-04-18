package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TAppIps implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5352156423977619755L;
	private Integer id;
	@NotBlank(message = "业务系统编号不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 4, message = "业务系统编号超过4个字节") 	
	private String appCode;
	private String appName;
	@NotBlank(message = "ip不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 64, message = "ip超过64个字节") 	
	private String ip;
	private Date createTime;
	private String creater;
	private Date updateTime;
	private String updater;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	

}

package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 *业务系统和IP对应关系对象
 * 
 * @author 00231257
 *
 */
public class SysAppIPS implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8020001936781255579L;

	// 主键
	private long id;

	// 业务系统编码
	private String appCode;

	// IP
	private String ip;

	// 创建时间
	private String createTime;

	// 修改时间
	private String updateTime;

	// 创建人
	private String creater;

	// 修改人
	private String updater;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

}

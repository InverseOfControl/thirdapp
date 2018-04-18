package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 * 系统信息类别和应用系统对应关系对象
 * 
 * @author 00231257
 *
 */
public class SysInfoCategoryAPPS implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 503435848603769643L;

	// 主键
	private long id;

	// 信息类别编码
	private String infoCategoryCode;

	// 应用系统编码
	private String appCode;

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
    

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
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

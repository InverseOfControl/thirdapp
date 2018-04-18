package com.zendaimoney.trust.channel.entity;

import java.io.Serializable;

/**
 * 系统信息类别
 * 
 * @author mencius
 *
 */
public class SysInfoCategory implements Serializable {

	/**
	 * implements Serializable
	 */
	private static final long serialVersionUID = -3758212164163924192L;

	// 主键
	private Long id;

	// 信息类别编码
	private String infoCategoryCode;

	// 信息类别名称
	private String infoCategoryName;

	// 优先级(3最高 2高 1中 0普通)
	private Integer priority;

	// 创建人
	private String creater;

	// 创建时间
	private String createTime;

	// 更新时间
	private String updateTime;

	// 修改人
	private String updater;

	// 商户号类型
	private String merchantType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}

	public String getInfoCategoryName() {
		return infoCategoryName;
	}

	public void setInfoCategoryName(String infoCategoryName) {
		this.infoCategoryName = infoCategoryName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
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

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

}

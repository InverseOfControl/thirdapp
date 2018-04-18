package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

/**
 * 系统信息类别
 * 
 * @author 00231257
 *
 */
public class SysInfoCategory implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 551572275552640859L;

	// 主键
	private long id;

	// 信息类别编码
	private String infoCategoryCode;

	// 信息类别名称
	private String infoCategoryName;

	// 优先级(3最高 2高 1中 0普通)
	private Integer priority;

	// 创建时间
	private String createTime;

	// 修改时间
	private String updateTime;

	// 创建人
	private String creater;

	// 修改人
	private String updater;
	
	// 支付通道
	private String paymentChannel;
	
	// 通道规则
	private String channelRules;
	

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

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getChannelRules() {
		return channelRules;
	}

	public void setChannelRules(String channelRules) {
		this.channelRules = channelRules;
	}

}

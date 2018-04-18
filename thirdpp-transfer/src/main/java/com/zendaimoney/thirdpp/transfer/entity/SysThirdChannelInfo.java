package com.zendaimoney.thirdpp.transfer.entity;

import java.io.Serializable;

public class SysThirdChannelInfo implements Serializable {

	// 0业务通道关闭
	public static final int STATUS_CLOSED = 0;

	// 0业务通道开启
	public static final int STATUS_OPENED = 1;

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2087065717978344516L;

	/**
	 * 主键
	 */
	private Long id;

	// 商户号
	private String merchantNo;

	/**
	 * 通道名称
	 */
	private String channelName;

	/**
	 * 第三方通道编码
	 */
	private String thirdTypeNo;

	// 商户号类型
	private String merchantType;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String userPwd;

	/**
	 * 证书路径
	 */
	private String certName;

	/**
	 * 证书密码
	 */
	private String certPwd;

	// 创建人
	private String creater;

	// 创建时间
	private String createTime;

	// 更新时间
	private String updateTime;

	// 修改人
	private String updater;

	// 状态(0业务通道关闭1业务通道开启)
	private Integer status;

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
		this.channelName = channelName;
	}

	public String getThirdTypeNo() {
		return thirdTypeNo;
	}

	public void setThirdTypeNo(String thirdTypeNo) {
		this.thirdTypeNo = thirdTypeNo;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getCertPwd() {
		return certPwd;
	}

	public void setCertPwd(String certPwd) {
		this.certPwd = certPwd;
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

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}

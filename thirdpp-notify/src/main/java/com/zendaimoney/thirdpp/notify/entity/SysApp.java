package com.zendaimoney.thirdpp.notify.entity;

import java.util.Date;

/**
 * @author 00225642
 * 
 */
public class SysApp {

	//主键(对应SEQ_TPP_SYS_APP序列)
	private long id;
	//业务系统名称
	private String appName;
	//业务系统编号
	private String appCode;
	//是否可用(1可用、0不可用)
	private String isFlag;
	//联系人姓名
	private String contactName;
	//联系人手机
	private String contactMobile;
	//备注
	private String remark;
	//创建时间
	private Date createTime;
	//创建人
	private String creater;
	//修改时间
	private Date updateTime;
	//修改人
	private String updater;
	//备用1
	private String spare1;
	//备用2
	private String spare2;
	
	// 线下代扣通知URL
	private String collectNotifyUrl;
	
	// 线下代付通知URL
	private String payNotifyUrl;
	
	// 线上退款通知URL
	private String cashBackNotifyUrl;
	
	// 线上提现通知URL
	private String cashDrawNotifyUrl;
	
	// 线上订单支付通知URL
	private String orderPayNotifyUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getIsFlag() {
		return isFlag;
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getSpare1() {
		return spare1;
	}

	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}

	public String getSpare2() {
		return spare2;
	}

	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}

	public String getCollectNotifyUrl() {
		return collectNotifyUrl;
	}

	public void setCollectNotifyUrl(String collectNotifyUrl) {
		this.collectNotifyUrl = collectNotifyUrl;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getCashBackNotifyUrl() {
		return cashBackNotifyUrl;
	}

	public void setCashBackNotifyUrl(String cashBackNotifyUrl) {
		this.cashBackNotifyUrl = cashBackNotifyUrl;
	}

	public String getCashDrawNotifyUrl() {
		return cashDrawNotifyUrl;
	}

	public void setCashDrawNotifyUrl(String cashDrawNotifyUrl) {
		this.cashDrawNotifyUrl = cashDrawNotifyUrl;
	}

	public String getOrderPayNotifyUrl() {
		return orderPayNotifyUrl;
	}

	public void setOrderPayNotifyUrl(String orderPayNotifyUrl) {
		this.orderPayNotifyUrl = orderPayNotifyUrl;
	}
	
}

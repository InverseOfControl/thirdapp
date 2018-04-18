package com.zdmoney.manager.models;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class TCategoryApps implements Serializable {

	private static final long serialVersionUID = 9064176962829140816L;
	private Integer id;
	@NotBlank(message = "信息类别编码不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 10, message = "信息类别编码超过10个字节")
	@Pattern(regexp = "^[a-z0-9A-Z]+$", message = "信息类别编码只能输入数字和字母")
	private String infoCateGoryCode;
	private String appCode;

	private String appName;
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	@NotBlank(message = "信息类别名称不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 50, message = "信息类别名称超过50个字节")
	private String infoCateGoryName;
	private String priority;
	private String merchantType;
	@NotBlank(message = "备注不能为空", groups = com.zdmoney.manager.Validate.InsertCheck.class)
	@Length(max = 512, message = "备注超过512个字节")
	private String note;
	private String paymentChannelName;
	private String channelRule;

	@Length(max = 10, message = "支付通道超过10个字节")
	private String paymentChannel;
	private String noText;

	public String getNoText() {
		return noText;
	}

	public void setNoText(String noText) {
		this.noText = noText;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	private String noteNo;

	public String getNoteNo() {
		return noteNo;
	}

	public void setNoteNo(String noteNo) {
		this.noteNo = noteNo;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInfoCateGoryCode() {
		return infoCateGoryCode;
	}

	public void setInfoCateGoryCode(String infoCateGoryCode) {
		this.infoCateGoryCode = infoCateGoryCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getInfoCateGoryName() {
		return infoCateGoryName;
	}

	public void setInfoCateGoryName(String infoCateGoryName) {
		this.infoCateGoryName = infoCateGoryName;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}

	public String getChannelRule() {
		return channelRule;
	}

	public void setChannelRule(String channelRule) {
		this.channelRule = channelRule;
	}
	
}

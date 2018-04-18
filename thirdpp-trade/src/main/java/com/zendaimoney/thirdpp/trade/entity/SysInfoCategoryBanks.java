package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;
import java.util.Date;

public class SysInfoCategoryBanks  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	// 信息类别编码
	private String infoCategoryCode;
	
	// 银行编码
	private String bankCode;
	
	// 第三方支付通道
	private String paySysNo;
	
	// 创建人
	private String creater;
	
	// 创建时间
	private String createTime;
	
	// 更新人
	private String updater;
	
	// 更新时间
	private String updateTime;

	

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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
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

	
}

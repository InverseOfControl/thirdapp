package com.zendaimoney.thirdpp.trade.entity;

public class BankOrgInfo {

	// 主键
	private Integer id;

	// 银行分行机构编码
	private String bankOrgNo;

	// 银行分行机构名称
	private String bankOrgName;

	// 总行编码
	private String bankCode;

	// 机构所在省份编码
	private String bankOrgProvinceNo;

	// 机构所在城市编码
	private String bankOrgProvinceCityNo;

	// 支付联行号
	private String bankLineNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBankOrgNo() {
		return bankOrgNo;
	}

	public void setBankOrgNo(String bankOrgNo) {
		this.bankOrgNo = bankOrgNo;
	}

	public String getBankOrgName() {
		return bankOrgName;
	}

	public void setBankOrgName(String bankOrgName) {
		this.bankOrgName = bankOrgName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankOrgProvinceNo() {
		return bankOrgProvinceNo;
	}

	public void setBankOrgProvinceNo(String bankOrgProvinceNo) {
		this.bankOrgProvinceNo = bankOrgProvinceNo;
	}

	public String getBankOrgProvinceCityNo() {
		return bankOrgProvinceCityNo;
	}

	public void setBankOrgProvinceCityNo(String bankOrgProvinceCityNo) {
		this.bankOrgProvinceCityNo = bankOrgProvinceCityNo;
	}

	public String getBankLineNo() {
		return bankLineNo;
	}

	public void setBankLineNo(String bankLineNo) {
		this.bankLineNo = bankLineNo;
	}

}

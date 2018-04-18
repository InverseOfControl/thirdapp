package com.zendaimoney.thirdpp.channel.pub.vo;

import java.io.Serializable;

public class BankCardBinQueryRespVO implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 *  银行编码
	 */
	private String bankCode;
	
	/**
	 *  银行名称
	 */
	private String bankName;

	/**
	 *  银行卡号
	 */
	private String bankCardNo;

	/**
	 *  付款人银行卡类型
	 */
	private String bankCardType;

	/**
	 *  卡bin
	 */
	private String bankCardBin;
	
	/**
	 * 卡长度
	 */
	private int bankCardLen;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCardBin() {
		return bankCardBin;
	}

	public void setBankCardBin(String bankCardBin) {
		this.bankCardBin = bankCardBin;
	}

	public int getBankCardLen() {
		return bankCardLen;
	}

	public void setBankCardLen(int bankCardLen) {
		this.bankCardLen = bankCardLen;
	}
	
	@Override
	public String toString() {
		return "BankCardBinQueryRespVO["
				+ "bankCode:" + this.bankCode
				+ ", bankName:" + this.bankName 
				+ ", bankCardNo:" + this.bankCardNo
				+ ", bankCardType:" + this.bankCardType
				+ ", bankCardBin:" + this.bankCardBin
				+ ", bankCardLen:" + this.bankCardLen
				+ "]";
	}
	
}

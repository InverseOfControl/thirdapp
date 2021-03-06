package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class UsrbDetailReq implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 用户号
	 */
	@CmbAnnotation(index = 1, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String userNo;
	
	/**
	 * 用户虚拟子账户号
	 */
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String virtualSubNo;
	
	/**
	 * 银行账户类型
	 */
	@CmbAnnotation(index = 3, length = 1, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankCardType;
	
	/**
	 * 银行账户号
	 */
	@CmbAnnotation(index = 4, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankCardNo;
	
	/**
	 * 银行账户名
	 */
	@CmbAnnotation(index = 5, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String userName;
	
	/**
	 * 账户开户银行
	 */
	@CmbAnnotation(index = 6, length = 40, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankName;
	
	/**
	 * 账户开户分支行
	 */
	@CmbAnnotation(index = 7, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankSubName;
	
	/**
	 * 手机号码
	 */
	@CmbAnnotation(index = 8, length = 11, rightFill = true, filler = Constants.BLANK, hex = false)
	private String mobile;
	
	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 9, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 10, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String msg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 11, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getVirtualSubNo() {
		return virtualSubNo;
	}

	public void setVirtualSubNo(String virtualSubNo) {
		this.virtualSubNo = virtualSubNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankSubName() {
		return bankSubName;
	}

	public void setBankSubName(String bankSubName) {
		this.bankSubName = bankSubName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

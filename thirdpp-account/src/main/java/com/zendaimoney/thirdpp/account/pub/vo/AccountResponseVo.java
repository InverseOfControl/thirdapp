package com.zendaimoney.thirdpp.account.pub.vo;

import java.io.Serializable;

public class AccountResponseVo implements Serializable{

	private static final long serialVersionUID = 4763556461199857594L;

	public static final String SUCCESS_RESPONSE_CODE = "000000";
	
	public static final String FAILED_RESPONSE_CODE = "111111";
	
	private String code;
	
	private String msg;
	
	public AccountResponseVo(){}
	
	public AccountResponseVo(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

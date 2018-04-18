package com.zendaimoney.trust.channel.pub.vo.resp;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class CommonResp implements Serializable{
	

	private static final long serialVersionUID = 1L;
	
	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 2, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String msg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 3, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
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
	
	
}

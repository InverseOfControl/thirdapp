package com.zendaimoney.trust.channel.pub.vo.resp;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 用户开户响应对象
 * @author mencius
 *
 */
public class UsraResp extends CommonResp {

	private static final long serialVersionUID = -6435862945176834064L;

	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 用户号
	 */
	@CmbAnnotation(index = 2, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String userNo;
	
	/**
	 * 用户虚拟子账户号
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String virtualSubNo;
	
	/**
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 4, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String msg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 5, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
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
	
	
	
}

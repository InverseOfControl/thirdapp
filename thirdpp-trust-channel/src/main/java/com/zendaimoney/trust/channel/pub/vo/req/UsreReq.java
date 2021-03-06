package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 用户修改信息
 * @author 00233197
 *
 */
public class UsreReq extends TrustBizReqVo{

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
	 * 用户名
	 */
	@CmbAnnotation(index = 3, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String userName;
	
	/**
	 * 证件类型
	 */
	@CmbAnnotation(index = 4, length = 3, rightFill = true, filler = Constants.BLANK, hex = false)
	private String idType;
	
	/**
	 * 证件号码
	 */
	@CmbAnnotation(index = 5, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String idNo;
	
	/**
	 * 手机号码
	 */
	@CmbAnnotation(index = 6, length = 11, rightFill = true, filler = Constants.BLANK, hex = false)
	private String mobile;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 7, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public String getVirtualSubNo() {
		return virtualSubNo;
	}

	public void setVirtualSubNo(String virtualSubNo) {
		this.virtualSubNo = virtualSubNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}

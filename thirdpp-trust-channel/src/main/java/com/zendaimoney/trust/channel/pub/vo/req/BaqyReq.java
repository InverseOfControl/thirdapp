package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 用户绑卡
 * @author 00233197
 *
 */
public class BaqyReq extends TrustBizReqVo{

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
	 * 备用
	 */
	@CmbAnnotation(index = 3, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
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

}

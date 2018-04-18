package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 关闭团(GROC)
 * @author mencius
 *
 */
public class GrocReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 1	团编号	30	M
	 */
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String groupNo;
	
	/**
	 * 2	团用户号	20	M
	 */
	@CmbAnnotation(index = 2, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String groupUserNo;
	
	/**
	 * 3	团虚拟子账户号	30	M
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String gVirtualSubNo;
	
	/**
	 * 4	备用	200	C
	 */
	@CmbAnnotation(index = 4, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getGroupUserNo() {
		return groupUserNo;
	}

	public void setGroupUserNo(String groupUserNo) {
		this.groupUserNo = groupUserNo;
	}

	public String getgVirtualSubNo() {
		return gVirtualSubNo;
	}

	public void setgVirtualSubNo(String gVirtualSubNo) {
		this.gVirtualSubNo = gVirtualSubNo;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	

}

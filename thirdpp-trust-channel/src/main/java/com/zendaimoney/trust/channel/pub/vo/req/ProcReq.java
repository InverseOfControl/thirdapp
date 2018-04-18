package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 关闭散标(PROC)
 * @author mencius
 *
 */
public class ProcReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 1	散标编号	30	M	编号唯一
	 */
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String disperseSubjectNo;
	
	/**
	 * 2	借款人用户号	20	M
	 */
	@CmbAnnotation(index = 2, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String borrowerNo;
	
	/**
	 * 3	借款人虚拟子账户号	30	M
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bVirtualSubNo;
	
	/**
	 * 4	备用	200	C
	 */
	@CmbAnnotation(index = 4, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getDisperseSubjectNo() {
		return disperseSubjectNo;
	}

	public void setDisperseSubjectNo(String disperseSubjectNo) {
		this.disperseSubjectNo = disperseSubjectNo;
	}

	public String getBorrowerNo() {
		return borrowerNo;
	}

	public void setBorrowerNo(String borrowerNo) {
		this.borrowerNo = borrowerNo;
	}

	public String getbVirtualSubNo() {
		return bVirtualSubNo;
	}

	public void setbVirtualSubNo(String bVirtualSubNo) {
		this.bVirtualSubNo = bVirtualSubNo;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
}

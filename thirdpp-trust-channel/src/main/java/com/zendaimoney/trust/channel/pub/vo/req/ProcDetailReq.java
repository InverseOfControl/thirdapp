package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 关闭散标(PROC)-批量明细
 * @author mencius
 *
 */
public class ProcDetailReq implements Serializable {

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
	 * 4	交易返回码	7	C
	 */
	@CmbAnnotation(index = 4, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 5	交易描述信息	80	C
	 */
	@CmbAnnotation(index = 5, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retMsg;
	
	/**
	 * 6	备用	200	C
	 */
	@CmbAnnotation(index = 6, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

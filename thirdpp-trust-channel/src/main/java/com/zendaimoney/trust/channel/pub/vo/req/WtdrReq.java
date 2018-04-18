package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 提现请求(WTDR)
 * @author mencius
 *
 */
public class WtdrReq extends TrustBizReqVo {

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
	 * 收款账户号	30	M
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankCardNo;
	
	/**
	 * 收款账户名	160	C	HEX
	 */
	@CmbAnnotation(index = 4, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String userName;
	
	/**
	 * 收款账户开户银行	80	C	HEX
	 */
	@CmbAnnotation(index = 5, length = 80, rightFill = true, filler = Constants.BLANK, hex = true)
	private String bankName;
	
	/**
	 * 收款账户开户分支行	160	C	HEX
	 */
	@CmbAnnotation(index = 6, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String bankSubName;
	
	/**
	 * 提现金额	15	M	以分为单位，左补零
	 */
	@CmbAnnotation(index = 7, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amount;
	
	/**
	 * 提现摘要	80	C	HEX
	 */
	@CmbAnnotation(index = 8, length = 80, rightFill = true, filler = Constants.BLANK, hex = true)
	private String note;
	
	/**
	 * 平台垫付用户号	20 C
	 */
	@CmbAnnotation(index = 9, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String advanceUserNo;
	
	/**
	 * 平台垫付虚拟子账户	30 C
	 */
	@CmbAnnotation(index = 10, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String advanceSubNo;
	
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

	public String getVirtualSubNo() {
		return virtualSubNo;
	}

	public void setVirtualSubNo(String virtualSubNo) {
		this.virtualSubNo = virtualSubNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAdvanceUserNo() {
		return advanceUserNo;
	}

	public void setAdvanceUserNo(String advanceUserNo) {
		this.advanceUserNo = advanceUserNo;
	}

	public String getAdvanceSubNo() {
		return advanceSubNo;
	}

	public void setAdvanceSubNo(String advanceSubNo) {
		this.advanceSubNo = advanceSubNo;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	
}

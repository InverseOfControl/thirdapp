package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 自助转账充值请求(SCHG)
 * @author mencius
 *
 */
public class SchgReq extends TrustBizReqVo {

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
	 * 自助转账交易流水
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String transferFlow;
	
	/**
	 * 金额
	 */
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amount;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 5, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
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


	public String getTransferFlow() {
		return transferFlow;
	}

	public void setTransferFlow(String transferFlow) {
		this.transferFlow = transferFlow;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
}

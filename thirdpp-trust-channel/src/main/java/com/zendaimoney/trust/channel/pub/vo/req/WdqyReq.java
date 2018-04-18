package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 提现请求查询
 * @author 00233197
 *
 */
public class WdqyReq extends TrustBizReqVo {

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
	 * 原提现商户流水	30	M
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String cashDrawFlow;
		
	/**
	 * 原提现金额	15	M	以分为单位，左补零
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

	public String getCashDrawFlow() {
		return cashDrawFlow;
	}

	public void setCashDrawFlow(String cashDrawFlow) {
		this.cashDrawFlow = cashDrawFlow;
	}

	
}

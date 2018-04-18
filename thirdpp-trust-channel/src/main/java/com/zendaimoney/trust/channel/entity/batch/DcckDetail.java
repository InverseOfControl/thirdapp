package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class DcckDetail implements Serializable {

	private static final long serialVersionUID = 7571799194546857995L;

	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK)
	private String mTradeFlow;

	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String bTradeFlow;

	// 用户号
	@CmbAnnotation(index = 3, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;

	// 用户虚拟子账户号
	@CmbAnnotation(index = 4, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;

	// 交易标识码
	@CmbAnnotation(index = 5, length = 4, rightFill = true, filler = Constants.BLANK)
	private String tradeCode;

	@CmbAnnotation(index = 6, length = 1, rightFill = true, filler = Constants.BLANK)
	private String tradeDirection;

	@CmbAnnotation(index = 7, length = 15, rightFill = false, filler = Constants.ZERO)
	private String amount;

	// 备用 C
	@CmbAnnotation(index = 8, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getmTradeFlow() {
		return mTradeFlow;
	}

	public void setmTradeFlow(String mTradeFlow) {
		this.mTradeFlow = mTradeFlow;
	}

	public String getbTradeFlow() {
		return bTradeFlow;
	}

	public void setbTradeFlow(String bTradeFlow) {
		this.bTradeFlow = bTradeFlow;
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

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}

	public String getTradeDirection() {
		return tradeDirection;
	}

	public void setTradeDirection(String tradeDirection) {
		this.tradeDirection = tradeDirection;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

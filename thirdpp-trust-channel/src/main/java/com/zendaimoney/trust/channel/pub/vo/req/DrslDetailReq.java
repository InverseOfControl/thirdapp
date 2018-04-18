package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class DrslDetailReq implements Serializable {

	private static final long serialVersionUID = -5085893361940210595L;

	// 用户号
	@CmbAnnotation(index = 1, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;

	// 用户虚拟子账户号
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;

	// 清分金额
	@CmbAnnotation(index = 3, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal amount;

	// 交易返回码 C
	@CmbAnnotation(index = 4, length = 7, rightFill = true, filler = Constants.BLANK)
	private String retCode;

	// 交易返回描述信息 C
	@CmbAnnotation(index = 5, length = 80, rightFill = true, filler = Constants.BLANK)
	private String retMsg;

	// 备用 C
	@CmbAnnotation(index = 6, length = 200, rightFill = true, filler = Constants.BLANK)
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

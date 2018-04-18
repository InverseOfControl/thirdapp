package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class BcckDetail implements Serializable {

	private static final long serialVersionUID = 7571799194546857995L;

	// 用户号
	@CmbAnnotation(index = 1, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;

	// 用户虚拟子账户号
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;

	// 可用金额
	@CmbAnnotation(index = 3, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal avaliableAmount;

	// 冻结金额
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal freezeAmount;

	// 到帐金额
	@CmbAnnotation(index = 5, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal inAmount;

	// 待清算金额
	@CmbAnnotation(index = 6, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal needSettleAmount;

	// 垫付金额
	@CmbAnnotation(index = 7, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal advanceAmount;

	// 备用 C
	@CmbAnnotation(index = 8, length = 200, rightFill = true, filler = Constants.BLANK)
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

	public BigDecimal getAvaliableAmount() {
		return avaliableAmount;
	}

	public void setAvaliableAmount(BigDecimal avaliableAmount) {
		this.avaliableAmount = avaliableAmount;
	}

	public BigDecimal getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(BigDecimal freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public BigDecimal getNeedSettleAmount() {
		return needSettleAmount;
	}

	public void setNeedSettleAmount(BigDecimal needSettleAmount) {
		this.needSettleAmount = needSettleAmount;
	}

	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

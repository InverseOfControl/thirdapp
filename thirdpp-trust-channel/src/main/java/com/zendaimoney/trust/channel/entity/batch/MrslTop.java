package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class MrslTop implements Serializable {

	private static final long serialVersionUID = -8890585720614365550L;

	// 清算日期 M
	@CmbAnnotation(index = 1, length = 8, rightFill = true, filler = Constants.BLANK)
	private String settleDate;

	// 三方支付公司标识 M
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String thirdpartyIdentify;

	// 清算总笔数 M
	@CmbAnnotation(index = 3, length = 6, rightFill = false, filler = Constants.ZERO)
	private int totalCount;

	// 清算总金额 M
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal totalAmount;

	// 实际入账金额 M
	@CmbAnnotation(index = 5, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal actualInAmount;

	// 垫付平台用户号 C
	@CmbAnnotation(index = 6, length = 20, rightFill = true, filler = Constants.BLANK)
	private String advancePlatformUserNo;

	// 垫付平台虚拟户 C
	@CmbAnnotation(index = 7, length = 30, rightFill = true, filler = Constants.BLANK)
	private String advancePlatformVirtualSubNo;

	// 清算成功笔数 C
	@CmbAnnotation(index = 8, length = 6, rightFill = false, filler = Constants.ZERO)
	private int settleSuccessCount;
    
	// 清算成功金额 C
	@CmbAnnotation(index = 9, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal settleSuccessAmount;

	// 备用 C
	@CmbAnnotation(index = 10, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getThirdpartyIdentify() {
		return thirdpartyIdentify;
	}

	public void setThirdpartyIdentify(String thirdpartyIdentify) {
		this.thirdpartyIdentify = thirdpartyIdentify;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getActualInAmount() {
		return actualInAmount;
	}

	public void setActualInAmount(BigDecimal actualInAmount) {
		this.actualInAmount = actualInAmount;
	}

	public String getAdvancePlatformUserNo() {
		return advancePlatformUserNo;
	}

	public void setAdvancePlatformUserNo(String advancePlatformUserNo) {
		this.advancePlatformUserNo = advancePlatformUserNo;
	}

	public String getAdvancePlatformVirtualSubNo() {
		return advancePlatformVirtualSubNo;
	}

	public void setAdvancePlatformVirtualSubNo(
			String advancePlatformVirtualSubNo) {
		this.advancePlatformVirtualSubNo = advancePlatformVirtualSubNo;
	}

	public int getSettleSuccessCount() {
		return settleSuccessCount;
	}

	public void setSettleSuccessCount(int settleSuccessCount) {
		this.settleSuccessCount = settleSuccessCount;
	}

	public BigDecimal getSettleSuccessAmount() {
		return settleSuccessAmount;
	}

	public void setSettleSuccessAmount(BigDecimal settleSuccessAmount) {
		this.settleSuccessAmount = settleSuccessAmount;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

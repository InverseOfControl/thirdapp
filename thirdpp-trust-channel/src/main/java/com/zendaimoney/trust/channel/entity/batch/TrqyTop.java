package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

public class TrqyTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户号
	@CmbAnnotation(index = 1, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;

	// 用户虚拟子账户号
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;

	// 开始时间 20160310
	@CmbAnnotation(index = 3, length = 8, rightFill = true, filler = Constants.BLANK)
	private String startDate;

	// 对账日期 20160310
	@CmbAnnotation(index = 4, length = 8, rightFill = true, filler = Constants.BLANK)
	private String endDate;

	// 总笔数
	@CmbAnnotation(index = 5, length = 6, rightFill = false, filler = Constants.ZERO)
	private int totalCount;

	// 总金额
	@CmbAnnotation(index = 6, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal totalAmount;

	// 备用
	@CmbAnnotation(index = 7, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}

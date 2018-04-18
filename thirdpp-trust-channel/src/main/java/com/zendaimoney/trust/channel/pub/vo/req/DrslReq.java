package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;
import java.util.List;

public class DrslReq extends AttachmentReqVo<List<DrslDetailReq>> {

	private static final long serialVersionUID = -3430992697470901190L;

	// 清算日期 M
	private String settleDate;
	
	// 清分总笔数 M
	private int totalCount;

	// 清分总金额 M
	private BigDecimal totalAmount;
	
	// 实际入账金额 M
	private BigDecimal actualInAmount;

	// 垫付平台用户号 C
	private String advancePlatformUserNo;
	
	// 垫付平台虚拟户 C
	private String advancePlatformVirtualSubNo;
	
	// 备用
	private String spare;

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
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

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

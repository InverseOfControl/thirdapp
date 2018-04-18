package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

public class RcqyTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	// 到账日期
	@CmbAnnotation(index = 1, length = 8, rightFill = true, filler = Constants.BLANK)
	private String receivedDate;

	// 总笔数
	@CmbAnnotation(index = 2, length = 6, rightFill = false, filler = Constants.ZERO)
	private int totalCount;

	// 总金额
	@CmbAnnotation(index = 3, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal totalAmount;

	// 备用
	@CmbAnnotation(index = 4, length = 200, rightFill = true, filler = Constants.BLANK)
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	
}

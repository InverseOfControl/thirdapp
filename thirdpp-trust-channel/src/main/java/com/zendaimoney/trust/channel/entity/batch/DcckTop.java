package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

public class DcckTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 4444965119317786872L;

	// 对账日期 20160310
	@CmbAnnotation(index = 1, length = 8, rightFill = true, filler = Constants.BLANK)
	private String accountDate;

	// 交易标识码
	@CmbAnnotation(index = 2, length = 4, rightFill = true, filler = Constants.BLANK)
	private String tradeCode;

	// 总笔数
	@CmbAnnotation(index = 3, length = 6, rightFill = false, filler = Constants.ZERO)
	private int totalCount;

	// 总金额
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal totalAmount;

	// 备用
	@CmbAnnotation(index = 5, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getTradeCode() {
		return tradeCode;
	}

	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
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

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

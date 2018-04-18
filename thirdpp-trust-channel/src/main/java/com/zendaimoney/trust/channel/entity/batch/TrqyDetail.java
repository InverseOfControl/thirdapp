package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class TrqyDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK)
	private String mTradeFlow;

	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String bTradeFlow;

	// 银行账务日期 8 M
	@CmbAnnotation(index = 3, length = 8, rightFill = true, filler = Constants.BLANK)
	private String bankAccountDate;

	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal amount;

	@CmbAnnotation(index = 5, length = 1, rightFill = true, filler = Constants.BLANK)
	private String tradeDirection;

	// 交易码
	@CmbAnnotation(index = 6, length = 4, rightFill = true, filler = Constants.BLANK)
	private String tradeCode;

	// 交易类别
	@CmbAnnotation(index = 7, length = 2, rightFill = true, filler = Constants.BLANK)
	private String tradeType;

	// 交易摘要
	@CmbAnnotation(index = 8, length = 40, rightFill = true, filler = Constants.BLANK)
	private String note;

	// 关联信息
	@CmbAnnotation(index = 9, length = 30, rightFill = true, filler = Constants.BLANK)
	private String relate;

	// 备用 C
	@CmbAnnotation(index = 10, length = 200, rightFill = true, filler = Constants.BLANK)
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

	public String getBankAccountDate() {
		return bankAccountDate;
	}

	public void setBankAccountDate(String bankAccountDate) {
		this.bankAccountDate = bankAccountDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRelate() {
		return relate;
	}

	public void setRelate(String relate) {
		this.relate = relate;
	}

}

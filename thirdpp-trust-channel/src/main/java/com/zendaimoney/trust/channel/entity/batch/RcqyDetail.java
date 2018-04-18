package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class RcqyDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	// 入账交易流水号
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK)
	private String tradeFlow;

	// 用户号
	@CmbAnnotation(index = 2, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;

	// 用户虚拟子账户号
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;

	// 充值金额
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO)
	private BigDecimal amount;
	
	// 转账卡号
	@CmbAnnotation(index = 5, length = 30, rightFill = true, filler = Constants.BLANK)
	private String transferCardId;

	// 转账户名
	@CmbAnnotation(index = 6, length = 80, rightFill = true, filler = Constants.BLANK, hex = true)
	private String transferCardName;
	
	// 转账银行
	@CmbAnnotation(index = 7, length = 40, rightFill = true, filler = Constants.BLANK)
	private String transferBank;
	
	// 转账备注
	@CmbAnnotation(index = 8, length = 80, rightFill = true, filler = Constants.BLANK)
	private String transferNote;
		
	// 备用 C
	@CmbAnnotation(index = 9, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransferCardId() {
		return transferCardId;
	}

	public void setTransferCardId(String transferCardId) {
		this.transferCardId = transferCardId;
	}

	public String getTransferCardName() {
		return transferCardName;
	}

	public void setTransferCardName(String transferCardName) {
		this.transferCardName = transferCardName;
	}

	public String getTransferBank() {
		return transferBank;
	}

	public void setTransferBank(String transferBank) {
		this.transferBank = transferBank;
	}

	public String getTransferNote() {
		return transferNote;
	}

	public void setTransferNote(String transferNote) {
		this.transferNote = transferNote;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

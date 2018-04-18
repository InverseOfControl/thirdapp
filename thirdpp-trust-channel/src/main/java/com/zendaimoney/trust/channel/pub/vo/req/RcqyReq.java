package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 存管专户入账交易查询(RCQY) 请求对象
 * 
 * @author 00237071
 * 
 */
public class RcqyReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	// 到账日期
	@CmbAnnotation(index = 1, length = 8, rightFill = true, filler = Constants.BLANK)
	private String receivedDate;

	// A：所有；X：虚拟户入账交易；N：非虚拟户入账交易
	@CmbAnnotation(index = 2, length = 1, rightFill = true, filler = Constants.BLANK)
	private String mode;

	// 入账交易流水号
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK)
	private String receivingTradeflow;

	// 备用
	@CmbAnnotation(index = 4, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getReceivingTradeflow() {
		return receivingTradeflow;
	}

	public void setReceivingTradeflow(String receivingTradeflow) {
		this.receivingTradeflow = receivingTradeflow;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

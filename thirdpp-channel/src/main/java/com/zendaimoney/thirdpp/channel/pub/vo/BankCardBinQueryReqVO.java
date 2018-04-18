package com.zendaimoney.thirdpp.channel.pub.vo;

public class BankCardBinQueryReqVO extends BizReqVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 卡号
	 */
	public String cardNo;
	
	/**
	 * 是否需要代扣银行编号
	 */
	public String needOraCardIssuer;
	
	/**
	 * 查询流水号
	 */
	private String queryFlow;
	
	
	
	public String getCardNo() {
		return cardNo;
	}
	
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getNeedOraCardIssuer() {
		return needOraCardIssuer;
	}
	
	public void setNeedOraCardIssuer(String needOraCardIssuer) {
		this.needOraCardIssuer = needOraCardIssuer;
	}
	
	public String getQueryFlow() {
		return queryFlow;
	}
	
	public void setQueryFlow(String queryFlow) {
		this.queryFlow = queryFlow;
	}
}

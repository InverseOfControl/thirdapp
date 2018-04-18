package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;

public class CardBinResponseVo implements Serializable{

	private static final long serialVersionUID = -1627953535220842360L;

	/**
	 *  银行编码 （与总行查询接口返回数据统一）
	 */
	private String bankCode;
	
	/**
	 *  银行名称
	 */
	private String bankName;

	/**
	 *  银行卡号
	 */
	private String bankCardNo;

	/**
	 *  银行卡类型 （1-借记卡，2：贷记卡（信用卡））
	 */
	private String bankCardType;

	/**
	 *  卡bin
	 */
	private String bankCardBin;
	
	/**
	 * 卡长度
	 */
	private int bankCardLen;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getBankCardBin() {
		return bankCardBin;
	}

	public void setBankCardBin(String bankCardBin) {
		this.bankCardBin = bankCardBin;
	}

	public int getBankCardLen() {
		return bankCardLen;
	}

	public void setBankCardLen(int bankCardLen) {
		this.bankCardLen = bankCardLen;
	}
	
}

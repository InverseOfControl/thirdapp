package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;

/**
 * 总行银行响应业务对象
 * 
 * @author 00237071
 *
 */
public class BankResponseVo implements Serializable {

	private static final long serialVersionUID = 6336912954397113769L;

	// 总行编码
	private String bankCode;
	
	// 总行名称
	private String bankName;

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
	
}

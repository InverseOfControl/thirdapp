package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;

public class ThirdPartyPayPlatformSupportBankRespVo implements Serializable {

	private static final long serialVersionUID = -5336545407802481347L;

	// 银行编码
	private String bankCode;

	// 银行名称
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

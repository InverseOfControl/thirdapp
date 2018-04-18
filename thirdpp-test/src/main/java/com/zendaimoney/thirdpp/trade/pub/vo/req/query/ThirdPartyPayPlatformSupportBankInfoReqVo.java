package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

public class ThirdPartyPayPlatformSupportBankInfoReqVo extends BaseQueryRequestVo{

	private static final long serialVersionUID = -7719507256802029774L;

	// 支付平台编码
	private String thirdPartyPayPlatformCode;
	
	// 银行编码
	private String bankCode;

	public String getThirdPartyPayPlatformCode() {
		return thirdPartyPayPlatformCode;
	}

	public void setThirdPartyPayPlatformCode(String thirdPartyPayPlatformCode) {
		this.thirdPartyPayPlatformCode = thirdPartyPayPlatformCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}

package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

public class ThirdPartyPayPlatformSupportBankReqVo extends BaseQueryRequestVo{

	private static final long serialVersionUID = -7719507256802029774L;

	// 支付平台编码
	private String thirdPartyPayPlatformCode;

	public String getThirdPartyPayPlatformCode() {
		return thirdPartyPayPlatformCode;
	}

	public void setThirdPartyPayPlatformCode(String thirdPartyPayPlatformCode) {
		this.thirdPartyPayPlatformCode = thirdPartyPayPlatformCode;
	}
	
}

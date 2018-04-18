package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;

public class ThirdPartyPayPlatformResponseVo implements Serializable {

	private static final long serialVersionUID = 8385286397586114840L;

	// 支付平台编码
	private String thirdPartyPayPlatformCode;
	
	// 支付平台名称
	private String thirdPartyPayPlatformName;

	public String getThirdPartyPayPlatformCode() {
		return thirdPartyPayPlatformCode;
	}

	public void setThirdPartyPayPlatformCode(String thirdPartyPayPlatformCode) {
		this.thirdPartyPayPlatformCode = thirdPartyPayPlatformCode;
	}

	public String getThirdPartyPayPlatformName() {
		return thirdPartyPayPlatformName;
	}

	public void setThirdPartyPayPlatformName(String thirdPartyPayPlatformName) {
		this.thirdPartyPayPlatformName = thirdPartyPayPlatformName;
	}

}

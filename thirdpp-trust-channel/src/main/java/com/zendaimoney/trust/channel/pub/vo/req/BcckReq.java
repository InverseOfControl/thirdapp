package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

public class BcckReq extends TrustBizReqVo {

	private static final long serialVersionUID = -6291997675608627391L;

	// 对账日期 20160310
	@CmbAnnotation(index = 1, length = 8, rightFill = true, filler = Constants.BLANK)
	private String balanceDate;

	// 备用
	@CmbAnnotation(index = 2, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

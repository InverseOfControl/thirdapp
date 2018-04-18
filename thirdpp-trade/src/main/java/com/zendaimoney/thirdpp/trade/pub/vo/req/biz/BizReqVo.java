package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

import java.io.Serializable;

public class BizReqVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8665840443262306710L;


	/**
	 * 第三方通道编码
	 */
	private String paySysNo;
	

	// 备用1
	private String spare1 = "0";

	// 备用2
	private String spare2;

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}


	public String getSpare1() {
		return spare1;
	}

	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}

	public String getSpare2() {
		return spare2;
	}

	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}

}

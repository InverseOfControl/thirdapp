package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

public class BankCardBinQueryReqVo extends BaseQueryRequestVo {

	private static final long serialVersionUID = 7693272251786667780L;

	// 信息类别编码
	private String infoCategoryCode;
	
	// 银行卡号
	private String bankCardNo;

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}


	
}

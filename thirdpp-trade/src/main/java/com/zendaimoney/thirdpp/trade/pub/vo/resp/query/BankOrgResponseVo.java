package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;

/**
 * 支行银行响应业务对象
 * 
 * @author 00237071
 * 
 */
public class BankOrgResponseVo implements Serializable {

	private static final long serialVersionUID = 6336912954397113769L;

	// 支行联行号
	private String bankLineNo;

	// 支行名称
	private String bankOrgName;

	public String getBankLineNo() {
		return bankLineNo;
	}

	public void setBankLineNo(String bankLineNo) {
		this.bankLineNo = bankLineNo;
	}

	public String getBankOrgName() {
		return bankOrgName;
	}

	public void setBankOrgName(String bankOrgName) {
		this.bankOrgName = bankOrgName;
	}

}

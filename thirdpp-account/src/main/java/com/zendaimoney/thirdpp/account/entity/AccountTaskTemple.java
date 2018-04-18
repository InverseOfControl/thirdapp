package com.zendaimoney.thirdpp.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountTaskTemple implements Serializable {

	private static final long serialVersionUID = -5333589425631080081L;

	 //金额
    private BigDecimal amount;
    
    //是否拆单(1:拆单、0不拆单)该字段由转发程序判断进行修改。
    private int isSeparate;

    //拆单数
    private int separateCount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getIsSeparate() {
		return isSeparate;
	}

	public void setIsSeparate(int isSeparate) {
		this.isSeparate = isSeparate;
	}

	public int getSeparateCount() {
		return separateCount;
	}

	public void setSeparateCount(int separateCount) {
		this.separateCount = separateCount;
	}
    
}

package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

public class BcckTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 4444965119317786872L;

	// 对账日期 20160310
	@CmbAnnotation(index = 1, length = 8, rightFill = true, filler = Constants.BLANK)
	private String balanceDate;

	// 总笔数
	@CmbAnnotation(index = 2, length = 6, rightFill = false, filler = Constants.ZERO)
	private int totalCount;

	// 备用
	@CmbAnnotation(index = 3, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(String balanceDate) {
		this.balanceDate = balanceDate;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

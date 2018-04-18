package com.zendaimoney.trust.channel.pub.vo.resp;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 提现请求(WTDR)响应对象
 * @author mencius
 *
 */
public class WtdrResp extends CommonResp {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 银行账务日期
	 */
	@CmbAnnotation(index = 2, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankAccountDate;
	
	/**
	 * 平台垫付金额	15	C	从平台虚拟子账户冻结
	 */
	@CmbAnnotation(index = 3, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal advanceAmount;
	
	/**
	 * 银行冻结流水	30	C
	 */
	@CmbAnnotation(index = 4, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String freezeFlow;
	
	/**
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 5, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String msg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 6, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getBankAccountDate() {
		return bankAccountDate;
	}

	public void setBankAccountDate(String bankAccountDate) {
		this.bankAccountDate = bankAccountDate;
	}

	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getFreezeFlow() {
		return freezeFlow;
	}

	public void setFreezeFlow(String freezeFlow) {
		this.freezeFlow = freezeFlow;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	
}

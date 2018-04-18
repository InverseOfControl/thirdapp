package com.zendaimoney.trust.channel.pub.vo.resp;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;
/**
 * 冻结返回
 * @author 00233197
 *
 */
public class TranResp extends CommonResp{

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
	 * 交易描述信息
	 */
	@CmbAnnotation(index = 3, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String msg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 4, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
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

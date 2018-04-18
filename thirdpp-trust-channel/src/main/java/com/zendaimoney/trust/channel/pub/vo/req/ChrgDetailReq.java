package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 批量三方充值文件明细
 * @author mencius
 *
 */
public class ChrgDetailReq implements Serializable{

	private static final long serialVersionUID = 1L;

	
	/**
	 * 1	商户交易流水	30	M
	 */
	@CmbAnnotation(index = 1, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String merchantFlow;
	
	/**
	 * 2	银行交易流水	30	C	银行返回填写
	 */
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankFlow;
	
	/**
	 * 3	用户号	20	M	客户号，唯一标识。
	 */
	@CmbAnnotation(index = 3, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String userNo;
	
	/**
	 * 4	用户虚拟子账户号	30	M
	 */
	@CmbAnnotation(index = 4, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String virtualSubNo;
	
	/**
	 * 5	三方支付公司标识	30	M
	 */
	@CmbAnnotation(index = 5, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String thirdFlag;
	
	/**
	 * 6	三方支付公司订单号	30	M
	 */
	@CmbAnnotation(index = 6, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String thirdOrderNo;
	
	/**
	 * 7	金额	15	M	以分为单位，左补零。例如：000000000001234表示12.34元
	 */
	@CmbAnnotation(index = 7, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amount;
	
	/**
	 * 8	交易返回码	7	C
	 */
	@CmbAnnotation(index = 8, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 9	银行账务日期	8	C
	 */
	@CmbAnnotation(index = 9, length = 8, rightFill = true, filler = Constants.BLANK, hex = false)
	private String bankAccountDate;
	
	/**
	 * 10	交易返回描述信息	80	C
	 */
	@CmbAnnotation(index = 10, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retMsg;
	
	/**
	 * 11	备用	200	C
	 */
	@CmbAnnotation(index = 11, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getMerchantFlow() {
		return merchantFlow;
	}

	public void setMerchantFlow(String merchantFlow) {
		this.merchantFlow = merchantFlow;
	}

	public String getBankFlow() {
		return bankFlow;
	}

	public void setBankFlow(String bankFlow) {
		this.bankFlow = bankFlow;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getVirtualSubNo() {
		return virtualSubNo;
	}

	public void setVirtualSubNo(String virtualSubNo) {
		this.virtualSubNo = virtualSubNo;
	}

	public String getThirdFlag() {
		return thirdFlag;
	}

	public void setThirdFlag(String thirdFlag) {
		this.thirdFlag = thirdFlag;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

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

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	

}

package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 解冻首行记录
 * @author 00233197
 *
 */
public class UnfrTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 解冻总笔数	6	M
	 */
	@CmbAnnotation(index = 1, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int unfrSum;
	
	/**
	 * 解冻总金额	15	M
	 */
	@CmbAnnotation(index = 2, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amountSum;
	
	/**
	 * 解冻成功总笔数	6	C
	 */
	@CmbAnnotation(index = 3, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int unfrSucSum;
	
	/**
	 * 解冻成功总金额	15	C
	 */
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amountSucSum;
	
	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 5, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 交易返回描述信息
	 */
	@CmbAnnotation(index = 6, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retMsg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 7, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	/**
	 * 构造器
	 * @param rechargeSum
	 * @param amountSum
	 */
	public UnfrTop(int unfrSum, BigDecimal amountSum) {
		this.unfrSum = unfrSum;
		this.amountSum = amountSum;
	}

	public BigDecimal getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
	}

	public BigDecimal getAmountSucSum() {
		return amountSucSum;
	}

	public void setAmountSucSum(BigDecimal amountSucSum) {
		this.amountSucSum = amountSucSum;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
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

	public int getUnfrSum() {
		return unfrSum;
	}

	public void setUnfrSum(int unfrSum) {
		this.unfrSum = unfrSum;
	}

	public int getUnfrSucSum() {
		return unfrSucSum;
	}

	public void setUnfrSucSum(int unfrSucSum) {
		this.unfrSucSum = unfrSucSum;
	}
	
	
}

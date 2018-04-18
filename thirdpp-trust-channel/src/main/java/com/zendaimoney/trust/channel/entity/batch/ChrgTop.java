package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 批量三方充值文件首行
 * @author mencius
 *
 */
public class ChrgTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 充值总笔数	6	M
	 */
	@CmbAnnotation(index = 1, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int rechargeSum;
	
	/**
	 * 充值总金额	15	M
	 */
	@CmbAnnotation(index = 2, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amountSum;
	
	/**
	 * 充值成功总笔数	6	C
	 */
	@CmbAnnotation(index = 3, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int rechargeSucSum;
	
	/**
	 * 充值成功总金额	15	C
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
	public ChrgTop(int rechargeSum, BigDecimal amountSum) {
		this.rechargeSum = rechargeSum;
		this.amountSum = amountSum;
	}
	public int getRechargeSum() {
		return rechargeSum;
	}

	public void setRechargeSum(int rechargeSum) {
		this.rechargeSum = rechargeSum;
	}

	public BigDecimal getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
	}

	public int getRechargeSucSum() {
		return rechargeSucSum;
	}

	public void setRechargeSucSum(int rechargeSucSum) {
		this.rechargeSucSum = rechargeSucSum;
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
	
	
}

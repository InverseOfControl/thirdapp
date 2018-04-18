package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;
import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 冻结首行记录
 * @author 00233197
 *
 */
public class FrozTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 冻结总笔数	6	M
	 */
	@CmbAnnotation(index = 1, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int frozSum;
	
	/**
	 * 冻结总金额	15	M
	 */
	@CmbAnnotation(index = 2, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal amountSum;
	
	/**
	 * 冻结成功总笔数	6	C
	 */
	@CmbAnnotation(index = 3, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int frozSucSum;
	
	/**
	 * 冻结成功总金额	15	C
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
	public FrozTop(int frozSum, BigDecimal amountSum) {
		this.frozSum = frozSum;
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

	public int getFrozSum() {
		return frozSum;
	}

	public void setFrozSum(int frozSum) {
		this.frozSum = frozSum;
	}

	public int getFrozSucSum() {
		return frozSucSum;
	}

	public void setFrozSucSum(int frozSucSum) {
		this.frozSucSum = frozSucSum;
	}
	
	
}

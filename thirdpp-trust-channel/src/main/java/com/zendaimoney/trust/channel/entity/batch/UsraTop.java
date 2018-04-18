package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 用户开户批量文件首行
 * @author mencius
 *
 */
public class UsraTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 开户总笔数
	 */
	@CmbAnnotation(index = 1, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int sum;
	
	/**
	 * 开户成功总笔数
	 */
	@CmbAnnotation(index = 2, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int sucSum;
	
	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 3, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 交易返回描述信息
	 */
	@CmbAnnotation(index = 4, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retMsg;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 5, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;
	
	public UsraTop(int sum) {
		this.sum = sum;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getSucSum() {
		return sucSum;
	}

	public void setSucSum(int sucSum) {
		this.sucSum = sucSum;
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

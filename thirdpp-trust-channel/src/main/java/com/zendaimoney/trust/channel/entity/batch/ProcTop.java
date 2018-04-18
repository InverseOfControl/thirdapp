package com.zendaimoney.trust.channel.entity.batch;

import java.io.Serializable;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.entity.cmb.FileTop;
import com.zendaimoney.trust.channel.util.Constants;

public class ProcTop extends FileTop implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 1	关闭总笔数	6	M	校验文件正确性字段
	 */
	@CmbAnnotation(index = 1, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int closeSum;
	
	/**
	 * 2	关闭成功总笔数	6	C	校验文件正确性字段
	 */
	@CmbAnnotation(index = 2, length = 6, rightFill = false, filler = Constants.ZERO, hex = false)
	private int closeSucSum;
	
	/**
	 * 3	交易返回码	7	C	银行返回填写CMBMB99
	 */
	@CmbAnnotation(index = 3, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 4	交易返回描述信息	80	C	银行返回填写
	 */
	@CmbAnnotation(index = 4, length = 80, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retMsg;
	
	/**
	 * 5	备用	200	C
	 */
	@CmbAnnotation(index = 5, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;
	
	
	/**
	 * 默认构造器
	 * @param closeSum
	 */
	public ProcTop(int closeSum) {
		this.closeSum = closeSum;
	}

	public int getCloseSum() {
		return closeSum;
	}

	public void setCloseSum(int closeSum) {
		this.closeSum = closeSum;
	}

	public int getCloseSucSum() {
		return closeSucSum;
	}

	public void setCloseSucSum(int closeSucSum) {
		this.closeSucSum = closeSucSum;
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

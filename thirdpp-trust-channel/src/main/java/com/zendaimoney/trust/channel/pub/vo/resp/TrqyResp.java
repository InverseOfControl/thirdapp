package com.zendaimoney.trust.channel.pub.vo.resp;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 虚拟户交易查询(TRQY) 响应对象
 * 
 * @author 00237071
 * 
 */
public class TrqyResp extends CommonResp {

	private static final long serialVersionUID = 1L;

	// 交易返回码
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK)
	private String retCode;

	// 返回记录数 前补0，例如000013
	@CmbAnnotation(index = 2, length = 6, rightFill = false, filler = Constants.ZERO)
	private int count;

	// 是否有续传  Y表示仍有续传；N表示没有续传
	@CmbAnnotation(index = 3, length = 1, rightFill = true, filler = Constants.BLANK)
	private String isContinue;

	// 返回文件名 
	@CmbAnnotation(index = 4, length = 40, rightFill = true, filler = Constants.BLANK)
	private String fileName;

	// 原交易描述信息 
	@CmbAnnotation(index = 5, length = 160, rightFill = true, filler = Constants.BLANK, hex = true)
	private String originalTradeDesc;

	// 备用 C
	@CmbAnnotation(index = 6, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getIsContinue() {
		return isContinue;
	}

	public void setIsContinue(String isContinue) {
		this.isContinue = isContinue;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalTradeDesc() {
		return originalTradeDesc;
	}

	public void setOriginalTradeDesc(String originalTradeDesc) {
		this.originalTradeDesc = originalTradeDesc;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

}

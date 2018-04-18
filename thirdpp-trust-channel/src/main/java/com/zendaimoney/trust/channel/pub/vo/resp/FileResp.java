package com.zendaimoney.trust.channel.pub.vo.resp;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 
 * @author user
 *
 */
public class FileResp extends CommonResp {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK, hex = false)
	private String retCode;
	
	/**
	 * 回盘文件名(银行返回文件名)
	 */
	@CmbAnnotation(index = 2, length = 40, rightFill = true, filler = Constants.BLANK, hex = false)
	private String respFilename;
	
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

	public String getRespFilename() {
		return respFilename;
	}

	public void setRespFilename(String respFilename) {
		this.respFilename = respFilename;
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

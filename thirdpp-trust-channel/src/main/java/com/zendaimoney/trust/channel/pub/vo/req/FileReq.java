/**
 * 2015年12月3日
 */
package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;


/**
 * 文件请求报文对象
 * @author mencius
 */
public class FileReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	@CmbAnnotation(index = 1, length = 40, rightFill = true, filler = Constants.BLANK, hex = false)
	private String reqFileName;
	
	@CmbAnnotation(index = 2, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
	private String spare;

	public String getReqFileName() {
		return reqFileName;
	}

	public void setReqFileName(String reqFileName) {
		this.reqFileName = reqFileName;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
}

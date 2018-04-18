package com.zendaimoney.trust.channel.pub.vo.resp;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 每日交易汇总对账(SCCK) 响应对象
 * @author 00237071
 *
 */
public class ScckResp extends CommonResp {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易返回码
	 */
	@CmbAnnotation(index = 1, length = 7, rightFill = true, filler = Constants.BLANK)
	private String retCode;
	
	/**
	 * 汇总交易信息
	 */
	@CmbAnnotation(index = 2, length = 1000, rightFill = true, filler = Constants.BLANK)
	private String summaryTradeInfo;
	
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getSummaryTradeInfo() {
		return summaryTradeInfo;
	}

	public void setSummaryTradeInfo(String summaryTradeInfo) {
		this.summaryTradeInfo = summaryTradeInfo;
	}
}

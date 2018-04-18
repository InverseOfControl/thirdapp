package com.zendaimoney.trust.channel.pub.vo.req;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 虚拟户交易查询(TRQY) 请求对象
 * @author 00237071
 *
 */
public class TrqyReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	// 用户号
	@CmbAnnotation(index = 1, length = 20, rightFill = true, filler = Constants.BLANK)
	private String userNo;
		
	// 用户虚拟子账户号
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK)
	private String virtualSubNo;
	
	// 开始时间
	@CmbAnnotation(index = 3, length = 8, rightFill = true, filler = Constants.BLANK)
	private String startDate;
	
	// 结束时间
	@CmbAnnotation(index = 4, length = 8, rightFill = true, filler = Constants.BLANK)
	private String endDate;
	
	// 银行交易流水号
	@CmbAnnotation(index = 5, length = 30, rightFill = true, filler = Constants.BLANK)
	private String bTradeFlow;

	// 备用
	@CmbAnnotation(index = 6, length = 200, rightFill = true, filler = Constants.BLANK)
	private String spare;

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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getbTradeFlow() {
		return bTradeFlow;
	}

	public void setbTradeFlow(String bTradeFlow) {
		this.bTradeFlow = bTradeFlow;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	
}

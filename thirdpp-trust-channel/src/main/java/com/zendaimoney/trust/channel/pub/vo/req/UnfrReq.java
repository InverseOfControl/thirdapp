package com.zendaimoney.trust.channel.pub.vo.req;

import java.math.BigDecimal;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.util.Constants;

/**
 * 解冻请求
 * @author 00233197
 *
 */
public class UnfrReq extends TrustBizReqVo {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户号
	 */
	@CmbAnnotation(index = 1, length = 20, rightFill = true, filler = Constants.BLANK, hex = false)
	private String userNo;
	
	
	/**
	 * 用户虚拟子账户号
	 */
	@CmbAnnotation(index = 2, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String virtualSubNo;
	
	/**
	 * 原商户冻结流水
	 */
	@CmbAnnotation(index = 3, length = 30, rightFill = true, filler = Constants.BLANK, hex = false)
	private String frozFlow;
	
	
	/**
	 * 金额
	 */
	@CmbAnnotation(index = 4, length = 15, rightFill = false, filler = Constants.ZERO, hex = false)
	private BigDecimal unfrAmount;
	
	/**
	 * 客户摘要
	 */
	@CmbAnnotation(index = 5, length = 80, rightFill = true, filler = Constants.BLANK, hex = true)
	private String summary;
	
	/**
	 * 备用
	 */
	@CmbAnnotation(index = 6, length = 200, rightFill = true, filler = Constants.BLANK, hex = false)
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


	public String getFrozFlow() {
		return frozFlow;
	}

	public void setFrozFlow(String frozFlow) {
		this.frozFlow = frozFlow;
	}

	public BigDecimal getUnfrAmount() {
		return unfrAmount;
	}

	public void setUnfrAmount(BigDecimal unfrAmount) {
		this.unfrAmount = unfrAmount;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	
}

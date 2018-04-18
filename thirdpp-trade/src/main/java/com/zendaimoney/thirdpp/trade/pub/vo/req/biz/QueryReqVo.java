package com.zendaimoney.thirdpp.trade.pub.vo.req.biz;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.zendaimoney.thirdpp.trade.validate.InsertCheck;


public class QueryReqVo extends RequestVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//业务流水号
	private String bizFlow;
	
	//创建人
	private String creater;
	


	public String getBizFlow() {
		return bizFlow;
	}

	public void setBizFlow(String bizFlow) {
		this.bizFlow = bizFlow;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}


}

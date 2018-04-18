package com.zendaimoney.thirdpp.trade.pub.vo.req.query;

import java.io.Serializable;

/**
 * 业务系统查询请求基类
 * @author 00237071
 *
 */
public class BaseQueryRequestVo implements Serializable {

	private static final long serialVersionUID = -7365988690196270188L;
	
	// 业务系统编码
	private String bizSysNo;

	public String getBizSysNo() {
		return bizSysNo;
	}

	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

}

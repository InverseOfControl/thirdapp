package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;

/**
 * 银行区域的响应业务对象
 * 
 * @author 00237071
 * 
 */
public class BankAreaResponseVo implements Serializable {

	private static final long serialVersionUID = 7207010280771314478L;

	// 区域编码
	private String areaCode;

	// 区域名称
	private String areaName;

	// 父节点编码
	private String parentId;

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}

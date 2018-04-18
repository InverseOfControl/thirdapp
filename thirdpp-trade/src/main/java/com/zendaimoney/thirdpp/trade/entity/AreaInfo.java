package com.zendaimoney.thirdpp.trade.entity;

public class AreaInfo {

	// 主键
	private Integer id;
	
	// 区域编码
	private String areaCode;
	
	// 区域名称
	private String areaName;
	
	// 父节点编码
	private String parentId;

	private static final String PARENT_ID = "0";
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public boolean isParent(){
		boolean b = false;
		if (PARENT_ID.equals(getParentId())) {
			b = true;
		}
		return b;
	}
}

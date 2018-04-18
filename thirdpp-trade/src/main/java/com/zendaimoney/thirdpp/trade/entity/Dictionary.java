package com.zendaimoney.thirdpp.trade.entity;

import java.io.Serializable;

public class Dictionary implements Serializable {

	private static final long serialVersionUID = -8270722796345353125L;

	// 3 = 接入支付平台
	public static final String DIC_TYPE_PAY_PLATFORM = "3";
	
	// 主键
	private long id;
	// 编码
	private String dicCode;
	// 名称
	private String dicName;
	// 类型(-1:父节点 0:币种1:银行卡类型2:交易状态3接入支付平台4业务类型5证件类型6商户号类型7查询模块名称8通知模块名称)
	private String dicType;
	// 父类ID，对应主键，0没有父类
	private long parentId;
	// 描述
	private String dicDesc;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getDicType() {
		return dicType;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getDicDesc() {
		return dicDesc;
	}

	public void setDicDesc(String dicDesc) {
		this.dicDesc = dicDesc;
	}

}

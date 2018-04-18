package com.zendaimoney.thirdpp.account.action.account;

import java.lang.reflect.Field;

public class AccountActionTarget {

	private Class<? extends AccountAction> actionClazz;

	// 类属性集合
	private Field[] fields;
	// 业务类型
	private String bizType;

	public AccountActionTarget(Class<? extends AccountAction> actionClazz,
			String fileSuffix) {
		this.actionClazz = actionClazz;
		this.bizType = fileSuffix;
		setFields();
	}

	private void setFields() {
		this.fields = actionClazz.getDeclaredFields();
		for (Field field : fields)
			field.setAccessible(true);
	}

	public Class<? extends AccountAction> getActionClazz() {
		return actionClazz;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public Field[] getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bizType == null) ? 0 : bizType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountActionTarget other = (AccountActionTarget) obj;
		if (bizType != other.bizType)
			return false;
		return true;
	}


}

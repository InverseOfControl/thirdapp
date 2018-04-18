package com.zendaimoney.thirdpp.account.action.download;

import java.lang.reflect.Field;

public class DownloadActionTarget {

	private Class<? extends DownloadAction> actionClazz;
	
	//类属性集合
	private Field[] fields;

	//获取方式
	private String fetchMethod;


	public DownloadActionTarget(Class<? extends DownloadAction> actionClazz,String fetchMethod) {
		this.actionClazz = actionClazz;
		this.fetchMethod = fetchMethod;
		setFields();
	}

	private void setFields() {
		this.fields = actionClazz.getDeclaredFields();
		for (Field field : fields)
			field.setAccessible(true);
	}

	public Class<? extends DownloadAction> getActionClazz() {
		return actionClazz;
	}

	public String getFetchMethod() {
		return fetchMethod;
	}

	public void setFetchMethod(String fetchMethod) {
		this.fetchMethod = fetchMethod;
	}

	public Field[] getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fetchMethod == null) ? 0 : fetchMethod.hashCode());
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
		DownloadActionTarget other = (DownloadActionTarget) obj;
		if (fetchMethod != other.fetchMethod)
			return false;
		return true;
	}

}

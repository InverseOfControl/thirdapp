package com.zendaimoney.thirdpp.account.action.parse;

import java.lang.reflect.Field;

public class FileParseActionTarget {

	private Class<? extends FileParseAction> actionClazz;

	// 类属性集合
	private Field[] fields;

	// 文件后缀
	private String fileSuffix;

	public FileParseActionTarget(Class<? extends FileParseAction> actionClazz,
			String fileSuffix) {
		this.actionClazz = actionClazz;
		this.fileSuffix = fileSuffix;
		setFields();
	}

	private void setFields() {
		this.fields = actionClazz.getDeclaredFields();
		for (Field field : fields)
			field.setAccessible(true);
	}

	public Class<? extends FileParseAction> getActionClazz() {
		return actionClazz;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public Field[] getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileSuffix == null) ? 0 : fileSuffix.hashCode());
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
		FileParseActionTarget other = (FileParseActionTarget) obj;
		if (fileSuffix != other.fileSuffix)
			return false;
		return true;
	}

}

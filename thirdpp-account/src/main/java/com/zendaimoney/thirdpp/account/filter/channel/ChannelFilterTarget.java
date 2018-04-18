package com.zendaimoney.thirdpp.account.filter.channel;

import java.lang.reflect.Field;

public class ChannelFilterTarget {

	private Class<? extends ChannelFilter> filterClass;

	// 类属性集合
	private Field[] fields;

	// 过滤顺序
	private Integer filterStep;

	public ChannelFilterTarget(Class<? extends ChannelFilter> actionClazz,
			int filterStep) {
		this.filterClass = actionClazz;
		this.filterStep = filterStep;
		setFields();
	}
	
	private void setFields() {
		this.fields = filterClass.getDeclaredFields();
		for (Field field : fields)
			field.setAccessible(true);
	}

	public Class<? extends ChannelFilter> getActionClazz() {
		return filterClass;
	}

	public int getFileSuffix() {
		return filterStep;
	}

	public void setFileSuffix(int filterStep) {
		this.filterStep = filterStep;
	}

	public Field[] getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((filterStep == null) ? 0 : filterStep.hashCode());
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
		ChannelFilterTarget other = (ChannelFilterTarget) obj;
		if (filterStep != other.filterStep)
			return false;
		return true;
	}

}

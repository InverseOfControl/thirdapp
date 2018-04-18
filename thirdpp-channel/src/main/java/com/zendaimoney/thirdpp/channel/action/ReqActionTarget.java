package com.zendaimoney.thirdpp.channel.action;

import java.lang.reflect.Field;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;

public class ReqActionTarget {

	/**
	 * 
	 */
	private Class<? extends Action> actionClazz;

	// 类属性集合
	private Field[] fields;

	// 业务类型
	private BizType bizType;
	// 通道编码
	private ThirdType thirdType;

	// 通道类别
	private ChannelCategory channelCategory;

	public ReqActionTarget(Class<? extends Action> actionClazz,
			BizType bizType, ThirdType thirdType,
			ChannelCategory channelCategory) {
		this.actionClazz = actionClazz;
		this.bizType = bizType;
		this.thirdType = thirdType;
		this.channelCategory = channelCategory;
		setFields();
	}

	private void setFields() {
		this.fields = actionClazz.getDeclaredFields();
		for (Field field : fields)
			field.setAccessible(true);
	}

	public Class<? extends Action> getActionClazz() {
		return actionClazz;
	}

	public BizType getBizType() {
		return bizType;
	}

	public ThirdType getThirdType() {
		return thirdType;
	}
	
	

	public ChannelCategory getChannelCategory() {
		return channelCategory;
	}

	public Field[] getFields() {
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((bizType == null) ? 0 : bizType.hashCode())
				+ ((thirdType == null) ? 0 : thirdType.hashCode())
						+ ((channelCategory == null) ? 0 : channelCategory
								.hashCode());
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
		ReqActionTarget other = (ReqActionTarget) obj;
		if (bizType != other.bizType || thirdType != other.thirdType || channelCategory != other.channelCategory)
			return false;
		return true;
	}

}

package com.zendaimoney.trust.channel.action;

import java.lang.reflect.Field;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;

/**
 * 请求Action收集对象
 * @author mencius
 *
 */
public class TradeActionTarget {

	/**
	 * 
	 */
	private Class<? extends TradeActionAbstract> actionClazz;

	// 类属性集合
	private Field[] fields;
	
	// 通道编码
	private ThirdType thirdType;

	// 业务类型
	private TrustBizType cmbBizType;

	// 通道类别
	private TrustCategory cmbCategory;

	public TradeActionTarget(Class<? extends TradeActionAbstract> actionClazz,
			ThirdType thirdType, TrustBizType cmbBizType, TrustCategory cmbCategory) {
		this.actionClazz = actionClazz;
		this.thirdType = thirdType;
		this.cmbBizType = cmbBizType;
		this.cmbCategory = cmbCategory;
		setFields();
	}

	private void setFields() {
		this.fields = actionClazz.getDeclaredFields();
		for (Field field : fields)
			field.setAccessible(true);
	}

	public Class<? extends TradeActionAbstract> getActionClazz() {
		return actionClazz;
	}
	
	public ThirdType getThirdType() {
		return thirdType;
	}

	public TrustBizType getCmbBizType() {
		return cmbBizType;
	}
	
	public TrustCategory getCmbCategory() {
		return cmbCategory;
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
				+ ((thirdType == null) ? 0 : thirdType.hashCode())
				+ ((cmbBizType == null) ? 0 : cmbBizType.hashCode())
				+ ((cmbCategory == null) ? 0 : cmbCategory.hashCode());
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
		TradeActionTarget other = (TradeActionTarget) obj;
		if (thirdType != other.thirdType || cmbBizType != other.cmbBizType || cmbCategory != other.cmbCategory)
			return false;
		return true;
	}

}

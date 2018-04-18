package com.zendaimoney.trust.channel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ReqActionAnnotation {
	
	/**
	 * 第三方通道编码
	 * @return
	 */
	public ThirdType thirdType();
	
	/**
	 * 招商银行业务类型
	 * @return
	 */
	public TrustBizType[] cmbBizType();
	
	
	/**
	 * 通道类别
	 * @return
	 */
	public TrustCategory cmbCategory();
}

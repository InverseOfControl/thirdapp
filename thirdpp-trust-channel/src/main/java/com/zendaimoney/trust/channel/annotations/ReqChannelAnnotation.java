package com.zendaimoney.trust.channel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;

/**
 * 自定义注解：第三方通道(编码 、请求类别)
 * @author mencius
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ReqChannelAnnotation {

	/**
	 * 第三方通道编码
	 * @return
	 */
	public ThirdType thirdType();
	
	/**
	 * 通道请求类别
	 */
	public TrustCategory trustCategory();
}

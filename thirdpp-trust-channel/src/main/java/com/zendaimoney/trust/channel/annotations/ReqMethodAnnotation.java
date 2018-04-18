package com.zendaimoney.trust.channel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zendaimoney.trust.channel.pub.enums.TrustBizType;

/**
 * 自定义注解：业务名称
 * @author mencius
 * 
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqMethodAnnotation {

	/**
	 * 业务名称
	 * @return
	 */
	TrustBizType trustBizType();
	
}

package com.zendaimoney.thirdpp.account.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface AccountAnnotation {
	/**
	 * 业务类型
	 * @return
	 */
	public String bizType();
	
}

package com.zendaimoney.trust.channel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * @author mencius
 * 
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface CmbAnnotation {

	/**
	 * 参数字段位置索引
	 * @return
	 */
	int index();
	
	/**
	 * 参数值域固定长度
	 * @return
	 */
	int length();
	
	/**
	 * 是否右补位
	 * @return true/false
	 */
	boolean rightFill();
	
	/**
	 * 补位填充物
	 * @return 空格 或 0
	 */
	String filler();
	
	/**
	 * 是否转换16进制
	 * @return hex
	 */
	boolean hex() default false;
}

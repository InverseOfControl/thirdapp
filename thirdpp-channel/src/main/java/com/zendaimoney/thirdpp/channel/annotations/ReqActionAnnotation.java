package com.zendaimoney.thirdpp.channel.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import com.zendaimoney.thirdpp.common.enums.ThirdType;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface ReqActionAnnotation {
	/**
	 * 业务类型
	 * @return
	 */
	public BizType bizType();
	
	
	/**
	 * 第三方通道编码
	 * @return
	 */
	public ThirdType thirdType();

	/**
	 * 通道类别
	 * @return
	 */
	public ChannelCategory channelCategory();
}

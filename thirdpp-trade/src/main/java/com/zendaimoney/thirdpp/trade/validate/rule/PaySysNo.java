package com.zendaimoney.thirdpp.trade.validate.rule;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = { PaySysNoValidator.class })
@Documented
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PaySysNo {
	String message() default "输入正确的第三方支付通道编码！";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
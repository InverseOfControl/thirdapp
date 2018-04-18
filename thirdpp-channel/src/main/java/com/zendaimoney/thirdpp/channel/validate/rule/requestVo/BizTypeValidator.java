package com.zendaimoney.thirdpp.channel.validate.rule.requestVo;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BizTypeValidator implements
		ConstraintValidator<BizType, String> {
	private final String[] ALL_VALUES = { "1","2","3"};

	public void initialize(BizType bizType) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

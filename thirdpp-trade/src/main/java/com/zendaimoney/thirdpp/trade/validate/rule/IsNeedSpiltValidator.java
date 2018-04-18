package com.zendaimoney.thirdpp.trade.validate.rule;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsNeedSpiltValidator implements
		ConstraintValidator<IsNeedSpilt, Integer> {
	private final Integer[] ALL_VALUES = {0,1};

	public void initialize(IsNeedSpilt isNeedSpilt) {
	}

	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

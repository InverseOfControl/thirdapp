package com.zendaimoney.thirdpp.trade.validate.rule;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IDTypeValidator implements
		ConstraintValidator<IDType, String> {
	private final String[] ALL_VALUES = { "0","1","2","3","4","5","6","7","8","9","X","Y","Z"};

	public void initialize(IDType idType) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

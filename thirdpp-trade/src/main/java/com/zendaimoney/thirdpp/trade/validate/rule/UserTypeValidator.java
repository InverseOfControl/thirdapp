package com.zendaimoney.thirdpp.trade.validate.rule;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserTypeValidator implements
		ConstraintValidator<UserType, String> {
	private final String[] ALL_VALUES = { "P","C"};

	public void initialize(UserType idType) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

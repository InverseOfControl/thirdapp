package com.zdmoney.manager.Validate.rule;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BankCardTypeValidator implements
		ConstraintValidator<BankCardType, String> {
	private final String[] ALL_VALUES = { "1","2"};

	public void initialize(BankCardType bankCardType) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

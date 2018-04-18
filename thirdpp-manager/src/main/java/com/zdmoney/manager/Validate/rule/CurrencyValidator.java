package com.zdmoney.manager.Validate.rule;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements
		ConstraintValidator<Currency, String> {
	private final String[] ALL_VALUES = { "0"};

	public void initialize(Currency currency) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

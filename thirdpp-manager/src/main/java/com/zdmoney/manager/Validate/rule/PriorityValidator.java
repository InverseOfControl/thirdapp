package com.zdmoney.manager.Validate.rule;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriorityValidator implements
		ConstraintValidator<Priority, Integer> {
	private final Integer[] ALL_VALUES = {0,1,2,3};

	public void initialize(Priority priority) {
	}

	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null || Arrays.asList(ALL_VALUES).contains(value))
			return true;
		return false;
	}
}

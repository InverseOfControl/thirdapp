package com.zendaimoney.thirdpp.trade.validate.rule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.zendaimoney.thirdpp.common.enums.ThirdType;

public class PaySysNoValidator implements
		ConstraintValidator<PaySysNo, String> {

	public void initialize(PaySysNo paySysNo) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {
		for (ThirdType thirdType : ThirdType.values()) {
			if (thirdType.getCode().equals(value))
				return true;
		}
		return false;
	}
}

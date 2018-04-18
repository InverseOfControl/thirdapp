package com.zdmoney.manager.Validate.rule;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyValidator implements
		ConstraintValidator<Money,BigDecimal> {
	public static String RGX_MONEY = "^[0-9]{1,15}+(.[0-9]{0,2})?$";

	public void initialize(Money money) {
	}

	public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		if(value.compareTo(BigDecimal.ZERO) <= 0)
			return false;
		return isMoney(value.toString());
	}

	/**
	 * 判断是否符合money格式
	 * 
	 * @param money
	 * @return
	 */
	private boolean isMoney(String money) {
		Pattern p = Pattern.compile(RGX_MONEY);
		Matcher m = p.matcher(money);
		boolean b = m.matches();
		System.out.println(b);
		return b;
	}
	
	public static void main(String args[]){
		System.out.println(new BigDecimal("-1").compareTo(BigDecimal.ZERO));
	}
}

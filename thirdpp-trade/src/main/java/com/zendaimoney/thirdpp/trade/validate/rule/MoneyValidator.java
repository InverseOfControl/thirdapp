package com.zendaimoney.thirdpp.trade.validate.rule;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MoneyValidator implements
		ConstraintValidator<Money,BigDecimal> {
	public static String RGX_MONEY = "^[0-9]{1,15}+(\\.[0-9]{0,2})?$";

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
//		System.out.println(b);
		return b;
	}
	
	public static void main(String args[]){
		
		MoneyValidator moneyValidator = new MoneyValidator();
		
		System.out.println("102345678901234:" +moneyValidator.isMoney("102345678901234"));
		
		System.out.println("1023456789012345:" + moneyValidator.isMoney("1023456789012345"));
		
		System.out.println("10234567890123456:" + moneyValidator.isMoney("10234567890123456"));
		
		System.out.println("102345678901234567:" + moneyValidator.isMoney("102345678901234567"));
		
		System.out.println("1.5666666:" + moneyValidator.isMoney("1.5666666"));
		
		System.out.println("-1:" + moneyValidator.isMoney("-1"));
		
		System.out.println("88.1:" + moneyValidator.isMoney("88.1"));
		
		System.out.println("88.01:" + moneyValidator.isMoney("88.01"));
	}
}

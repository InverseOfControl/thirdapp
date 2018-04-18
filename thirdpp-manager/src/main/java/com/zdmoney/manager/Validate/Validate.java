package com.zdmoney.manager.Validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.zdmoney.manager.exception.PlatformErrorCode;
import com.zdmoney.manager.vo.Response;
 
public class Validate<T> {

	private static Validate uniqueInstance = null;

	private static ValidatorFactory factory = null;

	private static Validator validator = null;


	/**
	 * 定义私有构造方法.
	 */
	private Validate() {

	}

	/**
	 * 单例模式.
	 * 
	 * @return
	 */
	public static Validate getInstance() {

		if (uniqueInstance == null) {
			factory = Validation.buildDefaultValidatorFactory();
			validator = factory.getValidator();
			uniqueInstance = new Validate();
		}

		return uniqueInstance;

	}

	/**
	 * 合法行校验
	 * 
	 * @param arg0
	 *            T
	 * @param systemCode
	 *            进行检验系统编码
	 * @param arg1
	 *            Class<T>
	 */
	public String validate(T arg0, Class<T>... arg1) {
		if (arg0 == null) {
			return "Object is null";
		}
		String message = "";
		String[] array = null;

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(
				arg0, arg1);
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			message = constraintViolation.getMessage();
			if (message == null) {
				message = constraintViolation.getPropertyPath() + " " + PlatformErrorCode.ERROR_CODE_MESSAGE_NULL.getErrorCode();
			}
			return message;
		}
		return message;
	}
}

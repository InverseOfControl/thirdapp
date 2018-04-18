package com.zendaimoney.thirdpp.trade.validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.thirdpp.common.vo.Response;
import com.zendaimoney.thirdpp.trade.exception.PlatformErrorCode;

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
	public Response validate(T arg0, Class<T>... arg1) {
		Response response = new Response(Response.SUCCESS_RESPONSE_CODE);
		if (arg0 == null) {
			response = new Response(PlatformErrorCode.VALIDATE_ISNULL.getErrorCode(),
					"Object is null");
			return response;
		}
		String message = "";
		String[] array = null;

		Set<ConstraintViolation<T>> constraintViolations = validator.validate(
				arg0, arg1);
//		List<ConstraintViolation<T>> tempList = sort(constraintViolations);
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			message = constraintViolation.getMessage();
			if (message != null) {
				array = message.split(",");
				if (array != null && array.length == 2) {
					response = new Response(array[0].trim(),
							constraintViolation.getPropertyPath() + " "
									+ array[1]);
				} else {
					response = new Response(PlatformErrorCode.ERROR_CODE_MESSAGE_FORMAT_BAD.getErrorCode(),
							constraintViolation.getPropertyPath() + " "
									+ PlatformErrorCode.ERROR_CODE_MESSAGE_FORMAT_BAD.getDefaultMessage());
				}
			} else {
				response = new Response(PlatformErrorCode.ERROR_CODE_MESSAGE_NULL.getErrorCode(),
						constraintViolation.getPropertyPath() + " "
								+ PlatformErrorCode.ERROR_CODE_MESSAGE_NULL.getErrorCode());
			}
			return response;
		}
		return response;
	}
	
	/**
	 * 将验证器返回的验证结果根据 错误码 进行升序排序，因为测试人员反映当set集合中出现两个或者两个以上的错误码时，set 中的顺序常常不一致
	 * @param constraintViolations
	 * @return
	 */
	private List<ConstraintViolation<T>> sort(Set<ConstraintViolation<T>> constraintViolations){
		List<ConstraintViolation<T>> tempList = new ArrayList<ConstraintViolation<T>>();
		if (null != constraintViolations) {
			tempList = new ArrayList<ConstraintViolation<T>>(constraintViolations);
			if (constraintViolations.size() > 1) {
				Collections.sort(tempList, new Comparator<ConstraintViolation<T>>(){
					@Override
					public int compare(ConstraintViolation<T> c1, ConstraintViolation<T> c2) {
						String c1Code = getErrorCode(c1.getMessage());
						String c2Code = getErrorCode(c2.getMessage());
						return c1Code.compareTo(c2Code);
					}
				});
			}
		} 
		return tempList;
	}
	
	private String getErrorCode(String message){
		if (StringUtils.isNotBlank(message)) {
			String[] array = message.split(",");
			if (array != null && array.length == 2) {
				return array[0].trim();
			}
		}
		return StringUtils.EMPTY;
	}
}

package com.zendaimoney.thirdpp.collect.exception;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author user
 */
public class CollectException extends RuntimeException{
	
	//日誌工具類
	protected static final Logger logger = LoggerFactory.getLogger(CollectException.class);
	protected CollectErrorCode errorCode;

	protected String realCode;

	private Object[] arguments;

	private String errorMsg;

	public CollectException(CollectErrorCode errorCode,String errorMsg, Object... arguments) {
		super();
		this.errorCode = errorCode;
		this.realCode = errorCode.getErrorCode();
		this.errorMsg=errorMsg;
		this.arguments = arguments;
	}

	public CollectException(CollectErrorCode errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.realCode = errorCode.getErrorCode();
	}

	@Override
	public String getMessage() {
		String notMessage = "not error, not message";
		if (errorCode == null || StringUtils.isBlank(realCode)) {
			return notMessage;
		}
		String defaultMessage = "";

		//如果是debug模式
		defaultMessage = errorCode.getDefaultMessage();
		if (StringUtils.isBlank(defaultMessage)) {
			return notMessage;
		}
		return MessageFormat.format(defaultMessage, this.arguments);
	}

	public String getRealCode() {
		return realCode;
	}

	public CollectException(String message, Throwable cause) {
		super(message, cause);
	}


	public CollectException(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}

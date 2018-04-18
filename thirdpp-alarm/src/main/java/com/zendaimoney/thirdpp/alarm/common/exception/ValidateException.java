package com.zendaimoney.thirdpp.alarm.common.exception;

public class ValidateException extends CsswebException {

	private static final long serialVersionUID = 8087751174148354521L;

	public ValidateException() {
		super();
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(long errorCode, String message) {
		super(message);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}
}

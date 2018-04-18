package com.zendaimoney.thirdpp.alarm.common.exception;

public class DataAccessException extends CsswebException {

	private static final long serialVersionUID = 8087751174148354521L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.zendaimoney.thirdpp.alarm.common.exception;

public class CsswebException extends RuntimeException {

	private static final long serialVersionUID = 8087751174148354521L;

	public CsswebException() {
		super();
	}

	public CsswebException(String message) {
		super(message);
	}

	public CsswebException(Throwable cause) {
		super(cause);
	}

	public CsswebException(String message, Throwable cause) {
		super(message, cause);
	}
}

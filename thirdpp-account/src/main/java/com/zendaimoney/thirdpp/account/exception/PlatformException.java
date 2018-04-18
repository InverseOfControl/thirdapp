package com.zendaimoney.thirdpp.account.exception;

public class PlatformException extends RuntimeException {

	private static final long serialVersionUID = 5946365813028078863L;

	public PlatformException() {
		super();
	}

	public PlatformException(String message) {
		super(message);
	}

	public PlatformException(Throwable cause) {
		super(cause);
	}

	public PlatformException(String message, Throwable cause) {
		super(message, cause);
	}
}

package com.zendaimoney.thirdpp.transfer.exception;

public class PlatformException extends RuntimeException {

	private static final long serialVersionUID = 8087751174148354521L;

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

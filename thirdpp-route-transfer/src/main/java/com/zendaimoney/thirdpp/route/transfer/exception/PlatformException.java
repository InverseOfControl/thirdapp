package com.zendaimoney.thirdpp.route.transfer.exception;

public class PlatformException extends RuntimeException {


	/**  */
    private static final long serialVersionUID = 1705618663148677529L;

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

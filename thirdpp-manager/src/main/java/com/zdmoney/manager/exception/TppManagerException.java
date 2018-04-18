package com.zdmoney.manager.exception;

public class TppManagerException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TppManagerException() {
		super();
	}
	 
	public TppManagerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TppManagerException(String message) {
		super(message);
	}

	public TppManagerException(Throwable cause) {
		super(cause);
	}
}

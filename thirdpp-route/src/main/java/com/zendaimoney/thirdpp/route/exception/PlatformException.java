package com.zendaimoney.thirdpp.route.exception;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class PlatformException extends RuntimeException {
	
	/**  */
    private static final long serialVersionUID = -392363186108810678L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

	protected PlatformErrorCode errorCode;

	protected String realCode;

	private Object[] arguments;
	
	private String errorMsg;

	public PlatformErrorCode getErrorCode() {
		return errorCode;
	}

	public PlatformException(PlatformErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
		this.realCode = errorCode.getErrorCode();
		this.errorMsg=errorCode.getDefaultMessage();
	}
	public PlatformException(PlatformErrorCode errorCode,String errorMsg, Object... arguments) {
		super();
		this.errorCode = errorCode;
		this.realCode = errorCode.getErrorCode();
		this.errorMsg=errorMsg;
		this.arguments = arguments;
	}

	public PlatformException(PlatformErrorCode errorCode, Throwable cause) {
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
		if(logger.isDebugEnabled()){
			defaultMessage = errorMsg;
		}else{
			defaultMessage = errorCode.getDefaultMessage();
		}		
		if (StringUtils.isBlank(defaultMessage)) {
			return notMessage;
		}
		return MessageFormat.format(defaultMessage, this.arguments);
	}
	

	public String getRealCode() {
		return realCode;
	}

	public PlatformException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public PlatformException(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}

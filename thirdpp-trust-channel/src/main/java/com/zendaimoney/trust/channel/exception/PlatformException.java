package com.zendaimoney.trust.channel.exception;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.trust.channel.util.LogPrn;

public class PlatformException extends RuntimeException {
	
	//日誌工具類
	private static final LogPrn logger = new LogPrn(PlatformException.class);

	private static final long serialVersionUID = 7946023196149777499L;

	protected PlatformErrorCode errorCode;

	protected String realCode;

	private Object[] arguments;
	
	private String errorMsg;

	public PlatformErrorCode getErrorCode() {
		return errorCode;
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
		if(logger.isDebugEnable()){
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


	public static void main(String args[]){
		PlatformException e = new PlatformException(PlatformErrorCode.VALIDATE_ILLEGAL_MULTIVALUE,PlatformErrorCode.VALIDATE_ILLEGAL_MULTIVALUE.getDefaultMessage(),"A","C");
		System.out.println(e.getMessage());
	}

}

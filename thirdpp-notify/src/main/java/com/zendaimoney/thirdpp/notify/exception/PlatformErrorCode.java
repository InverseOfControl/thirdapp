package com.zendaimoney.thirdpp.notify.exception;

/**
 * 异常代码，加入默认说明，这样减少配置文件的写入了
 */
public enum PlatformErrorCode {
	//检验错误,0开头
	VALIDATE_ISNULL("000001","{0} 为空"),
	VALIDATE_OVERLENGTH("000002","{0} 过长"),
	VALIDATE_ILLEGAL_MULTIVALUE("000003","{0} 应该为 [{1}]"),
	ERROR_CODE_MESSAGE_NULL("000004","{0} validate提示信息未配置"),
	ERROR_CODE_MESSAGE_FORMAT_BAD("000005","{0} validate提示信息格式不对"),

	//系统错误，1开头
	DEFAULT("100001", "默认，无具体信息"), 
	UNKNOWN_ERROR("100002", "未知错误"), 
	DB_ERROR("100003", "数据库操作错误"), 
	PARAM_ERROR("100004", "参数错误"), 
	SYSTEM_BUSY("100005","系统忙，请稍后再试"),
	XML_VALIDATE_ERROR("100006", "xml格式校验错误"),
	
	
	//业务错误，2开头
	XML_SIGN_ERROR("200000","xml报文签名错误"),
	READ_CONFIG_ERROR("200001","读取配置文件错误"),
	DTO_TRANSFER_ERROR("200002","数据传递对象转换错误"),
	DTO_ENCODE_ERROR("200003","数据传递对象编码错误"),
	DTO_DECODE_ERROR("200004","数据传递对象解码错误"),
	VO_2_DTO_ERROR("200005","VO转DTO 错误"),
	EXCEED_BATCHLIMIT_ERROR("200006","超出系统规定的最大批量提交数 错误"),
	NOT_SUPPORT_BANK_ERROR("200007","银行不支持错误"),
	MAX_MONEY_BANK_ERROR("200008","超出银行限额错误"),
	IP_ACCESS_APP_PERMISSION_ERROR("200009","此IP没有访问权限"),
	INFO_CATEGORY_ACCESS_APP_PERMISSION_ERROR("200010","此信息类别没有访问权限"),
	;
	
	private String code;

	private String defaultMessage;

	PlatformErrorCode(String code, String defaultMessage) {
		this.code = code;
		this.defaultMessage = defaultMessage;
	}

	public String getErrorCode() {
		return this.code;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

}

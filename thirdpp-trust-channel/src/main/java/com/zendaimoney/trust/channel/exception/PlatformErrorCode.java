package com.zendaimoney.trust.channel.exception;

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
	TRUST_PROXY_ERROR("100007","代理器运行错误"),
	
	
	//业务错误，3开头
	CHANNEL_SOCKET_ERROR("300000","SOCKET通信异常"),
	CMB_PARSE_ERROR("300001", "返回响应报文异常 解析错误"),
	READ_CONFIG_ERROR("300002","读取配置文件错误"),
	DTO_ENCODE_ERROR("300003","数据传递对象编码错误"),
	DTO_DECODE_ERROR("300004","数据传递对象解码错误"),
	CHANNEL_NOT_FOUND_ERROR("300005", "业务通道不存在或已关闭"),
	CHANNEL_START_ERROR("300006", "通道初始化错误"),
	CHANNEL_BIZ_NOT_FOUND_ERROR("300007", "业务通道类型不存在"),
	CHANNEL_FILE_DOWNLOAD_ERROR("300008", "文件下载失败"),
	CHANNEL_FILE_PARSE_ERROR("300009", "文件解析失败"),
	CHANNEL_FILE_MKDIR_ERROR("300010","TPP创建文件异常"),
	CHANNEL_FTP_MKDIR_ERROR("300011","FTP创建文件失败"),
	CHANNEL_FTP_CHANGE_PATH_ERROR("300012","FTP切换目录失败"),
	VO_2_DTO_ERROR("300013","VO转换 错误"),
	BATCH_OPER_UPDATE_ERROR("300014","批量操作更新错误"),
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

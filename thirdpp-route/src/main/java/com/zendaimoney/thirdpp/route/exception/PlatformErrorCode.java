package com.zendaimoney.thirdpp.route.exception;

/**
 * 异常代码，加入默认说明，这样减少配置文件的写入了
 */
public enum PlatformErrorCode {
	//检验错误,0开头
	VALIDATE_ISNULL("000001","请求参数为空"),

	//系统错误，1开头
	UNKNOW_ERROR("100000", "未知错误!"),
	ROUTE_PARSE_ERROR("100001", "路由配置解析错误!"),
	CACHE_LOAD_ERROR("100002", "缓存加载失败!"),

	//业务错误，2开头
	ROUTE_AVAILABLE_ERROR("200001", "没有可用路由!"),
	ROUTE_CONTROL_ERROR("200002", "失败次数已超过上限!"),


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

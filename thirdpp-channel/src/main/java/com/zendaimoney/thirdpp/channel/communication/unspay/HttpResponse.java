package com.zendaimoney.thirdpp.channel.communication.unspay;

public class HttpResponse {
    /**
     * http响应状态码
     */
    public int code;
    /**
     * 与响应代码一起返回的 HTTP 响应消息
     */
    public String message;
    /**
     * 响应正文内容
     */
    public String content;
 
    public HttpResponse(int code, String message, String content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}
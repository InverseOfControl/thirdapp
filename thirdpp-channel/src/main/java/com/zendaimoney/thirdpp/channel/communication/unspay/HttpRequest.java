package com.zendaimoney.thirdpp.channel.communication.unspay;

import java.util.Map;

public class HttpRequest {
    /**
     * http请求编码,默认UTF-8
     */
    private String charsetName = "UTF-8";
    /**
     * HTTP请求超时时间,默认5000ms
     */
    private int timeout = 5000;
    /***
     * http请求url地址
     */
    private String url;
    /***
     * http请求方法,只支持GET,POST,PUT,DELETE
     */
    private HttpMethod method;
    /***
     * http请求消息报头
     */
    private Map<String, String> headers;
    /**
     * http请求url参数
     */
    private Map<String, String> params;
    /***
     * http请求正文内容
     */
    private String content;
    
    public HttpRequest(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
    }
 
    public HttpRequest(String url, HttpMethod method, Map<String, String> params) {
        this(url, method);
        this.params = params;
    }
    public HttpRequest(String url, HttpMethod method,String content) {
        this(url, method);
        this.content = content;
    }
    public HttpRequest(String url, HttpMethod method,Map<String, String> params, Map<String, String> headers) {
        this(url, method, params);
        this.headers = headers;
    }
 
    public HttpRequest(String url, HttpMethod method,String content, Map<String, String> headers) {
        this(url, method);
        this.content = content;
        this.headers = headers;
    }

	public String getCharsetName() {
		return charsetName;
	}

	public void setCharsetName(String charsetName) {
		this.charsetName = charsetName;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    
    
}

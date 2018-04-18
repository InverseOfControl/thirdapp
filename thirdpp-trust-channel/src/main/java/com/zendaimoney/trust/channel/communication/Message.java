package com.zendaimoney.trust.channel.communication;

import java.util.Map;

public class Message {

	/**
	 * 请求地址
	 */
	protected String url;

	protected String message;

	/**
	 * 请求报文
	 */
	protected String requestInfo;

	/**
	 * 返回报文
	 */
	protected String responseInfo;

	/**
	 * 返回码
	 */
	protected String statusCode;

	/**
	 * 扩展参数
	 */
	protected Map<String, String> messageMap;

	// TPP通道reqid
	protected String channelReqId;

	// 交易流水号
	protected String tradeSn;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(String requestInfo) {
		this.requestInfo = requestInfo;
	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getMessageMap() {
		return messageMap;
	}

	public void setMessageMap(Map<String, String> messageMap) {
		this.messageMap = messageMap;
	}

	public String getChannelReqId() {
		return channelReqId;
	}

	public void setChannelReqId(String channelReqId) {
		this.channelReqId = channelReqId;
	}

	public String getTradeSn() {
		return tradeSn;
	}

	public void setTradeSn(String tradeSn) {
		this.tradeSn = tradeSn;
	}

}

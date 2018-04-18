package com.zendaimoney.thirdpp.channel.communication;

import java.io.Serializable;
import java.util.Map;

/**
 * 第三方支付平台报文
 * @author
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 4387511542449901406L;

	/**
	 * 请求地址
	 */
	protected String url;

	/**
	 * 描述
	 */
	protected String message;

	/**
	 * 请求报文(xml,json)
	 */
	protected String requestInfo;

	/**
	 * 返回报文(xml,json)
	 */
	protected String responseInfo;

	/**
	 * 返回码{@link com.zendaimoney.thirdpp.channel.util.Constants.CommunicationStatus}
	 */
	protected String httpStatusCode;

	/**
	 * 扩展参数
	 */
	protected Map<String, String> messageMap;

	/**
	 * TPP通道reqId
	 */
	protected String channelReqId;

	/**
	 * 交易流水号
	 */
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

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
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

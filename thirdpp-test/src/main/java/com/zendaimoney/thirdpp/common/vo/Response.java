package com.zendaimoney.thirdpp.common.vo;

import java.io.Serializable;

public class Response implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 901629064397797622L;

	/**
	 * 成功返回码
	 */
	public static final String SUCCESS_RESPONSE_CODE = "000000";
	
	public Response(){
		super();
	}

	/**
	 * 
	 * @param code
	 */
	public Response(String code) {
		this.code = code;
	}

	/**
	 * 
	 * @param code
	 * @param msg
	 */
	public Response(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 返回信息
	 */
	private String msg;

	/**
	 * 操作流水号
	 */
	private String flowId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFlowId() {
		return flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

}

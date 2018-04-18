package com.zendaimoney.thirdpp.channel.entity;

import java.io.Serializable;

/**
 * 報文對象
 * 
 * @author 00231257
 *
 */
public class MessageInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8827674647775801073L;

	// 報文類型-請求報文
	public static final String MSG_TYPE_Q = "Q";

	// 報文類型-返回報文
	public static final String MSG_TYPE_S = "S";

	/**
	 * 主鍵
	 */
	private long id;

	/**
	 * TPP通道請求id
	 */
	private String reqId;

	/**
	 * 报文类型(请求:Q,响应:S)
	 */
	private String msgType;
	
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 报文
	 */
	private String message;
	
	
	/**
	 * 支付渠道编码
	 */
	private String paySysNo;
	
	
	/**
	 * 备用字段1
	 */
	private String spare1;
	
	
	/**
	 * 备用字段2
	 */
	private String spare2;
	
	
	public MessageInfo(){
		
	}
	
	public MessageInfo(String reqId,String msgType,String message,String paySysNo, String spare1){
		this.reqId = reqId;
		this.msgType = msgType;
		this.message = message;
		this.paySysNo = paySysNo;
		this.spare1 = spare1;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getReqId() {
		return reqId;
	}


	public void setReqId(String reqId) {
		this.reqId = reqId;
	}


	public String getMsgType() {
		return msgType;
	}


	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getPaySysNo() {
		return paySysNo;
	}


	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}


	public String getSpare1() {
		return spare1;
	}


	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}


	public String getSpare2() {
		return spare2;
	}


	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}
	
	
	
	
	

}

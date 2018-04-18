package com.zendaimoney.thirdpp.channel.conf.yyoupay;

import java.io.Serializable;

public class YyoupayConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 证大商户号
	 */
	private String yYoupayOpenAct;
	
	/**
	 * 还款URL
	 */
	private String yyoupaRepayUrl;
	
	/**
	 *  报文是否需要入库(1-需要 0-不需要)
	 */
	private String msgInStorage;
	
	public String getyYoupayOpenAct() {
		return yYoupayOpenAct;
	}
	
	public void setyYoupayOpenAct(String yYoupayOpenAct) {
		this.yYoupayOpenAct = yYoupayOpenAct;
	}
	
	public String getYyoupaRepayUrl() {
		return yyoupaRepayUrl;
	}
	
	public void setYyoupaRepayUrl(String yyoupaRepayUrl) {
		this.yyoupaRepayUrl = yyoupaRepayUrl;
	}
	
	public String getMsgInStorage() {
		return msgInStorage;
	}
	
	public void setMsgInStorage(String msgInStorage) {
		this.msgInStorage = msgInStorage;
	}
	
}

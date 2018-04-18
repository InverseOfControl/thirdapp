package com.zendaimoney.thirdpp.route.pub.vo;

import java.util.UUID;

/**
 * 所有请求基类, 每个请求需继承此类
 * @author haowp
 *
 */
public class Request implements java.io.Serializable {

	/**  */
    private static final long serialVersionUID = 7606489825126897399L;
    //系统编号
    private String sysCode;
    //业务类别
    private String bizType;
    
    private String uuid = UUID.randomUUID().toString();

	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
    public String getUuid() {
        return uuid;
    }
}

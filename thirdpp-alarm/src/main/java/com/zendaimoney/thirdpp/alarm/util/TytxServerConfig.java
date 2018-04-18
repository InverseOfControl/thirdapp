package com.zendaimoney.thirdpp.alarm.util;

import java.io.Serializable;

/**
 * 无线天利服务器配置
 * 
 * @author liuyi
 * 
 */
public class TytxServerConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8375332091361869073L;
	
	// 系统号
	private String sysId;
	// 发送工号
	private String empId;
	// 信息类别
	private String mType;
	// 字符编码
	private String encoding;
	

	public TytxServerConfig() {

	}


	public TytxServerConfig(String sysId, String empId, String mType,
			String encoding) {
		super();
		this.sysId = sysId;
		this.empId = empId;
		this.mType = mType;
		this.encoding = encoding;
	}


	public String getSysId() {
		return sysId;
	}


	public void setSysId(String sysId) {
		this.sysId = sysId;
	}


	public String getEmpId() {
		return empId;
	}


	public void setEmpId(String empId) {
		this.empId = empId;
	}


	public String getmType() {
		return mType;
	}


	public void setmType(String mType) {
		this.mType = mType;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	
}

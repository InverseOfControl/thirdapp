package com.zendaimoney.thirdpp.channel.pub.vo;

import java.io.Serializable;

import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;

/**
 * 业务请求对象基类
 * @author 00231257
 *
 */
public class BizReqVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2690939846003590219L;
	
	/**
	 * 业务类型
	 */
	private BizType bizType;
	
	
	/**
	 * 第三方通道类型
	 */
	private ThirdType thirdType;
	
	/**
	 * 业务系统
	 */
	private BizSys bizSys;

	
	/**
	 * TPP通道請求ID
	 */
	private String channelReqId;
	
	/**
	 * 信息类别编码
	 */
	private String infoCategoryCode;

	public BizType getBizType() {
		return bizType;
	}


	public void setBizType(BizType bizType) {
		this.bizType = bizType;
	}


	public ThirdType getThirdType() {
		return thirdType;
	}


	public void setThirdType(ThirdType thirdType) {
		this.thirdType = thirdType;
	}


	public BizSys getBizSys() {
		return bizSys;
	}


	public void setBizSys(BizSys bizSys) {
		this.bizSys = bizSys;
	}


	public String getChannelReqId() {
		return channelReqId;
	}


	public void setChannelReqId(String channelReqId) {
		this.channelReqId = channelReqId;
	}


	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}


	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
	
	
	

}

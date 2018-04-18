package com.zendaimoney.trust.channel.pub.vo.req;

import java.io.Serializable;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.pub.enums.TrustBizType;
import com.zendaimoney.trust.channel.pub.enums.TrustCategory;

/**
 * 业务请求对象基类
 * @author mencius
 *
 */
public class TrustBizReqVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2690939846003590219L;
	
	/**
	 * TPP业务类型
	 */
	private BizType bizType;
	
	/**
	 * 资金托管业务类型
	 */
	private TrustBizType trustBizType;
	
	/**
	 * 通道类别
	 */
	private TrustCategory trustCategory;
	
	/**
	 * 通道编码
	 */
	private ThirdType thirdType;
	
	/**
	 * TPP通道请求ID
	 */
	private String channelReqId;
	
	/**
	 * 信息类别编码
	 */
	private String infoCategoryCode;
	
	/**
	 * 业务系统编码
	 */
	private String bizSysNo;
	
	/**
	 * 流水号
	 */
	private String tradeFlow;
	
	/**
	 * 交易日期
	 */
	private String tradeDate;
	
	/**
	 * 交易时间
	 */
	private String tradeTime;
	
	/**
	 * 批次号
	 */
	private int batchNo;
	
	/**
	 * 备用
	 */
	private String spare;
	
	public BizType getBizType() {
		return bizType;
	}
	
	public void setBizType(BizType bizType) {
		this.bizType = bizType;
	}

	public TrustBizType getTrustBizType() {
		return trustBizType;
	}
	
	public void setTrustBizType(TrustBizType trustBizType) {
		this.trustBizType = trustBizType;
	}
	
	public TrustCategory getTrustCategory() {
		return trustCategory;
	}
	
	public void setTrustCategory(TrustCategory trustCategory) {
		this.trustCategory = trustCategory;
	}
	
	public ThirdType getThirdType() {
		return thirdType;
	}
	
	public void setThirdType(ThirdType thirdType) {
		this.thirdType = thirdType;
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
	
	public String getBizSysNo() {
		return bizSysNo;
	}
	
	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}
	
	public String getTradeFlow() {
		return tradeFlow;
	}
	
	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}
	
	public String getTradeDate() {
		return tradeDate;
	}
	
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	
	public String getTradeTime() {
		return tradeTime;
	}
	
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	public int getBatchNo() {
		return batchNo;
	}
	
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	
	public String getSpare() {
		return spare;
	}
	
	public void setSpare(String spare) {
		this.spare = spare;
	}
	
	

}

package com.zendaimoney.thirdpp.notify.entity;

import java.util.Date;

/**
 * @author 00225642
 * 待合并实体类
 *
 */
public class WaitingMergeEntity {
	
	/**
	 * 主键ID
	 */
	private  long id;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 业务类型
	 */
	private String bizTypeNo;
	
	/**
	 * 处理状态，0-待处理；1-处理成功
	 */
	private String status;
	
	/**
	 * 合并模块名称
	 */
	private String mergeModuleName;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	public String getBizTypeNo() {
		return bizTypeNo;
	}

	public void setBizTypeNo(String bizTypeNo) {
		this.bizTypeNo = bizTypeNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMergeModuleName() {
		return mergeModuleName;
	}

	public void setMergeModuleName(String mergeModuleName) {
		this.mergeModuleName = mergeModuleName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}

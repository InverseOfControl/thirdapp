package com.zendaimoney.thirdpp.query.entity;

import java.io.Serializable;

public class MqQueryInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 待处理主键
	 */
	private String _id;

	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 集合名称
	 */
	private String collectionName;
	
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getTradeFlow() {
		return tradeFlow;
	}

	public void setTradeFlow(String tradeFlow) {
		this.tradeFlow = tradeFlow;
	}

	@Override
	public String toString() {
		return "[MqQueryInfo]: _id=" + _id
				+ "tradeFlow=" + tradeFlow
				+ "collectionName=" + collectionName;
	}
	
}

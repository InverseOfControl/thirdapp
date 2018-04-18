package com.zendaimoney.thirdpp.query.entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 对应MongoDB记录数据
 * @author mencius
 *
 */
public class QueryVO {

	
	/**
	 * 主键
	 */
	private String _id;
	
	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 业务类型编码
	 */
	private String bizTypeNo;
	
	/**
	 * 支付渠道编码
	 */
	private String paySysNo;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
	/**
	 * pojo到DBObject转换
	 * 
	 * @return
	 */
	public DBObject toDbObject() {
		BasicDBObject entity = new BasicDBObject();
		entity.put("_id", _id);
		entity.put("tradeFlow", tradeFlow);
		entity.put("bizTypeNo", bizTypeNo);
		entity.put("paySysNo", paySysNo);
		entity.put("createTime", createTime);
		return entity;
	}

	/**
	 * DBObject到pojo转换
	 * 
	 * @param obj
	 */
	public void fromDbObject(DBObject obj) {
		this._id = (String) obj.get("_id");
		this.tradeFlow = (String) obj.get("tradeFlow");
		this.bizTypeNo = (String) obj.get("bizTypeNo");
		this.paySysNo = (String) obj.get("paySysNo");
		this.createTime = (String) obj.get("createTime");
	}
	
}

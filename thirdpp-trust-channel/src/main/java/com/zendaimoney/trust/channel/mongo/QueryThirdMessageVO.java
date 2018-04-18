package com.zendaimoney.trust.channel.mongo;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.zendaimoney.trust.channel.util.CalendarUtils;

/**
 * 查询报文mongo存储Dto
 * @author user
 *
 */
public class QueryThirdMessageVO {

	/**
	 * 主键
	 */
	private String _id;

	/**
	 * 交易流水号
	 */
	private String tradeFlow;
	
	/**
	 * 查询响应时间
	 */
	private String responseTime;
	
	/**
	 * 报文内容
	 */
	private String message;
	
	public QueryThirdMessageVO(String tradeFlow, String message) {
		this.tradeFlow = tradeFlow;
		this.message = message;
	}
	
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
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getResponseTime() {
		return responseTime;
	}
	
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	
	/**
	 * pojo到DBObject转换
	 * 
	 * @return
	 */
	public Document toDbObject() {
		Document entity = new Document();
		entity.put("_id", StringUtils.isEmpty(_id) ? new ObjectId().toString() : _id);
		entity.put("tradeFlow", tradeFlow);
		entity.put("message", message);
		entity.put("responseTime", CalendarUtils.getFormatNow(CalendarUtils.LONG_FORMAT_LINE));
		return entity;
	}

	/**
	 * DBObject到pojo转换
	 * 
	 * @param obj
	 */
	public void fromDbObject(Document obj) {
		this._id = (String) obj.get("_id");
		this.tradeFlow = (String) obj.get("tradeFlow");
		this.message = (String) obj.get("message");
		this.responseTime = (String) obj.get("responseTime");
	}

	@Override
	public String toString() {

		return "[QueryThirdMessageVO{" + " _id:" + _id + " responseTime: " + responseTime +
				" tradeFlow:" + tradeFlow + " message:" + message
				+ "}]";
	}
	
}

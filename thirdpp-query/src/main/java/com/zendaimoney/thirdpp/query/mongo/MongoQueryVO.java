package com.zendaimoney.thirdpp.query.mongo;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.zendaimoney.thirdpp.query.exception.PlatformException;

/**
 * 对应MongoDB记录数据
 * @author mencius
 *
 */
public class MongoQueryVO {

	
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
	 * 系统编号
	 */
	private String bizSysNo;
	
	/**
	 * 支付渠道编码
	 */
	private String paySysNo;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
	
	/**
	 * 交易金额
	 */
	private String amount;
	
	/**
	 * 来源(进程处理名称)
	 */
	private String source;
	
	/**
	 * 创建日期
	 */
	private String createTime;
	
	/**
	 * 信息类别
	 */
	private String infoCategoryCode;
	
	/**
	 * 运营方式(0线下运营1线上运营)
	 */
	private String opMode;
	
	/**
	 * 付款方账户
	 */
	private String payerAccountNo;
	
	/**
	 * 处理标识
	 */
	private String dealFlag;
	
	/**
	 * 查询次数
	 */
	private int queryCount;

	
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
	
	public String getBizSysNo() {
		return bizSysNo;
	}
	
	public void setBizSysNo(String bizSysNo) {
		this.bizSysNo = bizSysNo;
	}

	public String getPaySysNo() {
		return paySysNo;
	}

	public void setPaySysNo(String paySysNo) {
		this.paySysNo = paySysNo;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}
	
	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
	}
	
	public String getDealFlag() {
		return dealFlag;
	}
	
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}
	
	public String getOpMode() {
		return opMode;
	}
	
	public void setOpMode(String opMode) {
		this.opMode = opMode;
	}
	
	public String getPayerAccountNo() {
		return payerAccountNo;
	}
	
	public void setPayerAccountNo(String payerAccountNo) {
		this.payerAccountNo = payerAccountNo;
	}
	
	public int getQueryCount() {
		return queryCount;
	}
	
	public void setQueryCount(int queryCount) {
		this.queryCount = queryCount;
	}
	
	/**
	 * pojo到DBObject转换
	 * 
	 * @return
	 */
	public Document toDbObject() {
		Document entity = new Document();
		entity.put("_id", new ObjectId().toString());
		entity.put("tradeFlow", tradeFlow);
		entity.put("bizTypeNo", bizTypeNo);
		entity.put("bizSysNo", bizSysNo);
		entity.put("paySysNo", paySysNo);
		entity.put("timestamp", timestamp);
		entity.put("amount", amount);
		entity.put("source", source);
		entity.put("createTime", createTime);
		entity.put("infoCategoryCode", infoCategoryCode);
		entity.put("opMode", opMode);// 运营方式
		entity.put("payerAccountNo", payerAccountNo); // 付款方账户
		entity.put("dealFlag", dealFlag);
		entity.put("queryCount", queryCount); // 查询次数
		return entity;
	}

	/**
	 * DBObject到pojo转换
	 * 
	 * @param obj
	 */
	public void fromDbObject(Document obj) {
		if (obj.containsKey("_id")) {
			
			Object object = obj.get("_id");
			
			if (object instanceof String) {
				
				this._id = (String) object;
			} else if (object instanceof ObjectId) {
				ObjectId objectId = (ObjectId)object;
				this._id = objectId.toString();
			}
		}
		
		if (obj.containsKey("tradeFlow")) {
			
			this.tradeFlow = (String) obj.get("tradeFlow");
		}
		
		if (obj.containsKey("bizTypeNo")) {
			
			this.bizTypeNo = (String) obj.get("bizTypeNo");
		}
		
		if (obj.containsKey("bizSysNo")) {
			
			this.bizSysNo = (String) obj.get("bizSysNo");
		}
		
		if (obj.containsKey("paySysNo")) {
			
			this.paySysNo = (String) obj.get("paySysNo");
		}
		
		if (obj.containsKey("timestamp")) {
			
			this.timestamp = (String) obj.get("timestamp");
		}
		
		if (obj.containsKey("amount")) {
			
			this.amount = (String) obj.get("amount");
		}
		
		if (obj.containsKey("source")) {
			
			this.source = (String) obj.get("source");
		}
		
		if (obj.containsKey("createTime")) {
			
			this.createTime = (String) obj.get("createTime");
		}
		
		if (obj.containsKey("infoCategoryCode")) {
			this.infoCategoryCode = (String) obj.get("infoCategoryCode");
		}
		
		if (obj.containsKey("opMode")) {
			this.opMode = (String) obj.get("opMode");
		}
		
		if (obj.containsKey("payerAccountNo")) {
			this.payerAccountNo = (String) obj.get("payerAccountNo");
		}
		
		if (obj.containsKey("dealFlag")) {
			this.dealFlag = (String) obj.get("dealFlag");
		}
		
		if (obj.containsKey("queryCount")) {
			this.queryCount = StringUtils.isBlank(obj.get("queryCount") + "") ? 0 : Integer.valueOf(obj.get("queryCount") + "");
		}
	}
	
	
	/**
	 * 验证对象是否合法
	 * @return true/false
	 */
	public boolean isValid() {
		
		if (StringUtils.isEmpty(this._id)) {
			throw new PlatformException(this.toString() + ": _id is empty!");
		}
		
		if (StringUtils.isEmpty(this.tradeFlow)) {
			throw new PlatformException(this.toString() + ": tradeFlow is empty!");
		}
		
		if (StringUtils.isEmpty(this.bizTypeNo)) {
			throw new PlatformException(this.toString() + ": bizTypeNo is empty!");
		}
		
		if (StringUtils.isEmpty(this.paySysNo)) {
			throw new PlatformException(this.toString() + ": paySysNo is empty!");
		}
		
		if (StringUtils.isEmpty(this.timestamp)) {
			throw new PlatformException(this.toString() + ": timestamp is empty!");
		}
		
		if (StringUtils.isEmpty(this.amount)) {
			throw new PlatformException(this.toString() + ": amount is empty!");
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		
		return "[MongoQueryVO{"
				+ " _id:" + _id
				+ " tradeFlow:" + tradeFlow 
				+ " bizTypeNo:" + bizTypeNo 
				+ " paySysNo:" + paySysNo 
				+ " timestamp:" + timestamp 
				+ " amount:" + amount 
				+ " source:" + source
				+ " infoCategoryCode:" + infoCategoryCode
				+ " opMode:" + opMode
				+ " payerAccountNo:" + payerAccountNo
				+ " dealFlag:" + dealFlag
				+ " queryCount:" + queryCount
				+ "}]";
	}
	
}

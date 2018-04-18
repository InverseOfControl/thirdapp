package com.zendaimoney.trust.channel.mongo;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.zendaimoney.trust.channel.util.CalendarUtils;

/**
 * 不确定交易
 * 
 * @author mencius
 *
 */
public class UnknowTradeVO {

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
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 交易金额
	 */
	private String amount;

	/**
	 * 来源(进程处理名称)
	 */
	private String source;

	/**
	 * 信息类别编码
	 */
	private String infoCategoryCode;
	
	/**
	 * 运营方式(0线下运营1线上运营)
	 */
	private String opMode;
	
	/**
	 * 用户账号
	 */
	private String payerAccountNo;
	
	/**
	 * 资金托管业务类型
	 */
	private String trustBizType;

	public UnknowTradeVO() {

	}

	public UnknowTradeVO(String tradeFlow, String bizTypeNo, String bizSysNo,
			String paySysNo, String amount, String infoCategoryCode, String opMode, String payerAccountNo) {
		this.tradeFlow = tradeFlow;
		this.bizTypeNo = bizTypeNo;
		this.bizSysNo = bizSysNo;
		this.paySysNo = paySysNo;
		this.createTime = CalendarUtils.parsefomatCalendar(
				Calendar.getInstance(), CalendarUtils.LONG_FORMAT_LINE);
		this.amount = amount;
		this.infoCategoryCode = infoCategoryCode;
		this.opMode = opMode;
		this.payerAccountNo = payerAccountNo;
	}
	
	public UnknowTradeVO(String tradeFlow, String bizTypeNo, String bizSysNo,
			String paySysNo, String amount, String infoCategoryCode, String opMode, String payerAccountNo, String trustBizType) {
		this.tradeFlow = tradeFlow;
		this.bizTypeNo = bizTypeNo;
		this.bizSysNo = bizSysNo;
		this.paySysNo = paySysNo;
		this.createTime = CalendarUtils.parsefomatCalendar(
				Calendar.getInstance(), CalendarUtils.LONG_FORMAT_LINE);
		this.amount = amount;
		this.infoCategoryCode = infoCategoryCode;
		this.opMode = opMode;
		this.payerAccountNo = payerAccountNo;
		this.trustBizType = trustBizType;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getInfoCategoryCode() {
		return infoCategoryCode;
	}

	public void setInfoCategoryCode(String infoCategoryCode) {
		this.infoCategoryCode = infoCategoryCode;
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
	
	public String getTrustBizType() {
		return trustBizType;
	}
	
	public void setTrustBizType(String trustBizType) {
		this.trustBizType = trustBizType;
	}

	/**
	 * pojo到DBObject转换
	 * 
	 * @return
	 */
	public Document toDbObject() {
		Document entity = new Document();
		entity.put("_id", StringUtils.isEmpty(_id) ? new ObjectId().toString()
				: _id);
		entity.put("tradeFlow", tradeFlow);
		entity.put("bizTypeNo", bizTypeNo);
		entity.put("bizSysNo", bizSysNo);
		entity.put("paySysNo", paySysNo);
		entity.put("createTime", createTime);
		entity.put("amount", amount);
		entity.put("source", source);
		entity.put("infoCategoryCode", infoCategoryCode);
		entity.put("opMode", opMode);
		entity.put("payerAccountNo", payerAccountNo);
		entity.put("trustBizType", trustBizType);
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
		this.bizTypeNo = (String) obj.get("bizTypeNo");
		this.bizSysNo = (String) obj.get("bizSysNo");
		this.paySysNo = (String) obj.get("paySysNo");
		this.createTime = (String) obj.get("createTime");
		this.amount = (String) obj.get("amount");
		this.source = (String) obj.get("source");
		this.infoCategoryCode = (String) obj.get("infoCategoryCode");
		this.opMode = (String) obj.get("opMode");
		this.payerAccountNo = (String) obj.get("payerAccountNo");
		this.trustBizType = (String) obj.get("trustBizType");
	}

	@Override
	public String toString() {

		return "[UnSureTradeVO{" + " _id:" + _id + " tradeFlow:" + tradeFlow
				+ " bizTypeNo:" + bizTypeNo + " paySysNo:" + paySysNo
				+ " createTime:" + createTime + " amount:" + amount
				+ " infoCategoryCode:" + infoCategoryCode + " source:" + source
				+ " opMode:" + opMode + " payerAccountNo:" + payerAccountNo
				+ " trustBizType:" + trustBizType
				+ "}]";
	}

}

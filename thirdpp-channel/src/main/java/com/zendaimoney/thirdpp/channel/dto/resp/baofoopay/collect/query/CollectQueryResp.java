package com.zendaimoney.thirdpp.channel.dto.resp.baofoopay.collect.query;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.baofoopay.collect.trade.CollectResp;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

import javax.xml.bind.annotation.*;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "result")
public class CollectQueryResp extends RespDto{

	/**
	 * 版本号(必须){4.0.0.0}
	 */
	@XmlElement(name = "version")
	private String version;

	/**
	 * 请求方保留域
	 */
	@XmlElement(name = "req_reserved")
	private String reqReserved;

	/**
	 * 附加字段
	 */
	@XmlElement(name = "additional_info")
	private String additionalInfo;

	/**
	 * 应答码
	 */
	@XmlElement(name = "resp_code")
	private String respCode;

	/**
	 * 应答信息
	 */
	@XmlElement(name = "resp_msg")
	private String respMsg;

	/**
	 * 商户号 (必填){在收付捷平台中开通的商户编号}
	 */
	@XmlElement(name = "member_id")
	private String merId;


	/**
	 * 终端号(必须){商户号+订单号+商户日期唯一标示一笔交易}
	 */
	@XmlElement(name = "terminal_id")
	private String terminalId;

	/**
	 * 加密数据类型(必须)
	 */
	@XmlElement(name = "data_type")
	private String dataType;


	/**
	 * 交易类型
	 */
	@XmlElement(name = "txn_type")
	private String txnType;

	/**
	 * 交易子类型
	 */
	@XmlElement(name = "txn_sub_type")
	private String txnSubType;

	/**
	 * 接入类型
	 */
	@XmlElement(name = "biz_type")
	private String accessType;

	/**
	 * 原订单时间
	 */
	@XmlElement(name = "orig_trade_date")
	private String origTradeDate;

	/**
	 * 商户订单号
	 */
	@XmlElement(name = "orig_trans_id")
	private String origTransId;

	/**
	 * 宝付交易流水号
	 */
	@XmlElement(name = "trans_no")
	private String transNo;

	/**
	 * 成功金额
	 */
	@XmlElement(name = "succ_amt")
	private String succAmt;

	/**
	 * 商户流水号
	 */
	@XmlElement(name = "trans_serial_no")
	private String transSerialNo;

	/**
	 * 商户流水号
	 */
	@XmlElement(name = "order_stat")
	private String orderStat;


	@Override
	public CollectQueryResp decode(String respMsg) throws PlatformException {


		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);


	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReqReserved() {
		return reqReserved;
	}

	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getOrigTradeDate() {
		return origTradeDate;
	}

	public void setOrigTradeDate(String origTradeDate) {
		this.origTradeDate = origTradeDate;
	}

	public String getOrigTransId() {
		return origTransId;
	}

	public void setOrigTransId(String origTransId) {
		this.origTransId = origTransId;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getSuccAmt() {
		return succAmt;
	}

	public void setSuccAmt(String succAmt) {
		this.succAmt = succAmt;
	}

	public String getTransSerialNo() {
		return transSerialNo;
	}

	public void setTransSerialNo(String transSerialNo) {
		this.transSerialNo = transSerialNo;
	}
}

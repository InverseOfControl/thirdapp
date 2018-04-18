package com.zendaimoney.thirdpp.channel.dto.resp.baofoopay.collect.trade;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.baofoopay.BaofoopayUtil;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

import javax.xml.bind.annotation.*;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "result")
public class CollectResp extends RespDto{

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
	 * 订单发送时间
	 */
	@XmlElement(name = "trade_date")
	private String tradeDate;
	
	/**
	 * 商户订单号
	 */
	@XmlElement(name = "trans_id")
	private String transId;
	
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
	

	@Override
	public CollectResp decode(String respMsg) throws PlatformException {
		
		CollectResp dto = new CollectResp();
		//Map<String, String> valueMap = BaofoopayUtil.getResponseMap(respMsg, ChannelCategory.TRADE);


		//dto = (CollectResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
		//return dto;

		//String valueJson = BaofoopayUtil.getResponseJson(respMsg, ChannelCategory.TRADE);
		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);


	}
	
	/**
	 * 对象转XML报文
	 * 
	 * @return
	 */
	public String encode(String respMsg) throws PlatformException {
		JaxbBinder binder = null;
		String xml = null;
		try {
			binder = new JaxbBinder(this.getClass());
			xml = binder.toXml(this, "UTF-8");
			
		} catch (Exception e) {
			throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return xml.substring(xml.indexOf("\n") + 1);
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
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

	@Override
	public String toString() {
		return super.toString();
	}
}

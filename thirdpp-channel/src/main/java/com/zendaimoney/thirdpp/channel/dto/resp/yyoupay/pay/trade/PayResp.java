package com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.pay.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "YyouPayResp")
public class PayResp extends RespDto {

	/**
	 * 交易类型
	 */
	@XmlElement(name = "dealType")
	private String dealType = "BID_PAYORDER";

	/**
	 * 交易子类型
	 */
	@XmlElement(name = "subDealType")
	private String subDealType;

	/**
	 * tp流水
	 */
	@XmlElement(name = "transferFlow")
	private String transferFlow;

	/**
	 * 我方订单号
	 */
	@XmlElement(name = "flow")
	private String flow;

	/**
	 * 放款用户ID
	 */
	@XmlElement(name = "tpUserCode")
	private String tpUserCode = "";

	/**
	 * 融资ID
	 */
	@XmlElement(name = "tpUserCode2")
	private String tpUserCode2 = "";

	/**
	 * 融资金额
	 */
	@XmlElement(name = "amount")
	private String amount = "";

	/**
	 * 交易日期
	 */
	@XmlElement(name = "date")
	private String date = "";

	/**
	 * tpId
	 */
	@XmlElement(name = "tpId")
	private String tpId = "";

	/**
	 * 返回码
	 */
	@XmlElement(name = "retCode")
	private String retCode;

	/**
	 * 附加信息
	 */
	@XmlElement(name = "retMessage")
	private String retMessage;

	/**
	 * 备注1
	 */
	@XmlElement(name = "note1")
	private String note1;

	/**
	 * 备注2
	 */
	@XmlElement(name = "note2")
	private String note2;

	public PayResp() {

	}

	@Override
	public PayResp decode(String respMsg) throws PlatformException {

		PayResp dto = new PayResp();
		// Map<String, String> valueMap = ShunionpayUtil.getResponseMap(respMsg,
		// ChannelCategory.TRADE);
		//
		// dto = (RepayResp)
		// JSONHelper.json2Object(JSONHelper.map2json(valueMap),
		// dto.getClass());
		return dto;
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

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getSubDealType() {
		return subDealType;
	}

	public void setSubDealType(String subDealType) {
		this.subDealType = subDealType;
	}

	public String getTpUserCode() {
		return tpUserCode;
	}

	public void setTpUserCode(String tpUserCode) {
		this.tpUserCode = tpUserCode;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMessage() {
		return retMessage;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	public String getNote1() {
		return note1;
	}

	public void setNote1(String note1) {
		this.note1 = note1;
	}

	public String getNote2() {
		return note2;
	}

	public void setNote2(String note2) {
		this.note2 = note2;
	}

	public String getTransferFlow() {
		return transferFlow;
	}

	public void setTransferFlow(String transferFlow) {
		this.transferFlow = transferFlow;
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getTpUserCode2() {
		return tpUserCode2;
	}

	public void setTpUserCode2(String tpUserCode2) {
		this.tpUserCode2 = tpUserCode2;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTpId() {
		return tpId;
	}

	public void setTpId(String tpId) {
		this.tpId = tpId;
	}

}

package com.zendaimoney.thirdpp.channel.dto.resp.shunionpay.collect.trade;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

@XmlAccessorType(XmlAccessType.FIELD)
public class CollectResp extends RespDto{

	/**
	 * 应答信息
	 */
	@XmlElement(name = "responseCode")
	private String responseCode;
	
	/**
	 * 商户号
	 */
	@XmlElement(name = "merId")
	private String merId;
	
	/**
	 * 商户日期
	 */
	@XmlElement(name = "transDate")
	private String transDate;
	
	/**
	 * 订单号
	 */
	@XmlElement(name = "orderNo")
	private String orderNo;
	
	/**
	 * 金额
	 */
	@XmlElement(name = "transAmt")
	private String transAmt;
	
	/**
	 * 币种
	 */
	@XmlElement(name = "curyId")
	private String curyId;
	
	/**
	 * 交易类型
	 */
	@XmlElement(name = "transType")
	private String transType;
	
	/**
	 * 私有域
	 */
	@XmlElement(name = "priv1")
	private String priv1;
	
	/**
	 * 代扣状态
	 */
	@XmlElement(name = "transStat")
	private String transStat;
	
	/**
	 * 网关号
	 */
	@XmlElement(name = "gateId")
	private String gateId;
	
	/**
	 * 卡折标志
	 */
	@XmlElement(name = "cardType")
	private String cardType;
	
	/**
	 * 卡号/折号
	 */
	@XmlElement(name = "cardNo")
	private String cardNo;
	
	/**
	 * 持卡人姓名
	 */
	@XmlElement(name = "userNme")
	private String userNme;
	
	/**
	 * 证件类型
	 */
	@XmlElement(name = "certType")
	private String certType;
	
	/**
	 * 证件号
	 */
	@XmlElement(name = "certId")
	private String certId;
	
	/**
	 * 描述
	 */
	@XmlElement(name = "message")
	private String message;
	
	/**
	 * 签名值
	 */
	@XmlElement(name = "chkValue")
	private String chkValue;
	
	@Override
	public CollectResp decode(String respMsg) throws PlatformException {
		
		CollectResp dto = new CollectResp();
		Map<String, String> valueMap = ShunionpayUtil.getResponseMap(respMsg, ChannelCategory.TRADE);
		
		dto = (CollectResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
		
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


	public String getResponseCode() {
		return responseCode;
	}


	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}


	public String getMerId() {
		return merId;
	}


	public void setMerId(String merId) {
		this.merId = merId;
	}


	public String getTransDate() {
		return transDate;
	}


	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getTransAmt() {
		return transAmt;
	}


	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}


	public String getCuryId() {
		return curyId;
	}


	public void setCuryId(String curyId) {
		this.curyId = curyId;
	}


	public String getTransType() {
		return transType;
	}


	public void setTransType(String transType) {
		this.transType = transType;
	}


	public String getPriv1() {
		return priv1;
	}


	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}


	public String getTransStat() {
		return transStat;
	}


	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}


	public String getGateId() {
		return gateId;
	}


	public void setGateId(String gateId) {
		this.gateId = gateId;
	}


	public String getCardType() {
		return cardType;
	}


	public void setCardType(String cardType) {
		this.cardType = cardType;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getUserNme() {
		return userNme;
	}


	public void setUserNme(String userNme) {
		this.userNme = userNme;
	}


	public String getCertType() {
		return certType;
	}


	public void setCertType(String certType) {
		this.certType = certType;
	}


	public String getCertId() {
		return certId;
	}


	public void setCertId(String certId) {
		this.certId = certId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getChkValue() {
		return chkValue;
	}


	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}

package com.zendaimoney.thirdpp.channel.dto.resp.shunionpay.pay.trade;

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
public class PayResp extends RespDto{

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
	 * 交易日期
	 */
	@XmlElement(name = "merDate")
	private String merDate;
	
	/**
	 * 商户流水号
	 */
	@XmlElement(name = "merSeqId")
	private String merSeqId;
	
	/**
	 * 响应日期
	 */
	@XmlElement(name = "cpDate")
	private String cpDate;
	
	/**
	 * 响应流水号
	 */
	@XmlElement(name = "cpSeqId")
	private String cpSeqId;
	
	/**
	 * 交易金额
	 */
	@XmlElement(name = "transAmt")
	private String transAmt;
	
	/**
	 * 交易状态码
	 */
	@XmlElement(name = "stat")
	private String stat;
	
	/**
	 * 收款账户
	 */
	@XmlElement(name = "cardNo")
	private String cardNo;
	
	/**
	 * 签名值
	 */
	@XmlElement(name = "chkValue")
	private String chkValue;
	
	
	@Override
	public PayResp decode(String respMsg) throws PlatformException {
		PayResp dto = new PayResp();
		Map<String, String> valueMap = ShunionpayUtil.getResponseMap(respMsg, ChannelCategory.TRADE);
		
		dto = (PayResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
		
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

	public String getMerDate() {
		return merDate;
	}

	public void setMerDate(String merDate) {
		this.merDate = merDate;
	}

	public String getMerSeqId() {
		return merSeqId;
	}

	public void setMerSeqId(String merSeqId) {
		this.merSeqId = merSeqId;
	}

	public String getCpDate() {
		return cpDate;
	}

	public void setCpDate(String cpDate) {
		this.cpDate = cpDate;
	}

	public String getCpSeqId() {
		return cpSeqId;
	}

	public void setCpSeqId(String cpSeqId) {
		this.cpSeqId = cpSeqId;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	
	

}

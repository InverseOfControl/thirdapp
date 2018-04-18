package com.zendaimoney.thirdpp.channel.dto.resp.shunionpay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class CollectResp extends RespDto {
	
	/**
	 * 应答信息
	 */
	@XmlElement(name = "responseCode")
	private String responseCode;
	
	/**
	 * 商户号 (必填){在收付捷平台中开通的商户编号}
	 */
	@XmlElement(name = "merId")
	private String merId;

	/**
	 * 商户日期(必须) {YYYYMMDD}
	 */
	@XmlElement(name = "transDate")
	private String transDate;
	
	/**
	 * 订单号(必须){商户号+订单号+商户日期唯一标示一笔交易}
	 */
	@XmlElement(name = "orderNo")
	private String orderNo;
	
	/**
	 * 整数，以分为单位(必须)
	 */
	@XmlElement(name = "transAmt")
	private String transAmt;

	/**
	 * 币种(必须){固定为156，表示货币单位为人民币}
	 */
	@XmlElement(name = "curyId")
	private String curyId;
	
	/**
	 * 交易类型
	 */
	@XmlElement(name = "transType")
	private String transType;
	
	/**
	 * 私有域1{商户保留域，需以unicode传值}
	 */
	@XmlElement(name = "priv1")
	private String priv1;
	
	/**
	 * 代扣状态
	 */
	@XmlElement(name = "transStat")
	private String transStat;
	
	/**
	 * 网管号(必须){固定为7008}
	 */
	@XmlElement(name = "gateId")
	private String gateId;
	
	/**
	 * 卡折标志(必须) {卡号或存折号标识位(0表示卡,1表示折)}
	 */
	@XmlElement(name = "cardType")
	private String cardType;

	/**
	 * 银行卡号或者存折号(必须)
	 */
	@XmlElement(name = "cardNo")
	private String cardNo;
	
	/**
	 * 持卡人姓名(必须){需以unicode传值}
	 */
	@XmlElement(name = "userNme")
	private String userNme;
	
	/**
	 * 证件类型(必须)
	 */
	@XmlElement(name = "certType")
	private String certType;
	
	/**
	 * 证件号(必须)
	 */
	@XmlElement(name = "certId")
	private String certId;
	
	/**
	 * 交易应答返回描述
	 */
	@XmlElement(name = "message")
	private String message;
	
	/**
	 * 签名值(必须)
	 */
	@XmlElement(name = "chkValue")
	private String chkValue;

	/**
	 * 返回XML报文转成CollectResp。
	 */
	@Override
	public CollectResp decode(String respMsg) {

		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);
	}

}

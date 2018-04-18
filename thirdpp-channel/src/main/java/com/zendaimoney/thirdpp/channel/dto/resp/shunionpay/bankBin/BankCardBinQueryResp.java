package com.zendaimoney.thirdpp.channel.dto.resp.shunionpay.bankBin;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.dto.resp.shunionpay.bankCardAuth.BankCardAuthResp;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "BankCardBinQryResp")
public class BankCardBinQueryResp extends RespDto{

	/**
	 * 应用系统编号
	 */
	@XmlElement(name = "appSysId")
	private String appSysId = "";
	
	/**
	 * 服务类型
	 */
	@XmlElement(name = "serviceType")
	private String serviceType = "";
	
	/**
	 * 签名方法
	 */
	@XmlElement(name = "signMethod")
	private String signMethod = "";
	
	/**
	 * 签名信息
	 */
	@XmlElement(name = "signature")
	private String signature = "";
	
	/**
	 * 卡号
	 */
	@XmlElement(name = "cardNo")
	private String cardNo = "";
	
	/**
	 * 卡种
	 */
	@XmlElement(name = "cardSpec")
	private String cardSpec = "";
	
	/**
	 * 卡bin
	 */
	@XmlElement(name = "cardBin")
	private String cardBin = "";
	
	/**
	 * 卡长度
	 */
	@XmlElement(name = "cardLen")
	private String cardLen = "";
	
	/**
	 * 借贷类型
	 */
	@XmlElement(name = "dcType")
	private String dcType = "";
	
	/**
	 * 发卡行编号
	 */
	@XmlElement(name = "cardIssuer")
	private String cardIssuer = "";
	
	/**
	 * 发卡行名称
	 */
	@XmlElement(name = "cardIssuerName")
	private String cardIssuerName = "";
	
	/**
	 * 代扣银行编号
	 */
	@XmlElement(name = "oraCardIssuer")
	private String oraCardIssuer = "";
	
	/**
	 * 响应码
	 */
	@XmlElement(name = "respcode")
	private String respcode = "";
	
	/**
	 * 响应信息
	 */
	@XmlElement(name = "respmsg")
	private String respmsg = "";
	
	
	
	@Override
	public BankCardBinQueryResp decode(String respMsg) throws PlatformException {
		
		// 如果响应报文为空，则解析报文失败
		if (StringUtils.isBlank(respMsg) || Constants.NULL.equalsIgnoreCase(respMsg.trim())) {
			throw new PlatformException(PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR, PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR.getDefaultMessage());
		}
		BankCardBinQueryResp dto = new BankCardBinQueryResp();
		Map<String, String> valueMap = ShunionpayUtil.getResponseMap(respMsg, ChannelCategory.AUTH);
		
		dto = (BankCardBinQueryResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
		
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
			
			System.out.println(e.getMessage());
			throw new PlatformException(PlatformErrorCode.DTO_ENCODE_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}
		return xml.substring(xml.indexOf("\n") + 1);
	}

	public String getAppSysId() {
		return appSysId;
	}

	public void setAppSysId(String appSysId) {
		this.appSysId = appSysId;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardSpec() {
		return cardSpec;
	}

	public void setCardSpec(String cardSpec) {
		this.cardSpec = cardSpec;
	}

	public String getCardBin() {
		return cardBin;
	}

	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}

	public String getCardLen() {
		return cardLen;
	}

	public void setCardLen(String cardLen) {
		this.cardLen = cardLen;
	}

	public String getDcType() {
		return dcType;
	}

	public void setDcType(String dcType) {
		this.dcType = dcType;
	}

	public String getCardIssuer() {
		return cardIssuer;
	}

	public void setCardIssuer(String cardIssuer) {
		this.cardIssuer = cardIssuer;
	}

	public String getCardIssuerName() {
		return cardIssuerName;
	}

	public void setCardIssuerName(String cardIssuerName) {
		this.cardIssuerName = cardIssuerName;
	}

	public String getOraCardIssuer() {
		return oraCardIssuer;
	}

	public void setOraCardIssuer(String oraCardIssuer) {
		this.oraCardIssuer = oraCardIssuer;
	}

	public String getRespcode() {
		return respcode;
	}

	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}

	public String getRespmsg() {
		return respmsg;
	}

	public void setRespmsg(String respmsg) {
		this.respmsg = respmsg;
	}
	
}

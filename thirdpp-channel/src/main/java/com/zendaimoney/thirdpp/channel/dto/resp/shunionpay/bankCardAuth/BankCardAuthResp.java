package com.zendaimoney.thirdpp.channel.dto.resp.shunionpay.bankCardAuth;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.MapToXmlUtils;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "BankCardAuthResp")
public class BankCardAuthResp extends RespDto {

	/**
	 * 应用系统编号
	 */
	@XmlElement(name = "appSysId")
	private String appSysId = "";

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
	 * 客户号
	 */
	@XmlElement(name = "usrSysId")
	private String usrSysId = "";

	/**
	 * 邮箱
	 */
	@XmlElement(name = "email")
	private String email = "";

	/**
	 * 手机号
	 */
	@XmlElement(name = "mobile")
	private String mobile = "";

	/**
	 * 卡号
	 */
	@XmlElement(name = "cardNo")
	private String cardNo = "";

	/**
	 * 证件类型
	 */
	@XmlElement(name = "certType")
	private String certType = "";

	/**
	 * 证件号
	 */
	@XmlElement(name = "certNo")
	private String certNo = "";

	/**
	 * 证件姓名
	 */
	@XmlElement(name = "usrName")
	private String usrName = "";

	/**
	 * CVN2
	 */
	@XmlElement(name = "cardCvn2")
	private String cardCvn2 = "";

	/**
	 * 卡有效期
	 */
	@XmlElement(name = "cardExpire")
	private String cardExpire = "";

	/**
	 * 卡关联手机号
	 */
	@XmlElement(name = "cardPhone")
	private String cardPhone = "";

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
	
	/**
	 * 第三方产品类型
	 */
	@XmlElement(name = "thirdBizType")
	private String thirdBizType = "";

	public String getAppSysId() {
		return appSysId;
	}

	public void setAppSysId(String appSysId) {
		this.appSysId = appSysId;
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

	public String getUsrSysId() {
		return usrSysId;
	}

	public void setUsrSysId(String usrSysId) {
		this.usrSysId = usrSysId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getCardCvn2() {
		return cardCvn2;
	}

	public void setCardCvn2(String cardCvn2) {
		this.cardCvn2 = cardCvn2;
	}

	public String getCardExpire() {
		return cardExpire;
	}

	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}

	public String getCardPhone() {
		return cardPhone;
	}

	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
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
	
	public String getThirdBizType() {
		return thirdBizType;
	}
	
	public void setThirdBizType(String thirdBizType) {
		this.thirdBizType = thirdBizType;
	}

	@Override
	public BankCardAuthResp decode(String respMsg) throws PlatformException {
		
		// 如果响应报文为空，则解析报文失败
		if (StringUtils.isBlank(respMsg) || Constants.NULL.equalsIgnoreCase(respMsg.trim())) {
			throw new PlatformException(PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR, PlatformErrorCode.THIRD_RESPONSE_NO_MESSAGE_ERROR.getDefaultMessage());
		}
		Map<String, String> valueMap = ShunionpayUtil.getResponseMap(respMsg, ChannelCategory.AUTH);
		
		return (BankCardAuthResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), this.getClass());
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

}

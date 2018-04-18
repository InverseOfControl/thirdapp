package com.zendaimoney.thirdpp.channel.dto.req.shunionpay.bankCardAuth;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardAuthReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

/**
 * 实名认证数据传递对象
 * 
 * @author 00231257
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "BankCardAuthReq")
public class BankCardAuthReq extends ReqDto {

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
	 * 认证渠道
	 */
	@XmlElement(name = "channelId")
	private String channelId = "";

	/**
	 * 认证方法
	 */
	@XmlElement(name = "validWayId")
	private String validWayId = "";

	/**
	 * 产品类型
	 */
	@XmlElement(name = "bizType")
	private String businessType = "";

	/**
	 * 渠道商户号
	 */
	@XmlElement(name = "merId")
	private String merId = "";

	/**
	 * 渠道商户名称
	 */
	@XmlElement(name = "merName")
	private String merName = "";

	/**
	 * 是否保存
	 */
	@XmlElement(name = "save")
	private String save = "false";

	/**
	 * 借贷标志(0：借记卡1：贷记卡)
	 */
	@XmlElement(name = "dcType", required = true)
	private String dcType = "";

	/**
	 * 卡号
	 */
	@XmlElement(name = "cardNo", required = true)
	private String cardNo = "";

	/**
	 * 证件类型
	 */
	@XmlElement(name = "certType", required = true)
	private String certType = "";

	/**
	 * 证件号
	 */
	@XmlElement(name = "certNo", required = true)
	private String certNo = "";

	/**
	 * 证件姓名
	 */
	@XmlElement(name = "usrName", required = true)
	private String usrName = "";

	/**
	 * 卡密
	 */
	@XmlElement(name = "pin")
	private String pin = "";

	/**
	 * 卡关联手机号
	 */
	@XmlElement(name = "cardPhone")
	private String cardPhone = "";

	@XmlTransient
	private String secretKey;

	/**
	 * 构造方法
	 * 
	 * @param BizReqVo
	 */
	public BankCardAuthReq(BizReqVo vo) {
		BankCardAuthReqVo bankCardAuthReqVo = (BankCardAuthReqVo) vo;

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(vo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());

		this.setAppSysId(channelInfo.getMerchantNo());
		this.setSignMethod("MD5"); // 签名方法:固定值：MD5

		this.setMobile(bankCardAuthReqVo.getMobile()); // 手机号

		// 借贷标志 0：借记卡 1：贷记卡 (固定为借记卡)
		this.setDcType(ThirdPPCacheContainer.tppBankCardTypeToThirdBankCardTypeMap
				.get(vo.getThirdType().getCode()
						+ bankCardAuthReqVo.getCardType()));

		this.setSecretKey(ConfigUtil.getInstance().getShunionpayConfig()
				.getSecretKey());
		this.setCardNo(bankCardAuthReqVo.getCardNo());
		this.setCertType(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo
				.getThirdType().getCode() + bankCardAuthReqVo.getCertType()));

		this.setUsrName(bankCardAuthReqVo.getUserName());
		this.setCertNo(bankCardAuthReqVo.getCertNo());

		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());

	}

	public BankCardAuthReq() {
		super();
	}

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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getValidWayId() {
		return validWayId;
	}

	public void setValidWayId(String validWayId) {
		this.validWayId = validWayId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String getDcType() {
		return dcType;
	}

	public void setDcType(String dcType) {
		this.dcType = dcType;
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

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getCardPhone() {
		return cardPhone;
	}

	public void setCardPhone(String cardPhone) {
		this.cardPhone = cardPhone;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public String encode() {
		JaxbBinder binder = new JaxbBinder(this.getClass());
		String xml = binder.toXml(this, "UTF-8");
		return xml.substring(xml.indexOf("\n") + 1);
	}

}

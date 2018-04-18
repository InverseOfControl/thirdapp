package com.zendaimoney.thirdpp.channel.dto.req.shunionpay.bankBin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BankCardBinQueryReqVO;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "BankCardBinQryReq")
public class BankCardBinQueryReq extends ReqDto {

	/**
	 * 应用系统编号
	 */
	@XmlElement(name = "appSysId")
	private String appSysId = "";

	/**
	 * 服务类型
	 */
	@XmlElement(name = "serviceType")
	private String serviceType = "1056";

	/**
	 * 签名方法
	 */
	@XmlElement(name = "signMethod")
	private String signMethod = "MD5";

	/**
	 * 签名信息
	 */
	@XmlElement(name = "signature")
	private String signature = "";

	/**
	 * 卡号
	 */
	@XmlElement(name = "cardNo", required = true)
	private String cardNo = "";

	/**
	 * 是否需要代扣银行编号
	 */
	@XmlElement(name = "needOraCardIssuer")
	private String needOraCardIssuer = "";

	/**
	 * 默认构造器
	 */
	public BankCardBinQueryReq() {
		super();
	}

	/**
	 * 请求对象转换
	 * 
	 * @param vo
	 */
	public BankCardBinQueryReq(BizReqVo vo) {
		BankCardBinQueryReqVO binQueryReqVO = (BankCardBinQueryReqVO) vo;

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(vo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());

		this.setAppSysId(channelInfo.getMerchantNo());
		this.setCardNo(binQueryReqVO.getCardNo());
		this.setNeedOraCardIssuer(binQueryReqVO.getNeedOraCardIssuer());

		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());
	}

	@Override
	public String encode() {
		JaxbBinder binder = new JaxbBinder(this.getClass());

		String xml = binder.toXml(this, "UTF-8");
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

	public String getNeedOraCardIssuer() {
		return needOraCardIssuer;
	}

	public void setNeedOraCardIssuer(String needOraCardIssuer) {
		this.needOraCardIssuer = needOraCardIssuer;
	}

}

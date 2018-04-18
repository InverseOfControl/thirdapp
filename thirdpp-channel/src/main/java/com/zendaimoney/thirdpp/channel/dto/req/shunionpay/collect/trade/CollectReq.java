package com.zendaimoney.thirdpp.channel.dto.req.shunionpay.collect.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.conf.shunionpay.ShunionpayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Shunionpay")
public class CollectReq extends ReqDto {


	/**
	 * 商户号 (必填){在收付捷平台中开通的商户编号}
	 */
	@XmlElement(name = "merId", required = true)
	private String merId = "";

	/**
	 * 商户日期(必须) {YYYYMMDD}
	 */
	@XmlElement(name = "transDate", required = true)
	private String transDate = "";

	/**
	 * 订单号(必须){商户号+订单号+商户日期唯一标示一笔交易}
	 */
	@XmlElement(name = "orderNo", required = true)
	private String orderNo = "";

	/**
	 * 交易类型(必须) {固定为“0003”}
	 */
	@XmlElement(name = "transType", required = true)
	private String transType = "";

	/**
	 * 开户行号(必须)
	 */
	@XmlElement(name = "openBankId", required = true)
	private String openBankId = "";

	/**
	 * 卡折标志(必须) {卡号或存折号标识位(0表示卡,1表示折)}
	 */
	@XmlElement(name = "cardType", required = true)
	private String cardType = "";

	/**
	 * 银行卡号或者存折号(必须)
	 */
	@XmlElement(name = "cardNo", required = true)
	private String cardNo = "";

	/**
	 * 持卡人姓名(必须){需以unicode传值}
	 */
	@XmlElement(name = "usrName", required = true)
	private String usrName = "";

	/**
	 * 证件类型(必须)
	 */
	@XmlElement(name = "certType", required = true)
	private String certType = "";

	/**
	 * 证件号(必须)
	 */
	@XmlElement(name = "certId", required = true)
	private String certId = "";

	/**
	 * 币种(必须){固定为156，表示货币单位为人民币}
	 */
	@XmlElement(name = "curyId", required = true)
	private String curyId = "";

	/**
	 * 整数，以分为单位(必须)
	 */
	@XmlElement(name = "transAmt", required = true)
	private String transAmt = "";

	/**
	 * 用途
	 */
	@XmlElement(name = "purpose")
	private String purpose = "";

	/**
	 * 私有域1{商户保留域，需以unicode传值}
	 */
	@XmlElement(name = "priv1")
	private String priv1 = "";

	/**
	 * 版本号(必须){固定为20100831}
	 */
	@XmlElement(name = "version", required = true)
	private String version = "";

	/**
	 * 网管号(必须){固定为7008}
	 */
	@XmlElement(name = "gateId", required = true)
	private String gateId = "";

	/**
	 * 签名值(必须)
	 */
	@XmlElement(name = "chkValue")
	private String chkValue = "";

	public CollectReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param collectReqVo
	 */
	public CollectReq(BizReqVo vo) {

		CollectReqVo collectReqVo = (CollectReqVo) vo;

		ShunionpayConfig chinapayConfig = ConfigUtil.getInstance()
				.getShunionpayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(collectReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setMerId(channelInfo.getMerchantNo());
		//
		this.setTransDate(CalendarUtils.getShortFormatNow());
		// 订单号
		this.setOrderNo(collectReqVo.getTradeFlow());
		// 交易类型
		this.setTransType(Constants.ShunionpayConstants.COLLECT_BUSINESS_CODE.getCode());
		// 开户行号
		this.setOpenBankId(ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap
				.get(vo.getThirdType().getCode()
						+ collectReqVo.getPayerBankCode()));
		// 卡号或存折号标识位 (0表示卡,1表示折)
		this.setCardType(ThirdPPCacheContainer.tppBankCardTypeToThirdBankCardTypeMap
				.get(vo.getThirdType().getCode()
						+ collectReqVo.getPayerBankCardType()));

		// 卡号/折号
		this.setCardNo(collectReqVo.getPayerBankCardNo());

		// 持卡人姓名 需以unicode传值
		this.setUsrName(ShunionpayUtil.stringToUnicode(collectReqVo
				.getPayerName()));

		// 证件类型 身份证01； 军官证02 ；护照03 ；户口簿04 ；回乡证05 ；其他06
		this.setCertType(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo
				.getThirdType().getCode() + collectReqVo.getPayerIdType()));
		// 证件号
		this.setCertId(collectReqVo.getPayerId());

		// 币种
		this.setCuryId(ThirdPPCacheContainer.tppCurrencyToThirdCurrencyMap
				.get(vo.getThirdType().getCode() + collectReqVo.getCurrency()));

		// 交易金额
		this.setTransAmt(ShunionpayUtil
				.yuanConvertFen(collectReqVo.getAmount()).toString());

		this.setPurpose(ShunionpayUtil.stringToUnicode(vo.getBizType()
				.getDesc()));
		this.setPriv1(ShunionpayUtil.stringToUnicode(vo.getBizSys().getDesc()));
		// 版本号
		this.setVersion(chinapayConfig.getVersion());

		// 网关
		this.setGateId(chinapayConfig.getGateId());

		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());

	}

	/**
	 * 对象转XML报文
	 * 
	 * @return
	 */
	public String encode() {
		JaxbBinder binder = new JaxbBinder(this.getClass());
		String xml = binder.toXml(this, "UTF-8");
		return xml.substring(xml.indexOf("\n") + 1);
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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOpenBankId() {
		return openBankId;
	}

	public void setOpenBankId(String openBankId) {
		this.openBankId = openBankId;
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

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
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

	public String getCuryId() {
		return curyId;
	}

	public void setCuryId(String curyId) {
		this.curyId = curyId;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPriv1() {
		return priv1;
	}

	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

}

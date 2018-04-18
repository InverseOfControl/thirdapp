package com.zendaimoney.thirdpp.channel.dto.req.shunionpay.pay.trade;

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
import com.zendaimoney.thirdpp.channel.pub.vo.PayReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;

/**
 * 融资业务请求数据传输对象
 * @author mencius
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Shunionpay")
public class PayReq extends ReqDto {
	
	/**
	 * 商户号(在收付捷平台中开通的商户编号)
	 */
	@XmlElement(name = "merId")
	private String merId;
	
	/**
	 * 商户日期(标志该笔交易发生的日期,格式为YYYYMMDD，请填写当天的日期。)
	 */
	@XmlElement(name = "merDate")
	private String merDate;
	
	/**
	 * 商户流水号(商户号+商户流水号+商户日期唯一标示一笔交易订单)
	 */
	@XmlElement(name = "merSeqId")
	private String merSeqId;
	
	/**
	 * 收款账号(银行卡号或者存折号)
	 */
	@XmlElement(name = "cardNo")
	private String cardNo;
	
	/**
	 * 收款人姓名(收款人在银行开户时留存的开户姓名)
	 */
	@XmlElement(name = "usrName")
	private String usrName;
	
	/**
	 * 开户银行名称
	 */
	@XmlElement(name = "openBank")
	private String openBank;
	
	/**
	 * 省份
	 */
	@XmlElement(name = "prov")
	private String prov;
	
	/**
	 * 城市
	 */
	@XmlElement(name = "city")
	private String city;
	
	/**
	 * 金额
	 */
	@XmlElement(name = "transAmt")
	private String transAmt;
	
	/**
	 * 用途
	 */
	@XmlElement(name = "purpose")
	private String purpose;
	
	/**
	 * 支行
	 */
	@XmlElement(name = "subBank")
	private String subBank;
	
	/**
	 * 付款标志(对公对私标记。“00”对私，“01”对公。该字段可以不填，如不填则默认为对私。)
	 */
	@XmlElement(name = "flag")
	private String flag;
	
	/**
	 * 版本号(固定为“20090501”)
	 */
	@XmlElement(name = "version")
	private String version = "20090501";
	
	/**
	 * 签名标志(固定为“1”)
	 */
	@XmlElement(name = "signFlag")
	private String signFlag;
	
	/**
	 * 签名值
	 */
	@XmlElement(name = "chkValue")
	private String chkValue;
	
	/**
	 * 默认构造器
	 */
	public PayReq() {
		super();
	}
	
	public PayReq(BizReqVo vo){
		PayReqVo payReqVo = (PayReqVo) vo;
		
		ShunionpayConfig chinapayConfig = ConfigUtil.getInstance()
				.getShunionpayConfig();
		
		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(payReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setMerId(channelInfo.getMerchantNo());
		
		this.setMerDate(CalendarUtils.getShortFormatNow());
		
		// 商户流水号
		this.setMerSeqId(payReqVo.getTradeFlow());
		
		// 收款人账户
		this.setCardNo(payReqVo.getReceiverBankCardNo());
		
		// 收款人姓名
		this.setUsrName(payReqVo.getReceiverName());
		
		// 开户名称
		this.setOpenBank(ThirdPPCacheContainer.tppBankCodeToThirdBankNameMap
				.get(payReqVo.getThirdType().getCode()
						+ payReqVo.getReceiverBankCode()));
		
		// 省份
		this.setProv("上海");
		
		// 城市
		this.setCity("上海");
		
		// 金额
		this.setTransAmt(ShunionpayUtil
				.yuanConvertFen(payReqVo.getAmount()).toString());
		
		// 用途
		this.setPurpose(vo.getBizType().getDesc());

		// 对公对私标记。“00”对私，“01”对公。该字段可以不填，如不填则默认为对私
		this.setFlag(ThirdPPCacheContainer.tppUserTypeTothirdUserTypeMap
				.get(payReqVo.getThirdType().getCode()
						+ payReqVo.getReceiverType()));
		
		// 固定为“20090501”
		this.setVersion(chinapayConfig.getPayVersion());
		
		// 固定为“1” 
		this.setSignFlag("1");
		
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
	@Override
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


	public String getOpenBank() {
		return openBank;
	}


	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}


	public String getProv() {
		return prov;
	}


	public void setProv(String prov) {
		this.prov = prov;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
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


	public String getSubBank() {
		return subBank;
	}


	public void setSubBank(String subBank) {
		this.subBank = subBank;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getSignFlag() {
		return signFlag;
	}


	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}


	public String getChkValue() {
		return chkValue;
	}


	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	
	

}

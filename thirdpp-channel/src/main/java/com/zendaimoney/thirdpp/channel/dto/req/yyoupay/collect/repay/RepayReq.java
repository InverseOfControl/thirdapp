package com.zendaimoney.thirdpp.channel.dto.req.yyoupay.collect.repay;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

/**
 * 还款业务请求数据传输对象
 * @author mencius
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "YYOU_REPAY_REQ")
public class RepayReq extends ReqDto {

//	/**
//	 * 第三方业务名称
//	 */
//	@XmlElement(name = "thirdBizName")
//	private String thirdBizName = "BID_REPAY_BASE";
	
	/**
	 * 交易类型
	 */
	@XmlElement(name = "dealType")
	private String dealType = "BID_REPAY_BASE";
	
	/**
	 * 子交易类型
	 */
	@XmlElement(name = "subDealType")
	private String subDealType = "";
	
	/**
	 * 畅捷通给p2p分配(证大商户号)
	 */
	@XmlElement(name = "tpCode")
	private String tpCode = "";
	
	/**
	 * 信贷端用户ID
	 */
	@XmlElement(name = "tpUserCode")
	private String tpUserCode= "";
	
	/**
	 * 信托ID
	 */
	@XmlElement(name = "tpUserCode2")
	private String tpUserCode2= "";
	
	/**
	 * 还款金额
	 */
	@XmlElement(name = "amount")
	private String amount = "";
	
	/**
	 * 交易日期
	 */
	@XmlElement(name = "date")
	private String date = "";
	
	/**
	 * 交易流水号
	 */
	@XmlElement(name = "flow")
	private String flow = "";
	
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
	
	/**
	 * 加密签名
	 */
	@XmlElement(name = "requestinfo")
	private String requestinfo;
	
	public RepayReq() {
		super();
	}
	
	public RepayReq(BizReqVo vo) {
		
		CollectReqVo collectReqVo = (CollectReqVo) vo;
		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(collectReqVo.getInfoCategoryCode());
				
		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		
		// 证大商户号
		this.setTpCode(channelInfo.getMerchantNo());
		
		// 信贷端用户ID
		this.setTpUserCode(collectReqVo.getPayerAccountNo());
		// 信托ID
		this.setTpUserCode2(collectReqVo.getReceiverAccountNo());
		// 还款金额
		this.setAmount(collectReqVo.getAmount().movePointRight(2).toString());
		// 子交易类型
		this.setSubDealType("002"); //子交易类型固定为 002 银行卡(001 账户)
		// 交易日期
		this.setDate(CalendarUtils.getFormatNow());
		// 交易流水号
		this.setFlow(collectReqVo.getTradeFlow());
		
		// 备注1
		this.setNote1(collectReqVo.getPayerName());
		
		// 备注2
		this.setNote2(collectReqVo.getPayerBankCardNo());
		
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

	public String getTpCode() {
		return tpCode;
	}

	public void setTpCode(String tpCode) {
		this.tpCode = tpCode;
	}

	public String getTpUserCode() {
		return tpUserCode;
	}

	public void setTpUserCode(String tpUserCode) {
		this.tpUserCode = tpUserCode;
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

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
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

	public String getRequestinfo() {
		return requestinfo;
	}

	public void setRequestinfo(String requestinfo) {
		this.requestinfo = requestinfo;
	}
	
}

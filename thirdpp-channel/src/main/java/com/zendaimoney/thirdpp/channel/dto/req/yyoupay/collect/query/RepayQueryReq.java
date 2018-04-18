package com.zendaimoney.thirdpp.channel.dto.req.yyoupay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
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
@XmlRootElement(name = "YYOU_QUERY_REQ")
public class RepayQueryReq extends ReqDto {

	/**
	 * 交易类型
	 */
	@XmlElement(name = "dealType")
	private String dealType = "QUERY_BASE";
	
	/**
	 * 子交易类型
	 */
	@XmlElement(name = "subDealType")
	private String subDealType = "002";
	
	
	/**
	 * 信贷端用户ID
	 */
	@XmlElement(name = "tpUserCode")
	private String tpUserCode= "";
	
	/**
	 * 畅捷通给p2p分配(证大商户号)
	 */
	@XmlElement(name = "tpCode")
	private String tpCode = "";
	
	/**
	 * CJZF系统id（可以不传）
	 */
	@XmlElement(name = "cjzfUserCode")
	private String cjzfUserCode= "";
	
	
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
	
	public RepayQueryReq() {
		super();
	}
	
	public RepayQueryReq(BizReqVo vo) {
		
		QueryReqVo queryReqVo = (QueryReqVo) vo;
		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(queryReqVo.getInfoCategoryCode());
				
		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		
		// 证大商户号
		this.setTpCode(channelInfo.getMerchantNo());
		
		// 信贷端用户ID
		this.setTpUserCode(queryReqVo.getPayerAccountNo());
		// 子交易类型
		this.setSubDealType("002"); //子交易类型固定为 002 银行卡(001 账户)
		// 交易流水号
		this.setFlow(queryReqVo.getTradeFlow());
		
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

	public String getCjzfUserCode() {
		return cjzfUserCode;
	}

	public void setCjzfUserCode(String cjzfUserCode) {
		this.cjzfUserCode = cjzfUserCode;
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

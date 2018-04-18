package com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "YyouRepayQueryResp")
public class RepayQueryResp extends RespDto {

	/**
	 * 交易类型
	 */
	@XmlElement(name = "dealType")
	private String dealType = "QUERY_BASE";
	
	/**
	 * 交易子类型
	 */
	@XmlElement(name = "subDealType")
	private String subDealType;
	
	/**
	 * 返回码
	 */
	@XmlElement(name = "retCode")
	private String retCode;
	
	/**
	 * 畅捷通给p2p分配
	 */
	@XmlElement(name = "tppUserCode")
	private String tppUserCode;
	
	/**
	 * CJZF系统id
	 */
	@XmlElement(name = "cjzfId")
	private String cjzfId;
	
	/**
	 * 
	 */
	@XmlElement(name = "flow")
	private String flow;
	
	/**
	 * CJZF流水日期
	 */
	@XmlElement(name = "tradeDate")
	private String tradeDate;
	
	/**
	 * 状态
	 */
	@XmlElement(name = "state")
	private String state;
	
	/**
	 * 交易金额
	 */
	@XmlElement(name = "amount")
	private String amount; 
	
	/**
	 * 发生方
	 */
	@XmlElement(name = "payer")
	private String payer;
	
	/**
	 * 接收方
	 */
	@XmlElement(name = "receiver")
	private String receiver;
	
	/**
	 * 附加信息
	 */
	@XmlElement(name = "retMessage")
	private String retMessage;
	
	
	public RepayQueryResp() {
		super();
	}
	@Override
	public RepayQueryResp decode(String respMsg) throws PlatformException {
		
		RepayQueryResp dto = new RepayQueryResp();
//		Map<String, String> valueMap = ShunionpayUtil.getResponseMap(respMsg, ChannelCategory.TRADE);
//		
//		dto = (RepayResp) JSONHelper.json2Object(JSONHelper.map2json(valueMap), dto.getClass());
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


	public String getTppUserCode() {
		return tppUserCode;
	}
	
	public void setTppUserCode(String tppUserCode) {
		this.tppUserCode = tppUserCode;
	}

	public String getCjzfId() {
		return cjzfId;
	}


	public void setCjzfId(String cjzfId) {
		this.cjzfId = cjzfId;
	}


	public String getRetCode() {
		return retCode;
	}


	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}


	public String getRetMessage() {
		return retMessage;
	}


	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	
}

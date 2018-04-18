package com.zendaimoney.thirdpp.channel.dto.resp.yyoupay.collect.repay;

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
@XmlRootElement(name = "YyouRepayResp")
public class RepayResp extends RespDto {

	/**
	 * 交易类型
	 */
	@XmlElement(name = "dealType")
	private String dealType = "BID_REPAY_BASE";
	
	/**
	 * 交易子类型
	 */
	@XmlElement(name = "subDealType")
	private String subDealType;
	
	/**
	 * 畅捷通给p2p分配
	 */
	@XmlElement(name = "tppCode")
	private String tppCode;
	
	/**
	 * P2P系统用户ID
	 */
	@XmlElement(name = "tpUserCode")
	private String tpUserCode;
	
	/**
	 * CJZF系统id
	 */
	@XmlElement(name = "cjzfId")
	private String cjzfId;
	
	/**
	 * 返回码
	 */
	@XmlElement(name = "retCode")
	private String retCode;
	
	/**
	 * 附加信息
	 */
	@XmlElement(name = "retMessage")
	private String retMessage;
	
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
	
	
	public RepayResp() {
		
	}
	@Override
	public RepayResp decode(String respMsg) throws PlatformException {
		
		RepayResp dto = new RepayResp();
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


	public String getTppCode() {
		return tppCode;
	}


	public void setTppCode(String tppCode) {
		this.tppCode = tppCode;
	}


	public String getTpUserCode() {
		return tpUserCode;
	}


	public void setTpUserCode(String tpUserCode) {
		this.tpUserCode = tpUserCode;
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
	
}

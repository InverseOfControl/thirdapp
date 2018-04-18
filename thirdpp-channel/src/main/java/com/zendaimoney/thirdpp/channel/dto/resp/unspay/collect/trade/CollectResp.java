package com.zendaimoney.thirdpp.channel.dto.resp.unspay.collect.trade;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "result")
public class CollectResp extends RespDto{

	/**
	 * 返回码
	 */
	@XmlElement(name = "resultCode")
	private String resultCode;

	/**
	 * 返回信息
	 */
	@XmlElement(name = "resultMsg")
	private String resultMsg;

	/**
	 * 基础信息错误订单
	 */
	@XmlElement(name = "errorOrder")
	private String errorOrder;


	

	@Override
	public CollectResp decode(String respMsg) throws PlatformException {
		
		CollectResp dto = new CollectResp();
		//Map<String, String> valueMap = BaofoopayUtil.getResponseMap(respMsg, ChannelCategory.TRADE);


		dto = (CollectResp) JSONHelper.json2Object(respMsg, dto.getClass());
		return dto;

		//JaxbBinder binder = new JaxbBinder(this.getClass());

		//return binder.fromXml(respMsg);


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


	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getErrorOrder() {
		return errorOrder;
	}

	public void setErrorOrder(String errorOrder) {
		this.errorOrder = errorOrder;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}

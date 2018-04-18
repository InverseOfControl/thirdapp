package com.zendaimoney.thirdpp.channel.dto.resp.zendaipay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ExceptionUtil;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

@XmlAccessorType(XmlAccessType.FIELD)
public class CollectQueryResp extends RespDto{
	
	/**
	 * 请求返回码
	 */
	@XmlElement(name = "code")
	private String code;
	
	/**
	 * 处理状态码
	 */
	@XmlElement(name = "resultCode")
	private String resultCode;
	
	/**
	 * 描述
	 */
	@XmlElement(name = "msg")
	private String msg;
	
	
	@Override
	public CollectQueryResp decode(String respMsg) throws PlatformException {
		
		CollectQueryResp dto = new CollectQueryResp();
		dto = (CollectQueryResp) JSONHelper.json2Object(respMsg, dto.getClass());
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

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return super.toString();
	}
}

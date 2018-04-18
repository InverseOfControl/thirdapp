package com.zendaimoney.thirdpp.channel.dto.resp.allinpay.collect.trade.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

/**
 * 通联代扣接口310011响应报文映射实体
 * 
 * @author ym10159
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class CollectResp extends RespDto {

	/**
	 * 返回头
	 */
	@XmlElement(name = "INFO")
	private RespHeader header;

	/**
	 * 返回体
	 */
	@XmlElement(name = "FASTTRXRET")
	private RespBody body;

	/**
	 * 返回XML报文转成CollectResp。
	 */
	@Override
	public CollectResp decode(String respMsg) {

		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);
	}

	public RespHeader getHeader() {
		return header;
	}

	public void setHeader(RespHeader header) {
		this.header = header;
	}

	public RespBody getBody() {
		return body;
	}

	public void setBody(RespBody body) {
		this.body = body;
	}
	

}

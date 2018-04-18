package com.zendaimoney.thirdpp.channel.dto.resp.allinpay.pay.query;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class PayQueryResp extends RespDto {

	/**
	 * 返回头
	 */
	@XmlElement(name = "INFO")
	private RespHeader header;

	/**
	 * 返回体
	 */
	@XmlElementWrapper(name="QTRANSRSP") 
    @XmlElement(name="QTDETAIL") 
	private List<RespBody> body;

	public RespHeader getHeader() {
		return header;
	}

	public void setHeader(RespHeader header) {
		this.header = header;
	}

    

	public List<RespBody> getBody() {
		return body;
	}

	public void setBody(List<RespBody> body) {
		this.body = body;
	}

	/**
	 * 返回XML报文转成CollectResp。
	 */
	@Override
	public PayQueryResp decode(String respMsg) {

		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);
	}

}

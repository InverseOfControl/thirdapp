package com.zendaimoney.thirdpp.channel.dto.resp.fuioupay.collect.query;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "qrytransrsp")
public class CollectQueryResp extends RespDto {

	/**
	 * 返回码
	 */
	@XmlElement(name = "ret")
	private String ret;

	/**
	 * 响应码描述
	 */
	@XmlElement(name = "memo")
	private String memo;

	/**
	 * 返回体
	 */
	@XmlElement(name = "trans")
	private List<RespBody> body;

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<RespBody> getBody() {
		return body;
	}

	public void setBody(List<RespBody> body) {
		this.body = body;
	}

	@Override
	public CollectQueryResp decode(String respMsg) {
		JaxbBinder binder = new JaxbBinder(this.getClass());

		return binder.fromXml(respMsg);
	}

}

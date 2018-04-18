package com.zendaimoney.thirdpp.channel.dto.resp.unspay.collect.query;

import com.zendaimoney.thirdpp.channel.dto.resp.RespDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.JSONHelper;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "result")
public class CollectQueryResp extends RespDto{

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
	 * 状态描述信息
	 */
	@XmlElement(name = "desc")
	private String desc;

	/**
	 * 订单状态
	 */
	@XmlElement(name = "status")
	private String status;

	/**
	 * 订单金额
	 */
	@XmlElement(name = "amount")
	private String amount;




	@Override
	public CollectQueryResp decode(String respMsg) throws PlatformException {

		CollectQueryResp dto = new CollectQueryResp();

		dto = (CollectQueryResp) JSONHelper.json2Object(respMsg, dto.getClass());

		return dto;


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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}

package com.zendaimoney.thirdpp.channel.dto.req.zendaipay.collect.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.conf.shunionpay.ShunionpayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;

/**
 * 代收业务请求数据传输对象
 * @author 00233197
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Zendaipay")
public class CollectReq extends ReqDto {

	/**
	 * 订单号(必须){商户号+订单号+商户日期唯一标示一笔交易}
	 */
	@XmlElement(name = "transNo", required = true)
	private String transNo = "";

	/**
	 * 账号(借款编号)(必须)
	 */
	@XmlElement(name = "borrowNo", required = true)
	private String borrowNo = "";

	/**
	 * 整数，以分为单位(必须)
	 */
	@XmlElement(name = "offerAmount", required = true)
	private String offerAmount = "";

	/**
	 * 签名值(必须)
	 */
	@XmlElement(name = "sign")
	private String sign = "";

	public CollectReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param collectReqVo
	 */
	public CollectReq(BizReqVo vo) {

		CollectReqVo collectReqVo = (CollectReqVo) vo;
		
		// 订单号
		this.setTransNo(collectReqVo.getTradeFlow());

		// 交易金额
		this.setOfferAmount(collectReqVo.getAmount().toString());
		
		// 账号
		this.setBorrowNo(collectReqVo.getPayerAccountNo());

		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());

	}

	/**
	 * 对象转XML报文
	 * 
	 * @return
	 */
	public String encode() {
		JaxbBinder binder = new JaxbBinder(this.getClass());
		String xml = binder.toXml(this, "UTF-8");
		return xml.substring(xml.indexOf("\n") + 1);
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getBorrowNo() {
		return borrowNo;
	}

	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}

	public String getOfferAmount() {
		return offerAmount;
	}

	public void setOfferAmount(String offerAmount) {
		this.offerAmount = offerAmount;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	


}

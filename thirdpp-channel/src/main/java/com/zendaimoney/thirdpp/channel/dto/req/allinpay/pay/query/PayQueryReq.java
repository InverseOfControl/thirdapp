package com.zendaimoney.thirdpp.channel.dto.req.allinpay.pay.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class PayQueryReq extends ReqDto {

	/**
	 * 请求头
	 */
	@XmlElement(name = "INFO")
	private ReqHeader header;

	/**
	 * 请求体
	 */
	@XmlElement(name = "QTRANSREQ")
	private ReqBody body;

	public PayQueryReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param queryReqVo
	 */
	public PayQueryReq(BizReqVo vo) {
		
		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(
				vo.getInfoCategoryCode()).getMerchantType();

		QueryReqVo queryReqVo = (QueryReqVo) vo;
		// 报文头
		header = new ReqHeader();
		header.setReqSn(queryReqVo.getTradeFlow());
		// 用户名需要根据不同的第三方支付通道编码和商户号类型进行判断
		header.setUserName(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
				vo.getThirdType().getCode() + merchantType).getUserName());
		// 密码需要根据不同的第三方支付通道编码和商户号类型进行判断
		header.setUserPass(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
				vo.getThirdType().getCode() + merchantType).getUserPwd());
		header.setVersion(ConfigUtil.getInstance().getAllinpayConfig()
				.getVersion());
		// 报文体
		body = new ReqBody();
		// 商户号需要根据不同的业务系统来判断
		body.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
				vo.getThirdType().getCode() + merchantType).getMerchantNo());
		//设置交易流水号
		body.setQuerySn(queryReqVo.getTradeFlow());

		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());
	}

	public ReqHeader getHeader() {
		return header;
	}

	public void setHeader(ReqHeader header) {
		this.header = header;
	}

	public ReqBody getBody() {
		return body;
	}

	public void setBody(ReqBody body) {
		this.body = body;
	}

	/**
	 * 对象转XML报文
	 * 
	 * @return
	 */
	public String encode() {
		JaxbBinder binder = new JaxbBinder(this.getClass());
		String xml = binder.toXml(this, "GBK");
		// System.out.println("===========" + xml);
		// binder.doValidate(xml, this.msgType.getCode());

		//return xml.substring(xml.indexOf("\n") + 1);
		//报文头中需要替换掉standalone="yes",这个需要根据具体情况来确定
		return xml.replaceFirst(JaxbBinder.REPLACE_STANDALONE, "");
	}
	
	public static void main(String args[]){
		ReqHeader header = new ReqHeader();
		ReqBody body = new ReqBody();
		PayQueryReq req = new PayQueryReq();
		req.setHeader(header);
		req.setBody(body);
		System.out.println(req.encode());
	}

}

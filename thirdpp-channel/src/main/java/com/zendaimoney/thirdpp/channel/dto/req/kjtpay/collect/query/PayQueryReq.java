package com.zendaimoney.thirdpp.channel.dto.req.kjtpay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.conf.kjtpay.KjtpayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
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
@XmlRootElement(name = "Kjtpay")
public class PayQueryReq extends ReqDto {

	/**
	 * 接口名称
	 */
	@XmlElement(name = "service", required = true)
	private String service = "query_fund_out";
	
	/**
	 * 商户网站唯一订单号
	 */
	@XmlElement(name = "outer_trade_no", required = true)
	private String outerTradeNo="";

	
	/**
	 * 快捷通交易号
	 */
	@XmlElement(name = "inner_trade_no", required = true)
	private String innerTradeNo="";
	
	/**
	 * 接口版本
	 */
	@XmlElement(name = "version", required = true)
	private String version = "";
	
	/**
	 * 商户号
	 */
	@XmlElement(name = "partner_id", required = true)
	private String partnerId="";
	
	/**
	 * 参数编码字符集
	 */
	@XmlElement(name = "_input_charset", required = true)
	private String _input_charset = "UTF-8";
	
	/**
	 * 签名
	 */
	@XmlElement(name = "sign", required = true)
	private String sign;
	
	/**
	 * 签名方式
	 */
	@XmlElement(name = "sign_type", required = true)
	private String signType = "ITRUSSRV";
	
	/**
	 * 页面跳转同步返回页面路径
	 */
	@XmlElement(name = "return_url", required = false)
	private String returnUrl="";
	
	/**
	 * 备注
	 */
	@XmlElement(name = "memo", required = false)
	private String memo="";
	

	public PayQueryReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param queryReqVo
	 */
	public PayQueryReq(BizReqVo vo) {
		
		QueryReqVo queryReqVo = (QueryReqVo) vo;
		KjtpayConfig chinapayConfig = ConfigUtil.getInstance().getKjtpayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap.get(queryReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode()+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setPartnerId(channelInfo.getMerchantNo());
		// 订单号
		this.setOuterTradeNo(queryReqVo.getTradeFlow());
		// 版本号
		this.setVersion(chinapayConfig.getVersion());

		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());
	}

	
	

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getOuterTradeNo() {
		return outerTradeNo;
	}

	public void setOuterTradeNo(String outerTradeNo) {
		this.outerTradeNo = outerTradeNo;
	}

	public String getInnerTradeNo() {
		return innerTradeNo;
	}

	public void setInnerTradeNo(String innerTradeNo) {
		this.innerTradeNo = innerTradeNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
	
//	public static void main(String args[]){
//		ReqHeader header = new ReqHeader();
//		ReqBody body = new ReqBody();
//		PayQueryReq req = new PayQueryReq();
//		req.setHeader(header);
//		req.setBody(body);
//		System.out.println(req.encode());
//	}

}

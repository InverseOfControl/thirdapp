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
 * @author 00233197
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Kjtpay")
public class CollectQueryReq extends ReqDto {

	/**
	 * 接口名称
	 */
	@XmlElement(name = "service", required = true)
	private String service = "query_trade";
	/**
	 * 接口版本
	 */
	@XmlElement(name = "version", required = true)
	private String version = "";
	/**
	 * 商户号
	 */
	@XmlElement(name = "partner_id", required = true)
	private String partner_id="";
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
	private String sign_type = "ITRUSSRV";
	/**
	 * 页面跳转同步返回页面路径
	 */
	@XmlElement(name = "return_url", required = false)
	private String return_url="";
	/**
	 * 备注
	 */
	@XmlElement(name = "memo", required = false)
	private String memo="";
	
	/**
	 * 商户网站唯一订单号
	 */
	@XmlElement(name = "outer_trade_no", required = true)
	private String outer_trade_no="";

	/**
	 * 快捷通交易号
	 */
	@XmlElement(name = "inner_trade_no", required = false)
	private String inner_trade_no="";

	public CollectQueryReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param collectReqVo
	 */
	public CollectQueryReq(BizReqVo vo) {

		QueryReqVo queryReqVo = (QueryReqVo) vo;
		
		KjtpayConfig chinapayConfig = ConfigUtil.getInstance()
				.getKjtpayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(queryReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setPartner_id(channelInfo.getMerchantNo());
		// 订单号
		this.setOuter_trade_no(queryReqVo.getTradeFlow());
		// 版本号
		this.setVersion(chinapayConfig.getVersion());

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

	public String getOuter_trade_no() {
		return outer_trade_no;
	}

	public void setOuter_trade_no(String outer_trade_no) {
		this.outer_trade_no = outer_trade_no;
	}

	public String getInner_trade_no() {
		return inner_trade_no;
	}

	public void setInner_trade_no(String inner_trade_no) {
		this.inner_trade_no = inner_trade_no;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
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

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}

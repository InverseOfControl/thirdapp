package com.zendaimoney.thirdpp.channel.dto.req.kjtpay.collect.trade;

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
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
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
public class CollectReq extends ReqDto {
	/**
	 * 接口名称
	 */
	@XmlElement(name = "service", required = true)
	private String service = "create_bank_withholding";
	/**
	 * 接口版本
	 */
	@XmlElement(name = "version", required = true)
	private String version = "";
	/**
	 * 商户号
	 */
	@XmlElement(name = "partner_id", required = true)
	private String partner_id = "";
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
	 * 商户网站唯一订单号,快捷通合作商户网站唯一订单号（确保在合作伙伴系统中唯一）
	 */
	@XmlElement(name = "outer_trade_no", required = true)
	private String outer_trade_no = "";

	/**
	 * 银行卡账户名,不支持~!！@#￥%^&*<>特殊字符
	 */
	@XmlElement(name = "user_name", required = true)
	private String user_name = "";

	/**
	 * 证件类型
	 */
	@XmlElement(name = "certificates_type", required = true)
	private String certificates_type = "";

	/**
	 * 证件号码
	 */
	@XmlElement(name = "certificates_number", required = true)
	private String certificates_number = "";

	/**
	 * 银行卡卡号
	 */
	@XmlElement(name = "card_no", required = true)
	private String card_no = "";

	/**
	 * 银行编码
	 */
	@XmlElement(name = "bank_code", required = true)
	private String bank_code = "";

	/**
	 * 交易金额 元
	 */
	@XmlElement(name = "payable_amount", required = true)
	private String payable_amount = "";

	/**
	 * 提交时间{数字串，一共14位，格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位] ，默认当期时间}
	 */
	@XmlElement(name = "submit_time", required = false)
	private String submit_time = "";

	/**
	 * 服务器异步通知页面路径
	 */
	@XmlElement(name = "notify_url", required = false)
	private String notify_url = "";

	//未加密的卡号
	@XmlElement(name = "cardNo_open", required = false)
	private String cardNoOpen = "";

	//未加密的身份证号
	@XmlElement(name = "certificatesNumber_open", required = false)
	private String certificatesNumberOpen = "";


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

		KjtpayConfig chinapayConfig = ConfigUtil.getInstance()
				.getKjtpayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(collectReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setPartner_id(channelInfo.getMerchantNo());
		//提交时间
		this.setSubmit_time(CalendarUtils.getFormatNow());
		// 订单号
		this.setOuter_trade_no(collectReqVo.getTradeFlow());
		// 开户行号
		this.setBank_code(ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap
				.get(vo.getThirdType().getCode()
						+ collectReqVo.getPayerBankCode()));
		// 卡号/折号 
		this.setCard_no(collectReqVo.getPayerBankCardNo());

		// 持卡人姓名 需以unicode传值
		this.setUser_name(collectReqVo.getPayerName());

		// 证件类型  1：身份证，2: 营业执照
		this.setCertificates_type(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo
				.getThirdType().getCode() + collectReqVo.getPayerIdType()));
		
		
		// 证件号
		this.setCertificates_number(collectReqVo.getPayerId());

		// 交易金额
		this.setPayable_amount(collectReqVo.getAmount().toString());
		// 版本号
		this.setVersion(chinapayConfig.getVersion());
		//未加密的卡号
		this.setCardNoOpen(collectReqVo.getPayerBankCardNo());
		//未加密的身份证号
		this.setCertificatesNumberOpen(collectReqVo.getPayerId());
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCertificates_type() {
		return certificates_type;
	}

	public void setCertificates_type(String certificates_type) {
		this.certificates_type = certificates_type;
	}

	public String getCertificates_number() {
		return certificates_number;
	}

	public void setCertificates_number(String certificates_number) {
		this.certificates_number = certificates_number;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getPayable_amount() {
		return payable_amount;
	}

	public void setPayable_amount(String payable_amount) {
		this.payable_amount = payable_amount;
	}

	public String getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
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

	public String getCardNoOpen() {
		return cardNoOpen;
	}

	public void setCardNoOpen(String cardNoOpen) {
		this.cardNoOpen = cardNoOpen;
	}

	public String getCertificatesNumberOpen() {
		return certificatesNumberOpen;
	}

	public void setCertificatesNumberOpen(String certificatesNumberOpen) {
		this.certificatesNumberOpen = certificatesNumberOpen;
	}

	
}

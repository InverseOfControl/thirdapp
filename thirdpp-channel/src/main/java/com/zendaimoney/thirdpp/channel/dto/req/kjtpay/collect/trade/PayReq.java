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
import com.zendaimoney.thirdpp.channel.pub.vo.PayReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

/**
 * 代付业务请求数据传输对象
 * 
 * @author 00233197
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Kjtpay")
public class PayReq extends ReqDto {
	
	
	/**
	 * 接口名称
	 */
	@XmlElement(name = "service", required = true)
	private String service = "create_payment_to_card";
	
	/**
	 * 商户网站唯一订单号
	 */
	@XmlElement(name = "outer_trade_no", required = true)
	private String outerTradeNo;
	
	/**
	 * 商户号
	 */
	@XmlElement(name = "partner_id", required = true)
	private String partnerId = "";
	
	/**
	 * 标识类型
	 */
	@XmlElement(name = "identity_type", required = true)
	private String identityType;
	
	/**
	 * 会员标识号
	 */
	@XmlElement(name = "identity_no", required = true)
	private String identityNo;
	
	/**
	 * 付款金额
	 */
	@XmlElement(name = "payable_amount", required = true)
	private String payableAmount;
	
	/**
	 * 账户类型
	 */
	@XmlElement(name = "account_type", required = true)
	private String accountType;
	
	/**
	 * 银行卡号  加密
	 */ 
	@XmlElement(name = "card_no", required = true)
	private String cardNo;
	
	/**
	 * 银行卡账户名 密文
	 */
	@XmlElement(name = "account_name", required = true)
	private String accountName;
	
	/**
	 * 银行编码
	 */
	@XmlElement(name = "bank_code", required = true)
	private String bankCode;
	
	
	/**
	 * 银行名称
	 */
	@XmlElement(name = "bank_name", required = true)
	private String bankName;
	
	
	/**
	 *  银行分支行名称
	 */
	@XmlElement(name = "branch_name", required = true)
	private String branchName;
	
	
	/**
	 * 银行分支行号
	 */
	@XmlElement(name = "bank_line_no", required = true)
	private String bankLineNo;
	
	
	/**
	 * 银行所在省
	 */
	@XmlElement(name = "bank_prov", required = true)
	private String bankProv;
	
	
	/**
	 * 银行所在市
	 */
	@XmlElement(name = "bank_city", required = true)
	private String bankCity;
	
	
	/**
	 * 对公/对私   对公/B, 对私/C
	 */
	@XmlElement(name = "company_or_personal", required = true)
	private String companyOrPersonal = "C";
	
	
	/**
	 * 到账级别   普通(0)，快速(1)
	 */
	@XmlElement(name = "fundout_grade", required = true)
	private String fundoutGrade = "0";
	
	
	/**
	 * 出款目的
	 */
	@XmlElement(name = "purpose", required = true)
	private String purpose;
	
	
	/**
	 * 提交时间    数字串，一共14位 格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位] ，默认当期时间
	 */
	@XmlElement(name = "submit_time", required = true)
	private String submitTime;
	
	/**
	 * 服务器异步通知页面路径   快捷通服务器主动通知商户网站里指定的页面http路径。
	 */
	@XmlElement(name = "notify_url", required = true)
	private String notifyUrl;
	
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
	 * 接口版本
	 */
	@XmlElement(name = "version", required = true)
	private String version = "";
	
	/**
	 * 构造方法
	 * 
	 * @param collectReqVo
	 */
	public PayReq(BizReqVo vo) {

		PayReqVo payReqVo = (PayReqVo) vo;
		
		KjtpayConfig chinapayConfig = ConfigUtil.getInstance()
				.getKjtpayConfig();
		
		//商户网站唯一订单号 
		this.setOuterTradeNo(payReqVo.getTradeFlow());
		
		// 根据信息类别编码去查询信息类别表
				SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
						.get(payReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
						.get(vo.getThirdType().getCode()
								+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setPartnerId(channelInfo.getMerchantNo());
		
		//付款金额
		this.setPayableAmount(payReqVo.getAmount().toString());
		
		//提交时间
		this.setSubmitTime(CalendarUtils.getFormatNow());
		
		//账户类型  可空
		this.setAccountType(payReqVo.getReceiverBankCardType());
		
		//卡号
		this.setCardNo(payReqVo.getReceiverBankCardNo());
		
		//银行卡姓名
		this.setAccountName(payReqVo.getReceiverName());
		
		//银行编码
		this.setBankCode(ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap
				.get(payReqVo.getThirdType().getCode()+payReqVo.getReceiverBankCode()));
		
		//银行名称
		this.setBankName(ThirdPPCacheContainer.tppBankCodeToThirdBankNameMap
				.get(payReqVo.getThirdType().getCode()+payReqVo.getReceiverBankCode()));
		
		//会员类型
		this.setIdentityType("1");
		
		
		// 商户类型
		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(payReqVo.getInfoCategoryCode()).getMerchantType();

		SysThirdChannelInfo thirdChannel = ThirdPPCacheContainer.sysThirdChannelInfoMap.get(payReqVo.getThirdType().getCode() + merchantType);
		//会员标识
//		this.setIdentityNo("kjt2015070959@kjtpay.com.cn");
		this.setIdentityNo(ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(payReqVo.getThirdType().getCode()+thirdChannel.getMerchantType()).getUserPwd());
		
		// 版本号
		this.setVersion(chinapayConfig.getVersion());
		
		//到账级别  可空
		this.setFundoutGrade("");
		
		//出账目的    可空
		this.setPurpose("");
		
		// 其他
		this.setBizSys(vo.getBizSys());
		this.setBizType(vo.getBizType());
		this.setThirdType(vo.getThirdType());
		this.setChannelReqId(vo.getChannelReqId());
		
		
	}

	public PayReq(){
		
	};

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


	public String getIdentityNo() {
		return identityNo;
	}


	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}


	public String getPayableAmount() {
		return payableAmount;
	}


	public void setPayableAmount(String payableAmount) {
		this.payableAmount = payableAmount;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getCardNo() {
		return cardNo;
	}


	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	public String getBankLineNo() {
		return bankLineNo;
	}


	public void setBankLineNo(String bankLineNo) {
		this.bankLineNo = bankLineNo;
	}


	public String getBankProv() {
		return bankProv;
	}


	public void setBankProv(String bankProv) {
		this.bankProv = bankProv;
	}

	public String getBankCity() {
		return bankCity;
	}


	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}


	public String getCompanyOrPersonal() {
		return companyOrPersonal;
	}


	public void setCompanyOrPersonal(String companyOrPersonal) {
		this.companyOrPersonal = companyOrPersonal;
	}


	public String getFundoutGrade() {
		return fundoutGrade;
	}


	public void setFundoutGrade(String fundoutGrade) {
		this.fundoutGrade = fundoutGrade;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public String getSubmitTime() {
		return submitTime;
	}


	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}


	public String getNotifyUrl() {
		return notifyUrl;
	}


	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
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


	
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	
	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public static void main(String args[]){
//		ReqHeader header = new ReqHeader();
//		ReqBody body = new ReqBody();
//		PayReq req = new PayReq();
//		header.setLevel("1");
//		req.setHeader(header);
//		req.setBody(body);
//		System.out.println(req.encode());
	}

}

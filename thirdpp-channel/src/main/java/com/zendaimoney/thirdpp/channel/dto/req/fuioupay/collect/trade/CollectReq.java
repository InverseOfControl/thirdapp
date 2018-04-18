package com.zendaimoney.thirdpp.channel.dto.req.fuioupay.collect.trade;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.fuioupay.FuioupayUtil;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "incomeforreq")
public class CollectReq extends ReqDto {

	/**
	 * 账户名称
	 */
	@XmlElement(name = "accntnm")
	private String accntnm = "";
	/**
	 * 总行代码
	 */
	@XmlElement(name = "bankno")
	private String bankno = "";
	/**
	 * 账号
	 */
	@XmlElement(name = "accntno")
	private String accntno = "";
	/**
	 * 证件类型(0身份证1护照2军官证3士兵证5户口本7其他)
	 */
	@XmlElement(name = "certtp")
	private String certtp = "";
	/**
	 * 证件号
	 */
	@XmlElement(name = "certno")
	private String certno = "";
	/**
	 * 金额
	 */
	@XmlElement(name = "amt")
	private String amt = "";
	/**
	 * 请求流水
	 */
	@XmlElement(name = "orderno")
	private String orderno = "";
	/**
	 * 备注
	 */
	@XmlElement(name = "memo")
	private String memo = "0";
	/**
	 * 手机号
	 */
	@XmlElement(name = "mobile")
	private String mobile = "";
	/**
	 * 版本号
	 */
	@XmlElement(name = "ver")
	private String ver = "";
	/**
	 * 企业流水号
	 */
	@XmlElement(name = "entseq")
	private String entseq = "";
	/**
	 * 请求日期
	 */
	@XmlElement(name = "merdt")
	private String merdt = "";
	@XmlTransient
	private String merchantId = "";


	
	/**
	 * 功能分类
	 */
	@XmlTransient
	private String functionCategory = "";

	public CollectReq() {
		super();
	}

	public String getAccntno() {
		return accntno;
	}

	public void setAccntno(String accntno) {
		this.accntno = accntno;
	}

	/**
	 * 构造方法
	 * 
	 * @param collectReqVo
	 */
	public CollectReq(BizReqVo vo) {

		CollectReqVo collectReqVo = (CollectReqVo) vo;
		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(
				vo.getInfoCategoryCode()).getMerchantType();

		// 报文体
		this.setAccntnm(collectReqVo.getPayerName());
		// 将TPP系统银行编码转化为相应第三方平台银行编码
		this.setBankno(ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap
				.get(vo.getThirdType().getCode()
						+ collectReqVo.getPayerBankCode()));
		this.setAccntno(collectReqVo.getPayerBankCardNo());
		this.setCerttp(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo
				.getThirdType().getCode() + collectReqVo.getPayerIdType()));
		this.setCertno(collectReqVo.getPayerId());
		this.setAmt(FuioupayUtil.yuanConvertFen(collectReqVo.getAmount())
				.toString());
		this.setOrderno(collectReqVo.getTradeFlow());
		// this.setMemo(collectReqVo.getMemo());
		this.setMobile(collectReqVo.getPayerMobile());
		this.setVer(ConfigUtil.getInstance().getFuioupayConfig().getVersion());
		// this.setEntseq(CalendarUtils.getFormatNow());
		this.setMerdt(CalendarUtils.getShortFormatNow());
		this.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
				vo.getThirdType().getCode() + merchantType).getMerchantNo());

		//设置功能分类
		this.setFunctionCategory(Constants.FuioupayConstrans.COLLECT_FUNCTION_CATEGORY.getCode());
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
	public String encode() throws PlatformException {
		JaxbBinder binder = new JaxbBinder(this.getClass());
		String xml = binder.toXml(this, "UTF-8");
		return xml;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getMerdt() {
		return merdt;
	}

	public void setMerdt(String merdt) {
		this.merdt = merdt;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	public String getEntseq() {
		return entseq;
	}

	public void setEntseq(String entseq) {
		this.entseq = entseq;
	}

	public String getAccntnm() {
		return accntnm;
	}

	public void setAccntnm(String accntnm) {
		this.accntnm = accntnm;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCerttp() {
		return certtp;
	}

	public void setCerttp(String certtp) {
		this.certtp = certtp;
	}

	public String getCertno() {
		return certno;
	}

	public void setCertno(String certno) {
		this.certno = certno;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}



	public String getFunctionCategory() {
		return functionCategory;
	}

	public void setFunctionCategory(String functionCategory) {
		this.functionCategory = functionCategory;
	}

    

}

package com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.trade.v2;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.time.DateFormatUtils;

import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.allinpay.AllinpayUtil;

/**
 * 通联代扣接口310011请求数据对象
 * 
 * @author ym10159
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "AIPG")
public class CollectReq extends ReqDto {

	/**
	 * 请求头
	 */
	@XmlElement(name = "INFO")
	private ReqHeader header;

	/**
	 * 请求体
	 */
	@XmlElement(name = "FASTTRX")
	private ReqBody body;

	public CollectReq() {
		super();
	}

	public CollectReq(BizReqVo vo,String agrmno) {
		CollectReqVo collectReqVo = (CollectReqVo) vo;
		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(vo.getInfoCategoryCode()).getMerchantType();
		
		// 报文头
		header = new ReqHeader();
		header.setLevel(ConfigUtil.getInstance().getAllinpayConfig().getLevel());
		header.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getMerchantNo());
		header.setUserName(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getUserName());
		// 密码需要根据不同的第三方支付通道编码和商户号类型进行判断
		header.setUserPass(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getUserPwd());
		header.setReqSn(collectReqVo.getTradeFlow());
		
		// 报文体
		body = new ReqBody();
		// 第三方的业务类型
		body.setBusinessCode(ConfigUtil.getInstance().getAllinpayConfig().getIncomeBusinessCode());
		body.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(vo.getThirdType().getCode() + merchantType).getMerchantNo());
		body.setSubmitTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		body.setAgrmno(agrmno);
		body.setAccountNo(collectReqVo.getPayerBankCardNo());
		body.setAccountName(collectReqVo.getPayerName());
		body.setAmount(AllinpayUtil.yuanConvertFen(collectReqVo.getAmount()).toString());
		body.setIdType(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo.getThirdType().getCode() + collectReqVo.getPayerIdType()));
		body.setId(collectReqVo.getPayerId());
		// 设置币种, 将TPP系统币种转化为相应第三方平台币种
		body.setCurrency(ThirdPPCacheContainer.tppCurrencyToThirdCurrencyMap.get(vo.getThirdType().getCode() + collectReqVo.getCurrency()));
		body.setId(collectReqVo.getPayerId());
		body.setTel(collectReqVo.getPayerMobile());

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
		String xml = binder.toXml(this, "GBK");
		//报文头中需要替换掉standalone="yes",这个需要根据具体情况来确定
		return xml.replaceFirst(JaxbBinder.REPLACE_STANDALONE, "");
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

}

package com.zendaimoney.thirdpp.channel.dto.req.baofoopay.collect.trade;

import com.zendaimoney.thirdpp.channel.conf.baofoopay.BaofoopayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.*;
import com.zendaimoney.thirdpp.channel.util.baofoopay.BaofoopayUtil;

import javax.xml.bind.annotation.*;

/**
 * 代收业务请求数据传输对象
 *
 * @author 00233197
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Baofoopay")
public class CollectReq extends ReqDto {


	/**
	 * 商户号 (必填){在收付捷平台中开通的商户编号}
	 */
	@XmlElement(name = "member_id", required = true)
	private String merId = "";


	/**
	 * 终端号(必须){商户号+订单号+商户日期唯一标示一笔交易}
	 */
	@XmlElement(name = "terminal_id", required = true)
	private String terminalId = "";

	/**
	 * 交易类型(必须) {固定为“0431”}
	 */
	@XmlElement(name = "txn_type", required = true)
	private String txnType = "0431";

	/**
	 * 交易子类型(必须) {固定为“13”}
	 */
	@XmlElement(name = "txn_sub_type", required = true)
	private String txnSubType = "13";

	/**
	 * 加密数据类型(必须)
	 */
	@XmlElement(name = "data_type", required = true)
	private String dataType = "xml";

	/**
	 * 加密数据
	 */
	@XmlElement(name = "sign", required = true)
	private String sign = "";


	/**
	 * 版本号(必须){4.0.0.0}
	 */
	@XmlElement(name = "version", required = true)
	private String version = "";

	@XmlElement(name = "data_content", required = true)
	private DataContent dataContent;


	public CollectReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param vo
	 */
	public CollectReq(BizReqVo vo) {

		CollectReqVo collectReqVo = (CollectReqVo) vo;

		BaofoopayConfig baofooConfig = ConfigUtil.getInstance().getBaofoopayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(collectReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setMerId(channelInfo.getMerchantNo());
		// 终端号
		this.setTerminalId(channelInfo.getUserName());
		this.setTxnSubType("13");
		this.setTxnType("0431");

		// 版本号
		this.setVersion(baofooConfig.getVersion());

		DataContent content = new DataContent();
		// 交易类型
		content.setTxnSubType("13");
		// 终端号
		content.setTerminalId(channelInfo.getUserName());
		// 商户号
		content.setMemberId(channelInfo.getMerchantNo());
		// 银行编码
		content.setPayCode(ThirdPPCacheContainer.tppBankCodeToThirdBankCodeMap
				.get(vo.getThirdType().getCode()
						+ collectReqVo.getPayerBankCode()));
		content.setPayCm("1");
		content.setAccNo(collectReqVo.getPayerBankCardNo());
		content.setIdCardType(ThirdPPCacheContainer.tppIdTypeToThirdIdTypeMap.get(vo
				.getThirdType().getCode() + collectReqVo.getPayerIdType()));
		content.setIdCard(collectReqVo.getPayerId());
		content.setIdHolder(collectReqVo.getPayerName());
		content.setTransId(collectReqVo.getTradeFlow());
		content.setMobile("13000000000");//collectReqVo.getPayerMobile()
		content.setValidNo("");
		content.setValidDate("");
		content.setAdditionalInfo("");
		content.setReqReserved("");

		content.setTxnAmt(BaofoopayUtil
				.yuanConvertFen(collectReqVo.getAmount()).toString());
		content.setTradeDate(CalendarUtils.getFormatNow());
		content.setTransSerialNo(collectReqVo.getChannelReqId());
		this.setDataContent(content);

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

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public DataContent getDataContent() {
		return dataContent;
	}

	public void setDataContent(DataContent dataContent) {
		this.dataContent = dataContent;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}

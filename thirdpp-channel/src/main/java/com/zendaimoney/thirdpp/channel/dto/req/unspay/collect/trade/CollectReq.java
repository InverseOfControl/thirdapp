package com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.trade;

import com.zendaimoney.thirdpp.channel.conf.baofoopay.BaofoopayConfig;
import com.zendaimoney.thirdpp.channel.conf.unspay.UnspayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.trade.DataContent;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.CollectReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
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
@XmlRootElement(name = "Unspay")
public class CollectReq extends ReqDto {


	/**
	 * 商户号 (必填){在银生宝平台中开通的商户编号}
	 */
	@XmlElement(name = "accountId", required = true)
	private String accountId = "";


	/**
	 * 批次号(必须)
	 */
	@XmlElement(name = "batchNo", required = true)
	private String batchNo = "";

	/**
	 * 总笔数(必须)
	 */
	@XmlElement(name = "totalNum", required = true)
	private String totalNum = "";

	/**
	 * 总金额(必须)
	 */
	@XmlElement(name = "totalAmount", required = true)
	private String totalAmount = "";

	/**
	 * 请求时间(必须)
	 */
	@XmlElement(name = "reqTime", required = true)
	private String reqTime = "";

	/**
	 * 版本号
	 */
	@XmlElement(name = "version", required = true)
	private String version = "1.0";

	/**
	 * 订单数据
	 */
	@XmlElement(name = "data", required = true)
	private String data = "";


	/**
	 * 数字签名(必须)
	 */
	@XmlElement(name = "mac", required = true)
	private String mac = "";

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

		UnspayConfig unspayConfig = ConfigUtil.getInstance().getUnspayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(collectReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setAccountId(channelInfo.getMerchantNo());
		this.setBatchNo(collectReqVo.getTradeFlow());
		this.setTotalNum("1");
		this.setTotalAmount(collectReqVo.getAmount().toString());
		this.setReqTime(CalendarUtils.getFormatNow());
		this.setVersion(unspayConfig.getVersion());

		DataContent content = new DataContent();
		content.setAccNo(collectReqVo.getPayerBankCardNo());
		content.setAccName(collectReqVo.getPayerName());
		content.setAmount(collectReqVo.getAmount().toString());
		content.setIdCardNo(collectReqVo.getPayerId());
		content.setOrderId(collectReqVo.getTradeFlow());
		content.setPhoneNo("13000000000");
		if(collectReqVo.getPayerName().equals("张三")){
			content.setPurpose("00");
		}else if(collectReqVo.getPayerName().equals("张四")){
			content.setPurpose("10");
		}else if(collectReqVo.getPayerName().equals("张五")){
			content.setPurpose("99");
		}else{
			content.setPurpose("代扣");
		}


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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public DataContent getDataContent() {
		return dataContent;
	}

	public void setDataContent(DataContent dataContent) {
		this.dataContent = dataContent;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}

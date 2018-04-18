package com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.query;

import com.zendaimoney.thirdpp.channel.conf.baofoopay.BaofoopayConfig;
import com.zendaimoney.thirdpp.channel.conf.unspay.UnspayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.dto.req.baofoopay.collect.query.QueryDataContent;
import com.zendaimoney.thirdpp.channel.dto.req.unspay.collect.trade.DataContent;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;

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
public class CollectQueryReq extends ReqDto {


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
	 * 订单号(必须)
	 */
	@XmlElement(name = "orderId", required = true)
	private String orderId = "";


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
	 * 数字签名(必须)
	 */
	@XmlElement(name = "mac", required = true)
	private String mac = "";


	public CollectQueryReq() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param vo
	 */
	public CollectQueryReq(BizReqVo vo) {

		QueryReqVo queryReqVo = (QueryReqVo) vo;

		UnspayConfig unspayConfig = ConfigUtil.getInstance().getUnspayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(queryReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());

		// 商户号 (从通道信息对象中获取 merchantNo-商户ID)
		this.setAccountId(channelInfo.getMerchantNo());
		this.setVersion(unspayConfig.getVersion());
		this.setOrderId(queryReqVo.getTradeFlow());
		this.setBatchNo(queryReqVo.getTradeFlow());

		try {
			// 订单日期
			queryReqVo.setOrderDate(CalendarUtils.parsefomatCalendar(CalendarUtils.parseCalendar(queryReqVo.getOrderDate(), CalendarUtils.LONG_FORMAT_LINE), CalendarUtils.LONG_FORMAT));
		} catch (PlatformException e) {
			// 订单日期，如果未传入则初始化为系统日期
			queryReqVo.setOrderDate(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT));
		}

		this.setReqTime(queryReqVo.getOrderDate());


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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
}

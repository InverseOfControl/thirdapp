package com.zendaimoney.thirdpp.channel.dto.req.shunionpay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.zendaimoney.thirdpp.channel.conf.shunionpay.ShunionpayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.JaxbBinder;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.channel.util.shunionpay.ShunionpayUtil;

/**
 * 代收业务请求数据传输对象
 * 
 * @author 00231257
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document")
@XmlRootElement(name = "Shunionpay")
public class CollectQueryReq extends ReqDto {


	/**
	 * 商户号 (必填){在收付捷平台中开通的商户编号}
	 */
	@XmlElement(name = "merId", required = true)
	private String merId;

	/**
	 * 交易类型(必须) {固定为“0003”}
	 */
	@XmlElement(name = "transType", required = true)
	private String transType;

	/**
	 * 订单号(必须){商户号+订单号+商户日期唯一标示一笔交易}
	 */
	@XmlElement(name = "orderNo", required = true)
	private String orderNo;

	/**
	 * 商户日期(必须) {YYYYMMDD}
	 */
	@XmlElement(name = "transDate", required = true)
	private String transDate;

	/**
	 * 私有域1{商户保留域，需以unicode传值}
	 */
	@XmlElement(name = "priv1")
	private String priv1;

	/**
	 * 版本号(必须){固定为20100831}
	 */
	@XmlElement(name = "version", required = true)
	private String version;

	/**
	 * 签名值(必须)
	 */
	@XmlElement(name = "chkValue")
	private String chkValue;

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

		ShunionpayConfig shunionpayConfig = ConfigUtil.getInstance()
				.getShunionpayConfig();

		// 根据信息类别编码去查询信息类别表
		SysInfoCategory infoCategory = ThirdPPCacheContainer.sysInfoCategoryMap
				.get(queryReqVo.getInfoCategoryCode());

		// 根据通道编号 + 商户类型 取得通道信息对象
		SysThirdChannelInfo channelInfo = ThirdPPCacheContainer.sysThirdChannelInfoMap
				.get(vo.getThirdType().getCode()
						+ infoCategory.getMerchantType());
		// 商户号 (从通道信息对象中获取 UserName-商户ID)
		this.setMerId(channelInfo.getMerchantNo());
		try {
			// 订单日期
			queryReqVo.setOrderDate(CalendarUtils.parsefomatCalendar(CalendarUtils.parseCalendar(queryReqVo.getOrderDate(), CalendarUtils.LONG_FORMAT_LINE), CalendarUtils.SHORT_FORMAT));
		} catch (PlatformException e) {
			// 订单日期，如果未传入则初始化为系统日期
			queryReqVo.setOrderDate(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT));
		}
		// 订单日期
		this.setTransDate(queryReqVo.getOrderDate());
		// 订单号
		this.setOrderNo(queryReqVo.getTradeFlow());
		// 交易类型
		this.setTransType(Constants.ShunionpayConstants.COLLECT_BUSINESS_CODE.getCode());

		// 私有域
		this.setPriv1(ShunionpayUtil.stringToUnicode(""));
		// 版本号
		this.setVersion(shunionpayConfig.getVersion());

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

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getPriv1() {
		return priv1;
	}

	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

}

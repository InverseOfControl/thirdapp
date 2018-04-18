package com.zendaimoney.thirdpp.channel.dto.req.fuioupay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.thirdpp.channel.conf.fuioupay.FuioupayConfig;
import com.zendaimoney.thirdpp.channel.dto.req.ReqDto;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
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
@XmlRootElement(name = "qrytransreq")
public class CollectQueryReq extends ReqDto {

	/**
	 * 版本号
	 */
	@XmlElement(name = "ver", required = true)
	private String ver = "";

	/**
	 * 业务代码(AC01代收 AP01代付)
	 */
	@XmlElement(name = "busicd", required = true)
	private String bussinessCode = "";

	/**
	 * 原请求流水
	 */
	@XmlElement(name = "orderno", required = true)
	private String orderno = "";

	/**
	 * 开始日期.时间格式:yyyyMMdd
	 */
	@XmlElement(name = "startdt", required = true)
	private String startdt = "";

	/**
	 * 结束日期，时间格式:yyyyMMdd
	 */
	@XmlElement(name = "enddt")
	private String enddt = "";

	/**
	 * 交易状态(0交易未发送1交易已发送且成功2交易已发送且失败3交易发送中7交易已发送且超时)
	 * 一般不建议填写
	 */
	@XmlElement(name = "transst", required = true)
	private String transst;
	
	
	@XmlTransient
	private String merchantId = "";

	/**
	 * 功能分类
	 */
	@XmlTransient
	private String functionCategory = "";
	
	


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

		FuioupayConfig fuioupayConfig = ConfigUtil.getInstance()
				.getFuioupayConfig();
		
		String merchantType = ThirdPPCacheContainer.sysInfoCategoryMap.get(
				vo.getInfoCategoryCode()).getMerchantType();


		this.setVer(fuioupayConfig.getVersion());
		this.setBussinessCode(Constants.FuioupayConstrans.COLLECT_BUSINESS_CODE.getCode());
		this.setMerchantId(ThirdPPCacheContainer.sysThirdChannelInfoMap.get(
				vo.getThirdType().getCode() + merchantType).getMerchantNo());
		
		this.setOrderno(queryReqVo.getTradeFlow());
		
		this.setFunctionCategory(Constants.FuioupayConstrans.QRYTRANS_FUNCTION_CATEGORY.getCode());
		
		try {
			
			// 订单日期
			queryReqVo.setOrderDate(CalendarUtils.parsefomatCalendar(CalendarUtils.parseCalendar(queryReqVo.getOrderDate(), CalendarUtils.LONG_FORMAT_LINE), CalendarUtils.SHORT_FORMAT));
		} catch (PlatformException e) {
			// 订单日期，如果未传入则初始化为系统日期
			queryReqVo.setOrderDate(CalendarUtils.getFormatNow(CalendarUtils.SHORT_FORMAT));
		}
		
		this.setStartdt(queryReqVo.getOrderDate());
		this.setEnddt(queryReqVo.getOrderDate());

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
		//return xml.substring(xml.indexOf("\n") + 1);
		return xml;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getStartdt() {
		return startdt;
	}

	public void setStartdt(String startdt) {
		this.startdt = startdt;
	}

	public String getEnddt() {
		return enddt;
	}

	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}

	public String getTransst() {
		return transst;
	}

	public void setTransst(String transst) {
		this.transst = transst;
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

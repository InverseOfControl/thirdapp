package com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReqBody {

	/**
	 * 商户代码
	 */
	@XmlElement(name = "MERCHANT_ID")
	private String merchantId = "";

	/**
	 * 要查询的交易流水
	 */
	@XmlElement(name = "QUERY_SN")
	private String querySn = "";

	/**
	 * 交易状态条件, 0成功,1失败, 2全部,3退票
	 */
	@XmlElement(name = "STATUS")
	private String status = "2";

	/**
	 * 0.按完成日期1.按提交日期，默认为1
	 */
	@XmlElement(name = "TYPE")
	private String type = "1";

	/**
	 * 若不填QUERY_SN则必填,格式:YYYYMMDDHHmmss
	 */
	@XmlElement(name = "START_DAY")
	private String startDay;

	/**
	 * 填了开始时间必填,格式:YYYYMMDDHHmmss
	 */
	@XmlElement(name = "END_DAY")
	private String endDay;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		if (merchantId != null) {
			this.merchantId = merchantId;
		}
	}

	public String getQuerySn() {
		return querySn;
	}

	public void setQuerySn(String querySn) {
		if (querySn != null) {
			this.querySn = querySn;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status != null) {
			this.status = status;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type != null) {
			this.type = type;
		}
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		if (startDay != null) {
			this.startDay = startDay;
		}
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		if (endDay != null) {
			this.endDay = endDay;
		}
	}

}

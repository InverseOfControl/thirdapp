package com.zendaimoney.thirdpp.channel.dto.req.allinpay.collect.query.v2;

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

}

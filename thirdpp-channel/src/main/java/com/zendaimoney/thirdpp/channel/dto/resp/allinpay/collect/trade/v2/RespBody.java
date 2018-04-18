package com.zendaimoney.thirdpp.channel.dto.resp.allinpay.collect.trade.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 通联代扣接口310011响应报文体映射实体
 * 
 * @author gaohx
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RespBody {
	/**
	 * 返回码
	 */
	@XmlElement(name = "RET_CODE")
	private String retCode;

	/**
	 * 清算日期
	 */
	@XmlElement(name = "SETTLE_DAY")
	private String settleDay;

	/**
	 * 错误文本
	 */
	@XmlElement(name = "ERR_MSG")
	private String errMsg;
	
	/**
	 * 卡号后4位
	 */
	@XmlElement(name = "ACCT_SUFFIX")
	private String acctSuffix;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getSettleDay() {
		return settleDay;
	}

	public void setSettleDay(String settleDay) {
		this.settleDay = settleDay;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getAcctSuffix() {
		return acctSuffix;
	}

	public void setAcctSuffix(String acctSuffix) {
		this.acctSuffix = acctSuffix;
	}

}

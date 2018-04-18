package com.zendaimoney.thirdpp.trade.pub.vo.resp.query;

import java.io.Serializable;
import java.math.BigDecimal;

public class ThirdPartyPayPlatformSupportBankInfoRespVo implements Serializable {

	private static final long serialVersionUID = -5336545407802481347L;
	
	// 状态 (0-银行通道关闭,1-银行通道开启)
	private int status;

	// 支付通道代收业务银行最高限额
	private BigDecimal collectMaxMoney;
	
	// 支付通道代付业务银行最高限额
	private BigDecimal payMaxMoney;
	
	// 支付通道快捷业务银行最高限额
	private BigDecimal quickPayMaxMoney;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getCollectMaxMoney() {
		return collectMaxMoney;
	}

	public void setCollectMaxMoney(BigDecimal collectMaxMoney) {
		this.collectMaxMoney = collectMaxMoney;
	}

	public BigDecimal getPayMaxMoney() {
		return payMaxMoney;
	}

	public void setPayMaxMoney(BigDecimal payMaxMoney) {
		this.payMaxMoney = payMaxMoney;
	}

	public BigDecimal getQuickPayMaxMoney() {
		return quickPayMaxMoney;
	}

	public void setQuickPayMaxMoney(BigDecimal quickPayMaxMoney) {
		this.quickPayMaxMoney = quickPayMaxMoney;
	}

}

package com.zendaimoney.trust.channel.pub.enums;

/**
 * 资金托管通道 请求类别
 * @author mencius
 *
 */
public enum TrustCategory {

	TRADE("01","交易指令下发"),
    QUERY("02","交易状态查询指令下发"),
    BATCH_TRADE("03","批量交易指令下发"),
    BATCH_QUERY("04","批量交易状态查询指令下发"),
	;
	private final String code;

	private final String desc;

	private TrustCategory(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static TrustCategory get(String code) {
		for (TrustCategory cmbCategory : TrustCategory.values()) {
			if (cmbCategory.getCode().equals(code))
				return cmbCategory;
		}
		throw new IllegalArgumentException("cmbCategory is not exist : "
				+ code);
	}
}

package com.zendaimoney.thirdpp.common.enums;

public enum ThirdType {
	
	/** 通联 支付*/
	ALLINPAY("0", "通联支付"),
	
	/** 富友支付 */
	FUIOUPAY("2", "富友支付"),
	/** 上海银联支付 */
	SHUNIONPAY("4", "上海银联支付"),
	
	/** 用友支付 */
	YONGYOUUNIONPAY("6", "用友支付"),
	
	/** 上海银联支付-实名验证 */
	SHUNIONPAY_ACCOUNT_AUTH("8", "上海银联支付-实名认证"),
	
	/** 证大爱特*/
	ZENDAIPAY("10","证大爱特支付"),
	
	CMBPAY("12", "招商银行"),
	
	KFTPAY("14","快付通支付"),
	
	IFREPAY("16","数信支付"),
	
	KJTPAY("18","快捷通支付"),

	BAOFOOPAY("20","宝付支付"),

	UNSPAY("22","银生宝支付"),

	ALLINPAY2("100", "通联支付2"),
	
	ROUTEPAY("999","默认路由规则")
	;
	private final String code;
	
	
	private final String desc;

	private ThirdType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static ThirdType get(String code) {
		for (ThirdType thirdType : ThirdType.values()) {
			if (thirdType.getCode().equals(code))
				return thirdType;
		}
		throw new IllegalArgumentException("thirdType is not exist : " + code);
	}

}

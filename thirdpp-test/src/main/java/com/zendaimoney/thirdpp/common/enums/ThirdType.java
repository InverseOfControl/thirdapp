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

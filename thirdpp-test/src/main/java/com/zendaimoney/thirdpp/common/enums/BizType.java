package com.zendaimoney.thirdpp.common.enums;


public enum BizType {
	
	BROKER_COLLECT("001","居间人模式代收"),
	BROKER_PAY("002","居间人模式代付"),
	TRUST_FINANCE("003", "资金托管融资"),
	TRUST_REFUND("004","资金托管还款"),
	TRUST_OPEN_ACCOUNT("005","资金托管开户"),
	TRUST_BIND_CARD("006","资金托管绑卡"),
	TRUST_OPEN_ACCOUNT_BIND_CARD("007","资金托管开户绑卡"),
	TRUST_RECHARGE("008","资金托管充值"),
	TRUST_WITHDRWA("009","资金托管提现"),
	CERTIFICATION("010","实名认证"),
	ONLINE_RECHARGE("011","线上充值"),
	ONLINE_WITHDRWA("012","线上提现"),
	ONLINE_PAY("013","线上支付"),
	ONLINE_REFUND("014","线上退款"),
	TRUST_TRANSFER("015","资金托管转账"),
	;
	private final String code;
	
	
	private final String desc;

	private BizType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static BizType get(String code) {
		for (BizType bizType : BizType.values()) {
			if (bizType.getCode().equals(code))
				return bizType;
		}
		throw new IllegalArgumentException("bizType is not exist : " + code);
	}

}

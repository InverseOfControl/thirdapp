package com.zendaimoney.thirdpp.common.enums;

/**
 * 通道类别
 * 
 * @author 00231257
 *
 */
public enum ChannelCategory {
	
	
    TRADE("01","交易指令下发"),
    QUERY("02","交易状态查询指令下发"),
    AUTH("03","实名认证指令下发"),
    BATCH_TRADE("04","批量交易指令下发"),
    BATCH_QUERY("05","批量交易状态查询指令下发"),
    OPEN_ACCOUNT("06","开户"),
	SIGN_MESSAGE("07", "签约短信"),
	SIGN("08", "签约")
	;
	private final String code;

	private final String desc;

	private ChannelCategory(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static ChannelCategory get(String code) {
		for (ChannelCategory channelCategory : ChannelCategory.values()) {
			if (channelCategory.getCode().equals(code))
				return channelCategory;
		}
		throw new IllegalArgumentException("channelCategory is not exist : "
				+ code);
	}

}

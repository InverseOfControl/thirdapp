package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 对账渠道状态
 * @author srainsk
 *
 */
public enum AccountChannelStatusEnum {
	/**
	 *  关闭
	 */
	CLOSE("0"),
	/**
	 *  开启
	 */
	OPEN("1"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountChannelStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountChannelStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountChannelStatusEnum, String>(AccountChannelStatusEnum.class);
    	map.put(AccountChannelStatusEnum.OPEN , "开启");
    	map.put(AccountChannelStatusEnum.CLOSE , "关闭");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountChannelStatusEnum getEnum(String value) {
    	AccountChannelStatusEnum tmpKey = null;
        for (AccountChannelStatusEnum tmpEnum : AccountChannelStatusEnum.values()) {
            if (tmpEnum.value.equals(value)) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }
    /**
     * 返回对应的描述.
     * @param value int.
     * @return String
     */
    public static String getEnumDesc(final String value) {
        return AccountChannelStatusEnum.map.get(AccountChannelStatusEnum.getEnum(value));
    }
}

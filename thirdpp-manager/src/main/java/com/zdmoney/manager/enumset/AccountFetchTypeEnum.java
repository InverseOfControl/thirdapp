package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 获得对账文件方式
 * @author srainsk
 *
 */
public enum AccountFetchTypeEnum {
	/**
	 *  主动请求
	 */
	REQUEST("0"),
	/**
	 *  对方推送
	 */
	RECEIVE("1"),
	/**
	 *  手动下载
	 */
	MANUAL("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountFetchTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountFetchTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountFetchTypeEnum, String>(AccountFetchTypeEnum.class);
    	map.put(AccountFetchTypeEnum.REQUEST , "主动请求");
    	map.put(AccountFetchTypeEnum.RECEIVE , "对方推送");
    	map.put(AccountFetchTypeEnum.MANUAL , "手动下载");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountFetchTypeEnum getEnum(String value) {
    	AccountFetchTypeEnum tmpKey = null;
        for (AccountFetchTypeEnum tmpEnum : AccountFetchTypeEnum.values()) {
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
        return AccountFetchTypeEnum.map.get(AccountFetchTypeEnum.getEnum(value));
    }
}

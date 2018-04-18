package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 业务系统配置状态
 * @author srainsk
 *
 */
public enum AccountBizsysConfigStatusEnum {
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
    
    AccountBizsysConfigStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountBizsysConfigStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountBizsysConfigStatusEnum, String>(AccountBizsysConfigStatusEnum.class);
    	map.put(AccountBizsysConfigStatusEnum.CLOSE , "关闭");
    	map.put(AccountBizsysConfigStatusEnum.OPEN , "开启");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountBizsysConfigStatusEnum getEnum(String value) {
    	AccountBizsysConfigStatusEnum tmpKey = null;
        for (AccountBizsysConfigStatusEnum tmpEnum : AccountBizsysConfigStatusEnum.values()) {
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
        return AccountBizsysConfigStatusEnum.map.get(AccountBizsysConfigStatusEnum.getEnum(value));
    }
}

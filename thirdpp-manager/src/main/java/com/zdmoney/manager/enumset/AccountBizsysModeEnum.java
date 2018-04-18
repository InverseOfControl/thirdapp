package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 对账模式
 * @author srainsk
 *
 */
public enum AccountBizsysModeEnum {
	/**
	 *  合并
	 */
	MERGE("0"),
	/**
	 *  拆开
	 */
	SERARATE("1"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountBizsysModeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountBizsysModeEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountBizsysModeEnum, String>(AccountBizsysModeEnum.class);
    	map.put(AccountBizsysModeEnum.MERGE , "合并");
    	map.put(AccountBizsysModeEnum.SERARATE , "拆开");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountBizsysModeEnum getEnum(String value) {
    	AccountBizsysModeEnum tmpKey = null;
        for (AccountBizsysModeEnum tmpEnum : AccountBizsysModeEnum.values()) {
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
        return AccountBizsysModeEnum.map.get(AccountBizsysModeEnum.getEnum(value));
    }
}

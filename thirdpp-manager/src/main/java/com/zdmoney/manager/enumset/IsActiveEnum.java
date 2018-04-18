package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 是否有效
 * @author srainsk
 *
 */
public enum IsActiveEnum {
	/**
	 *  有效
	 */
	ACTIVE("1"),
	/**
	 * 无效
	 */
	INACTIVE("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    IsActiveEnum(String value) {
        this.value = value;
    }
    
    public final static Map<IsActiveEnum, String> map ;
    
    static{
    	map = new EnumMap<IsActiveEnum, String>(IsActiveEnum.class);
    	map.put(IsActiveEnum.ACTIVE , "有效");
    	map.put(IsActiveEnum.INACTIVE , "无效");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static IsActiveEnum getEnum(String value) {
    	IsActiveEnum tmpKey = null;
        for (IsActiveEnum tmpEnum : IsActiveEnum.values()) {
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
        return IsActiveEnum.map.get(IsActiveEnum.getEnum(value));
    }
}

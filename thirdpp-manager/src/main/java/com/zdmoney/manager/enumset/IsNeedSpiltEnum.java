package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 是否需要拆单
 * @author srainsk
 *
 */
public enum IsNeedSpiltEnum {
	/**
	 *  不需要拆单
	 */
	NOT_NEED_SPILT("0"),
	/**
	 * 需要拆单
	 */
	NEED_SPILT("1"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    IsNeedSpiltEnum(String value) {
        this.value = value;
    }
    
    public final static Map<IsNeedSpiltEnum, String> map ;
    
    static{
    	map = new EnumMap<IsNeedSpiltEnum, String>(IsNeedSpiltEnum.class);
    	map.put(IsNeedSpiltEnum.NOT_NEED_SPILT , "不需要拆单");
    	map.put(IsNeedSpiltEnum.NEED_SPILT , "需要拆单");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static IsNeedSpiltEnum getEnum(String value) {
    	IsNeedSpiltEnum tmpKey = null;
        for (IsNeedSpiltEnum tmpEnum : IsNeedSpiltEnum.values()) {
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
        return IsNeedSpiltEnum.map.get(IsNeedSpiltEnum.getEnum(value));
    }
}

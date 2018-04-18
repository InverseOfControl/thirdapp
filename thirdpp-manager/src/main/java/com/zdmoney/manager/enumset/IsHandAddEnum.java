package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 币种
 * @author srainsk
 *
 */
public enum IsHandAddEnum {
	/**
	 *  补单
	 */
	HAND_ADD("1"),
	/**
	 * 非补单
	 */
	NOT_HAND_ADD("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    IsHandAddEnum(String value) {
        this.value = value;
    }
    
    public final static Map<IsHandAddEnum, String> map ;
    
    static{
    	map = new EnumMap<IsHandAddEnum, String>(IsHandAddEnum.class);
    	map.put(IsHandAddEnum.HAND_ADD , "补单");
    	map.put(IsHandAddEnum.NOT_HAND_ADD , "非补单");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static IsHandAddEnum getEnum(String value) {
    	IsHandAddEnum tmpKey = null;
        for (IsHandAddEnum tmpEnum : IsHandAddEnum.values()) {
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
        return IsHandAddEnum.map.get(IsHandAddEnum.getEnum(value));
    }
}

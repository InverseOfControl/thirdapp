package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 性别
 * @author srainsk
 *
 */
public enum GenderEnum {
	/**
	 *男  
	 */
	MAN("1"),
	/**
	 * 女
	 */
	WOMAN("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    GenderEnum(String value) {
        this.value = value;
    }
    
    public final static Map<GenderEnum, String> map ;
    
    static{
    	map = new EnumMap<GenderEnum, String>(GenderEnum.class);
    	map.put(GenderEnum.MAN , "男");
    	map.put(GenderEnum.WOMAN , "女");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static GenderEnum getEnum(String value) {
    	GenderEnum tmpKey = null;
        for (GenderEnum tmpEnum : GenderEnum.values()) {
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
        return GenderEnum.map.get(GenderEnum.getEnum(value));
    }
}

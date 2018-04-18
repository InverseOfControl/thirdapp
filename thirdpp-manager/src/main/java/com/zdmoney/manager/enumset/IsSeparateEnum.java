package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 是否拆单
 * @author srainsk
 *
 */
public enum IsSeparateEnum {
	/**
	 *  已拆单
	 */
	SERARATE("1"),
	/**
	 * 未拆单
	 */
	NOT_SERARATE("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    IsSeparateEnum(String value) {
        this.value = value;
    }
    
    public final static Map<IsSeparateEnum, String> map ;
    
    static{
    	map = new EnumMap<IsSeparateEnum, String>(IsSeparateEnum.class);
    	map.put(IsSeparateEnum.SERARATE , "已拆单");
    	map.put(IsSeparateEnum.NOT_SERARATE , "未拆单");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static IsSeparateEnum getEnum(String value) {
    	IsSeparateEnum tmpKey = null;
        for (IsSeparateEnum tmpEnum : IsSeparateEnum.values()) {
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
        return IsSeparateEnum.map.get(IsSeparateEnum.getEnum(value));
    }
}

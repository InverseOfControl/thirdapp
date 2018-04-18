package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 币种
 * @author srainsk
 *
 */
public enum IsSyncEnum {
	/**
	 *  同步
	 */
	SYNC("1"),
	/**
	 * 异步
	 */
	NOT_SYNC("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    IsSyncEnum(String value) {
        this.value = value;
    }
    
    public final static Map<IsSyncEnum, String> map ;
    
    static{
    	map = new EnumMap<IsSyncEnum, String>(IsSyncEnum.class);
    	map.put(IsSyncEnum.SYNC , "同步");
    	map.put(IsSyncEnum.NOT_SYNC , "异步");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static IsSyncEnum getEnum(String value) {
    	IsSyncEnum tmpKey = null;
        for (IsSyncEnum tmpEnum : IsSyncEnum.values()) {
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
        return IsSyncEnum.map.get(IsSyncEnum.getEnum(value));
    }
}

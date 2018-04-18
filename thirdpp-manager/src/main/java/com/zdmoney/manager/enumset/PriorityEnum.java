package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 优先级
 * @author srainsk
 *
 */
public enum PriorityEnum {
	/**
	 *  最高
	 */
	HIGHEST("3"),
	/**
	 * 高
	 */
	HIGH("2"),
	/**
	 * 中
	 */
	MIDDLE("1"),
	/**
	 * 普通
	 */
	GENERAL("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    PriorityEnum(String value) {
        this.value = value;
    }
    
    public final static Map<PriorityEnum, String> map ;
    
    static{
    	map = new EnumMap<PriorityEnum, String>(PriorityEnum.class);
    	map.put(PriorityEnum.HIGHEST , "最高");
    	map.put(PriorityEnum.HIGH , "高");
    	map.put(PriorityEnum.MIDDLE , "中");
    	map.put(PriorityEnum.GENERAL , "普通");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static PriorityEnum getEnum(String value) {
    	PriorityEnum tmpKey = null;
        for (PriorityEnum tmpEnum : PriorityEnum.values()) {
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
        return PriorityEnum.map.get(PriorityEnum.getEnum(value));
    }
}

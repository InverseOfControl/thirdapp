package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 性别
 * @author srainsk
 *
 */
public enum OpenAccountCardsStatusEnum {
	/**
	 *正常  
	 */
	Normal("1"),
	/**
	 * 解绑
	 */
	Unbundling("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    OpenAccountCardsStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<OpenAccountCardsStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<OpenAccountCardsStatusEnum, String>(OpenAccountCardsStatusEnum.class);
    	map.put(OpenAccountCardsStatusEnum.Normal , "正常");
    	map.put(OpenAccountCardsStatusEnum.Unbundling , "解绑");
     
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static OpenAccountCardsStatusEnum getEnum(String value) {
    	OpenAccountCardsStatusEnum tmpKey = null;
        for (OpenAccountCardsStatusEnum tmpEnum : OpenAccountCardsStatusEnum.values()) {
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
        return OpenAccountCardsStatusEnum.map.get(OpenAccountCardsStatusEnum.getEnum(value));
    }
}

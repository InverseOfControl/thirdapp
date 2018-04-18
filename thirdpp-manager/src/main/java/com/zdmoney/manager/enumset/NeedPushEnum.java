package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 推送
 * @author srainsk
 *
 */
public enum NeedPushEnum {
	/**
	 *  需要推送
	 */
	NEED_PUSH("1"),
	/**
	 * 不需要推送
	 */
	NOT_NEED_PUSH("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    NeedPushEnum(String value) {
        this.value = value;
    }
    
    public final static Map<NeedPushEnum, String> map ;
    
    static{
    	map = new EnumMap<NeedPushEnum, String>(NeedPushEnum.class);
    	map.put(NeedPushEnum.NEED_PUSH , "需要推送");
    	map.put(NeedPushEnum.NOT_NEED_PUSH , "不需要推送");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static NeedPushEnum getEnum(String value) {
    	NeedPushEnum tmpKey = null;
        for (NeedPushEnum tmpEnum : NeedPushEnum.values()) {
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
        return NeedPushEnum.map.get(NeedPushEnum.getEnum(value));
    }
}

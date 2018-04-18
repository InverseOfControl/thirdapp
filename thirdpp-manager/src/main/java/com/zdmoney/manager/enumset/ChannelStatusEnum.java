package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 运营方式
 * @author srainsk
 *
 */
public enum ChannelStatusEnum {
	/**
	 *  关闭
	 */
	close("0"),
	
	/**
	 *  开启
	 */
	open("1"),
	 ;
	
	
	private String value;

    public String getValue() {
        return value;
    }
    
    ChannelStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<ChannelStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<ChannelStatusEnum, String>(ChannelStatusEnum.class);
    	map.put(ChannelStatusEnum.close , "关闭");
    	map.put(ChannelStatusEnum.open , "开启");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static ChannelStatusEnum getEnum(String value) {
    	ChannelStatusEnum tmpKey = null;
        for (ChannelStatusEnum tmpEnum : ChannelStatusEnum.values()) {
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
        return ChannelStatusEnum.map.get(ChannelStatusEnum.getEnum(value));
    }
}

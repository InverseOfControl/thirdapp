package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 报文类型
 * @author srainsk
 *
 */
public enum MessageTypeEnum {
	/**
	 *  请求
	 */
	REQUEST("Q"),
	/**
	 * 响应
	 */
	RESPONSE("S"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    MessageTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<MessageTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<MessageTypeEnum, String>(MessageTypeEnum.class);
    	map.put(MessageTypeEnum.REQUEST , "请求报文");
    	map.put(MessageTypeEnum.RESPONSE , "响应报文");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static MessageTypeEnum getEnum(String value) {
    	MessageTypeEnum tmpKey = null;
        for (MessageTypeEnum tmpEnum : MessageTypeEnum.values()) {
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
        return MessageTypeEnum.map.get(MessageTypeEnum.getEnum(value));
    }
}

package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 渠道请求状态
 * @author srainsk
 *
 */
public enum ChannelRequestStatusEnum {
	
	/**
	 *  初始化
	 */
	INIT("0"),
	/**
	 * 请求报文解析失败
	 */
	REQUEST_PARSE_FAIL("1"),
	/**
	 * 请求报文已发送
	 */
	REQUEST_SENT("2"),
	/**
	 * 请求报文发送失败
	 */
	REQUEST_SEND_FAIL("3"),
	/**
	 * 接受到响应报文
	 */
	RECEIVE_RESPONSE("4"),
	/**
	 * 响应报文解析失败
	 */
	RESPONSE_PARSE_FAIL("5"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    ChannelRequestStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<ChannelRequestStatusEnum, String> map ;
    static{
    	map = new EnumMap<ChannelRequestStatusEnum, String>(ChannelRequestStatusEnum.class);
    	map.put(ChannelRequestStatusEnum.INIT , "初始化");
    	map.put(ChannelRequestStatusEnum.REQUEST_PARSE_FAIL , "请求报文解析失败");
    	map.put(ChannelRequestStatusEnum.REQUEST_SENT , "请求报文已发送");
    	map.put(ChannelRequestStatusEnum.REQUEST_SEND_FAIL , "请求报文发送失败");
    	map.put(ChannelRequestStatusEnum.RECEIVE_RESPONSE , "收到响应报文");
    	map.put(ChannelRequestStatusEnum.RESPONSE_PARSE_FAIL , "响应报文解析失败");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static ChannelRequestStatusEnum getEnum(String value) {
    	ChannelRequestStatusEnum tmpKey = null;
        for (ChannelRequestStatusEnum tmpEnum : ChannelRequestStatusEnum.values()) {
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
        return ChannelRequestStatusEnum.map.get(ChannelRequestStatusEnum.getEnum(value));
    }
}

package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 发送状态
 * @author srainsk
 *
 */
public enum SendStatusEnum {
	/**
	 *  待发送
	 */
	WAIT_SEND("0"),
	/**
	 * 发送中
	 */
	DO_SEND("1"),
	/**
	 * 已发送
	 */
	SENT("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    SendStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<SendStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<SendStatusEnum, String>(SendStatusEnum.class);
    	map.put(SendStatusEnum.WAIT_SEND , "待发送");
    	map.put(SendStatusEnum.DO_SEND , "发送中");
    	map.put(SendStatusEnum.SENT , "已发送");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static SendStatusEnum getEnum(String value) {
    	SendStatusEnum tmpKey = null;
        for (SendStatusEnum tmpEnum : SendStatusEnum.values()) {
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
        return SendStatusEnum.map.get(SendStatusEnum.getEnum(value));
    }
}

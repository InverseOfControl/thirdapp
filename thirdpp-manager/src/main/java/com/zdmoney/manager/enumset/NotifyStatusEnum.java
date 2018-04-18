package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 通知状态
 * @author srainsk
 *
 */
public enum NotifyStatusEnum {
	/**
	 *  待通知
	 */
	WAIT_NOTIFY("1"),
	/**
	 * 通知中
	 */
	DO_NOTIFY("2"),
	/**
	 * 已通知
	 */
	NOTIFIED("3"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    NotifyStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<NotifyStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<NotifyStatusEnum, String>(NotifyStatusEnum.class);
    	map.put(NotifyStatusEnum.WAIT_NOTIFY , "待通知");
    	map.put(NotifyStatusEnum.DO_NOTIFY , "通知中");
    	map.put(NotifyStatusEnum.NOTIFIED , "已通知");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static NotifyStatusEnum getEnum(String value) {
    	NotifyStatusEnum tmpKey = null;
        for (NotifyStatusEnum tmpEnum : NotifyStatusEnum.values()) {
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
        return NotifyStatusEnum.map.get(NotifyStatusEnum.getEnum(value));
    }
}

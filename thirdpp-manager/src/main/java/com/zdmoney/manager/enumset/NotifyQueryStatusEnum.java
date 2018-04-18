package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 通知查询状态
 * @author srainsk
 *
 */
public enum NotifyQueryStatusEnum {
	/**
	 *  待通知查询
	 */
	WAIT_NOTIFY("0"),
	/**
	 * 不需要通知查询
	 */
	NOT_NOTIFY("1"),
	/**
	 * 已通知查询
	 */
	NOTIFIED("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    NotifyQueryStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<NotifyQueryStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<NotifyQueryStatusEnum, String>(NotifyQueryStatusEnum.class);
    	map.put(NotifyQueryStatusEnum.WAIT_NOTIFY , "待通知查询");
    	map.put(NotifyQueryStatusEnum.NOT_NOTIFY , "不需要通知查询");
    	map.put(NotifyQueryStatusEnum.NOTIFIED , "已通知查询");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static NotifyQueryStatusEnum getEnum(String value) {
    	NotifyQueryStatusEnum tmpKey = null;
        for (NotifyQueryStatusEnum tmpEnum : NotifyQueryStatusEnum.values()) {
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
        return NotifyQueryStatusEnum.map.get(NotifyQueryStatusEnum.getEnum(value));
    }
}

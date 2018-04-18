package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 通知合并状态
 * @author srainsk
 *
 */
public enum NotifyMergeStatusEnum {
	/**
	 *  待通知合并
	 */
	WAIT_NOTIFY("0"),
	/**
	 * 不需要通知合并
	 */
	NOT_NOTIFY("1"),
	/**
	 * 已通知合并
	 */
	NOTIFIED("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    NotifyMergeStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<NotifyMergeStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<NotifyMergeStatusEnum, String>(NotifyMergeStatusEnum.class);
    	map.put(NotifyMergeStatusEnum.WAIT_NOTIFY , "待通知合并");
    	map.put(NotifyMergeStatusEnum.NOT_NOTIFY , "不需要通知合并");
    	map.put(NotifyMergeStatusEnum.NOTIFIED , "已通知合并");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static NotifyMergeStatusEnum getEnum(String value) {
    	NotifyMergeStatusEnum tmpKey = null;
        for (NotifyMergeStatusEnum tmpEnum : NotifyMergeStatusEnum.values()) {
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
        return NotifyMergeStatusEnum.map.get(NotifyMergeStatusEnum.getEnum(value));
    }
}

package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 交易等待状态
 * @author srainsk
 *
 */
public enum TradeWaitingStatusEnum {
	/**
	 *  待处理
	 */
	WAIT_PROCESS("0"),
	/**
	 *  已处理
	 */
	PROCESSED("1"),
	/**
	 *  待审核
	 */
	WAIT_CHECK("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    TradeWaitingStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<TradeWaitingStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<TradeWaitingStatusEnum, String>(TradeWaitingStatusEnum.class);
    	map.put(TradeWaitingStatusEnum.WAIT_PROCESS , "待处理");
    	map.put(TradeWaitingStatusEnum.PROCESSED , "已处理");
    	map.put(TradeWaitingStatusEnum.WAIT_CHECK , "待审核");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static TradeWaitingStatusEnum getEnum(String value) {
    	TradeWaitingStatusEnum tmpKey = null;
        for (TradeWaitingStatusEnum tmpEnum : TradeWaitingStatusEnum.values()) {
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
        return TradeWaitingStatusEnum.map.get(TradeWaitingStatusEnum.getEnum(value));
    }
}

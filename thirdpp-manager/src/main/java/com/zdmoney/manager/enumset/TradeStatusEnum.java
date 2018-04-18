package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 交易状态
 * @author srainsk
 *
 */
public enum TradeStatusEnum {
	/**
	 *  交易成功
	 */
	SUCCESS("000000"),
	/**
	 * 交易失败
	 */
	FAIL("111111"),
	/**
	 * 交易处理中
	 */
	PROCESSING("222222"),
	/**
	 * 交易异常
	 */
	EXCEPTION("333333"),
	
	/**
	 * 部分成功
	 */
	PART_SUCCESS("444444"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    TradeStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<TradeStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<TradeStatusEnum, String>(TradeStatusEnum.class);
    	map.put(TradeStatusEnum.SUCCESS , "交易成功");
    	map.put(TradeStatusEnum.FAIL , "交易失败");
    	map.put(TradeStatusEnum.PROCESSING , "交易处理中");
    	map.put(TradeStatusEnum.EXCEPTION , "交易异常");
    	map.put(TradeStatusEnum.PART_SUCCESS , "部分成功");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static TradeStatusEnum getEnum(String value) {
    	TradeStatusEnum tmpKey = null;
        for (TradeStatusEnum tmpEnum : TradeStatusEnum.values()) {
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
        return TradeStatusEnum.map.get(TradeStatusEnum.getEnum(value));
    }
}

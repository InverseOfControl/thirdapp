package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 与业务系统的对账状态
 * @author srainsk
 *
 */
public enum AccountBizsysNotifyStatusEnum {
	/**
	 *  未对账
	 */
	NO_ACCOUNT("0"),
	/**
	 *  对账进行中
	 */
	IN_ACCOUNT("1"),
	/**
	 *  对账完成
	 */
	FINISH_ACCOUNT("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountBizsysNotifyStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountBizsysNotifyStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountBizsysNotifyStatusEnum, String>(AccountBizsysNotifyStatusEnum.class);
    	map.put(AccountBizsysNotifyStatusEnum.NO_ACCOUNT , "未对账");
    	map.put(AccountBizsysNotifyStatusEnum.IN_ACCOUNT , "对账进行中");
    	map.put(AccountBizsysNotifyStatusEnum.FINISH_ACCOUNT , "对账完成");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountBizsysNotifyStatusEnum getEnum(String value) {
    	AccountBizsysNotifyStatusEnum tmpKey = null;
        for (AccountBizsysNotifyStatusEnum tmpEnum : AccountBizsysNotifyStatusEnum.values()) {
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
        return AccountBizsysNotifyStatusEnum.map.get(AccountBizsysNotifyStatusEnum.getEnum(value));
    }
}

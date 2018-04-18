package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 与业务系统对账状态
 * @author srainsk
 *
 */
public enum AccountBizsysStatusEnum {
	/**
	 *  未对账
	 */
	NO_ACCOUNT("0"),
	/**
	 *  已对账
	 */
	ALLREADY_ACCOUNT("1"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountBizsysStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountBizsysStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountBizsysStatusEnum, String>(AccountBizsysStatusEnum.class);
    	map.put(AccountBizsysStatusEnum.NO_ACCOUNT , "未对账");
    	map.put(AccountBizsysStatusEnum.ALLREADY_ACCOUNT , "已对账");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountBizsysStatusEnum getEnum(String value) {
    	AccountBizsysStatusEnum tmpKey = null;
        for (AccountBizsysStatusEnum tmpEnum : AccountBizsysStatusEnum.values()) {
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
        return AccountBizsysStatusEnum.map.get(AccountBizsysStatusEnum.getEnum(value));
    }
}

package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 对账货币单位
 * @author srainsk
 *
 */
public enum AccountCurrencyUnitEnum {
	/**
	 *  分
	 */
	FEN("0"),
	/**
	 *  元
	 */
	YUAN("1"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountCurrencyUnitEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountCurrencyUnitEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountCurrencyUnitEnum, String>(AccountCurrencyUnitEnum.class);
    	map.put(AccountCurrencyUnitEnum.FEN , "分");
    	map.put(AccountCurrencyUnitEnum.YUAN , "元");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountCurrencyUnitEnum getEnum(String value) {
    	AccountCurrencyUnitEnum tmpKey = null;
        for (AccountCurrencyUnitEnum tmpEnum : AccountCurrencyUnitEnum.values()) {
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
        return AccountCurrencyUnitEnum.map.get(AccountCurrencyUnitEnum.getEnum(value));
    }
}

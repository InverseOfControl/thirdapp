package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 币种
 * @author srainsk
 *
 */
public enum CurrencyEnum {
	/**
	 *  人民币
	 */
	RMB("0"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    CurrencyEnum(String value) {
        this.value = value;
    }
    
    public final static Map<CurrencyEnum, String> map ;
    
    static{
    	map = new EnumMap<CurrencyEnum, String>(CurrencyEnum.class);
    	map.put(CurrencyEnum.RMB , "人民币");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static CurrencyEnum getEnum(String value) {
    	CurrencyEnum tmpKey = null;
        for (CurrencyEnum tmpEnum : CurrencyEnum.values()) {
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
        return CurrencyEnum.map.get(CurrencyEnum.getEnum(value));
    }
}

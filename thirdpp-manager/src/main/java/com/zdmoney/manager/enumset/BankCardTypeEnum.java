package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 银行卡类型
 * @author srainsk
 *
 */
public enum BankCardTypeEnum {
	/**
	 *  借记卡
	 */
	DEBIT_CARD("1"),
	/**
	 * 信用卡
	 */
	CREDIT_CARD("2"), 	
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    BankCardTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<BankCardTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<BankCardTypeEnum, String>(BankCardTypeEnum.class);
    	map.put(BankCardTypeEnum.DEBIT_CARD , "借记卡");
    	map.put(BankCardTypeEnum.CREDIT_CARD, "信用卡");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static BankCardTypeEnum getEnum(String value) {
    	BankCardTypeEnum tmpKey = null;
        for (BankCardTypeEnum tmpEnum : BankCardTypeEnum.values()) {
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
        return BankCardTypeEnum.map.get(BankCardTypeEnum.getEnum(value));
    }
}

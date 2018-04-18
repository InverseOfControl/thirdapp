package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 与第三方的对账状态
 * @author srainsk
 *
 */
public enum AccountThirdStatusEnum {
	/**
	 * 未对账
	 */
	UNDO("0"),
	/**
	 *  对账失败
	 */
	FAIL("1"),
	/**
	 *  对账成功
	 */
	SUCCESS("2"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountThirdStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountThirdStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountThirdStatusEnum, String>(AccountThirdStatusEnum.class);
    	map.put(AccountThirdStatusEnum.UNDO , "未对账");
    	map.put(AccountThirdStatusEnum.SUCCESS , "对账成功");
    	map.put(AccountThirdStatusEnum.FAIL , "对账失败");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountThirdStatusEnum getEnum(String value) {
    	AccountThirdStatusEnum tmpKey = null;
        for (AccountThirdStatusEnum tmpEnum : AccountThirdStatusEnum.values()) {
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
        return AccountThirdStatusEnum.map.get(AccountThirdStatusEnum.getEnum(value));
    }
}

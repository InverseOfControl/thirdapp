package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 手工对账状态
 * @author srainsk
 *
 */
public enum AccountHandleAccountStatusEnum {
	/**
	 *  0：未处理
	 */
	UNPROCESS("0"),
	/**
	 *  1：处理中
	 */
	PROCESSING("1"),
	/**
	 *  2：处理失败
	 */
	FAIL_PROCESS("2"),
	/**
	 *  3：处理成功
	 */
	SUCCESS_PROCESS("3"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountHandleAccountStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountHandleAccountStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountHandleAccountStatusEnum, String>(AccountHandleAccountStatusEnum.class);
    	map.put(AccountHandleAccountStatusEnum.UNPROCESS , "未处理");
    	map.put(AccountHandleAccountStatusEnum.PROCESSING , "处理中");
    	map.put(AccountHandleAccountStatusEnum.FAIL_PROCESS , "处理失败");
    	map.put(AccountHandleAccountStatusEnum.SUCCESS_PROCESS , "处理成功");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountHandleAccountStatusEnum getEnum(String value) {
    	AccountHandleAccountStatusEnum tmpKey = null;
        for (AccountHandleAccountStatusEnum tmpEnum : AccountHandleAccountStatusEnum.values()) {
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
        return AccountHandleAccountStatusEnum.map.get(AccountHandleAccountStatusEnum.getEnum(value));
    }
}

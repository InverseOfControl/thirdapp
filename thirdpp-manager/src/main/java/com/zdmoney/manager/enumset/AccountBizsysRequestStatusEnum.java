package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 业务系统对账请求状态
 * @author srainsk
 *
 */
public enum AccountBizsysRequestStatusEnum {
	/**
	 *  初始状态
	 */
	INIT("0"),
	/**
	 *  保存至本地失败
	 */
	SAVE2LOCAL_FAIL("1"),
	/**
	 *  保存至本地成功
	 */
	SAVE2LOCAL_SUCCESS("2"),
	/**
	 *  推送失败
	 */
	PUSH_FAIL("3"),
	/**
	 *  推送成功
	 */
	PUSH_SUCCESS("4"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    AccountBizsysRequestStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<AccountBizsysRequestStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<AccountBizsysRequestStatusEnum, String>(AccountBizsysRequestStatusEnum.class);
    	map.put(AccountBizsysRequestStatusEnum.INIT , "初始状态");
    	map.put(AccountBizsysRequestStatusEnum.SAVE2LOCAL_FAIL , "保存至本地失败");
    	map.put(AccountBizsysRequestStatusEnum.SAVE2LOCAL_SUCCESS , "保存至本地成功");
    	map.put(AccountBizsysRequestStatusEnum.PUSH_FAIL , "推送失败");
    	map.put(AccountBizsysRequestStatusEnum.PUSH_SUCCESS , "推送成功");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static AccountBizsysRequestStatusEnum getEnum(String value) {
    	AccountBizsysRequestStatusEnum tmpKey = null;
        for (AccountBizsysRequestStatusEnum tmpEnum : AccountBizsysRequestStatusEnum.values()) {
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
        return AccountBizsysRequestStatusEnum.map.get(AccountBizsysRequestStatusEnum.getEnum(value));
    }
}

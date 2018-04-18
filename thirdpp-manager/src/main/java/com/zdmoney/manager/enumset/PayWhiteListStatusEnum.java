package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

public enum PayWhiteListStatusEnum {
	// 无效
	invalid("0"), 
	// 有效
	valid("1");
	
	private String code;

	PayWhiteListStatusEnum(String code) {
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public final static Map<PayWhiteListStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<PayWhiteListStatusEnum, String>(PayWhiteListStatusEnum.class);
    	map.put(PayWhiteListStatusEnum.invalid , "无效");
    	map.put(PayWhiteListStatusEnum.valid , "有效");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static PayWhiteListStatusEnum getEnum(String value) {
    	PayWhiteListStatusEnum tmpKey = null;
        for (PayWhiteListStatusEnum tmpEnum : PayWhiteListStatusEnum.values()) {
            if (tmpEnum.code.equals(value)) {
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
        return PayWhiteListStatusEnum.map.get(PayWhiteListStatusEnum.getEnum(value));
    }
}

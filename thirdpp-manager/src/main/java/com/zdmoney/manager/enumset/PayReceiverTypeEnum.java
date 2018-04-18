package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

public enum PayReceiverTypeEnum {
	// 对公
	to_public("C"), 
	// 对私
	to_private("P");
	
	private String code;

	PayReceiverTypeEnum(String code) {
		this.code = code;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public final static Map<PayReceiverTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<PayReceiverTypeEnum, String>(PayReceiverTypeEnum.class);
    	map.put(PayReceiverTypeEnum.to_public , "对公");
    	map.put(PayReceiverTypeEnum.to_private , "对私");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static PayReceiverTypeEnum getEnum(String value) {
    	PayReceiverTypeEnum tmpKey = null;
        for (PayReceiverTypeEnum tmpEnum : PayReceiverTypeEnum.values()) {
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
        return PayReceiverTypeEnum.map.get(PayReceiverTypeEnum.getEnum(value));
    }
}

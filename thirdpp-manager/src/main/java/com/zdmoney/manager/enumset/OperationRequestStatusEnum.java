package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 交易状态
 * @author srainsk
 *
 */
public enum OperationRequestStatusEnum {
	/**
	 *  操作成功
	 */

	SUCCESS("1"),
	/**
	 * 操作失败
	 */
	FAIL("2"),
	/**
	 * 处理中
	 */
	PROCESSING("0")   
	 
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    OperationRequestStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<OperationRequestStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<OperationRequestStatusEnum, String>(OperationRequestStatusEnum.class);
    	map.put(OperationRequestStatusEnum.SUCCESS , "操作成功");
    	map.put(OperationRequestStatusEnum.FAIL , "操作失败");
    	map.put(OperationRequestStatusEnum.PROCESSING , "处理中");
     
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static OperationRequestStatusEnum getEnum(String value) {
    	OperationRequestStatusEnum tmpKey = null;
        for (OperationRequestStatusEnum tmpEnum : OperationRequestStatusEnum.values()) {
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
        return OperationRequestStatusEnum.map.get(OperationRequestStatusEnum.getEnum(value));
    }
}

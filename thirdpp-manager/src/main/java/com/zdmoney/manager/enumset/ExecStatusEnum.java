package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 执行状态
 * @author srainsk
 *
 */
public enum ExecStatusEnum {
	/**
	 *  待执行
	 */
	NOT_EXEC("0"),
	/**
	 * 已执行
	 */
	EXECED("1"),
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    ExecStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<ExecStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<ExecStatusEnum, String>(ExecStatusEnum.class);
    	map.put(ExecStatusEnum.NOT_EXEC , "未执行");
    	map.put(ExecStatusEnum.EXECED , "已执行");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static ExecStatusEnum getEnum(String value) {
    	ExecStatusEnum tmpKey = null;
        for (ExecStatusEnum tmpEnum : ExecStatusEnum.values()) {
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
        return ExecStatusEnum.map.get(ExecStatusEnum.getEnum(value));
    }
}

package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 菜单类型
 * @author srainsk
 *
 */
public enum PermissionTypeEnum {
	/**
	 *  菜单
	 */
	MENU("1"),
	/**
	 * 功能
	 *//*
	FUNCTION("2"),*/
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    PermissionTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<PermissionTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<PermissionTypeEnum, String>(PermissionTypeEnum.class);
    	map.put(PermissionTypeEnum.MENU , "菜单");
    	//map.put(PermissionTypeEnum.FUNCTION , "功能");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static PermissionTypeEnum getEnum(String value) {
    	PermissionTypeEnum tmpKey = null;
        for (PermissionTypeEnum tmpEnum : PermissionTypeEnum.values()) {
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
        return PermissionTypeEnum.map.get(PermissionTypeEnum.getEnum(value));
    }
}

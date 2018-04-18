package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 运营方式
 * @author srainsk
 *
 */
public enum OPModeEnum {
	/**
	 *  线下
	 */
	OFF_LINE("0"),
	
	/**
	 *  线上
	 */
	ON_LINE("1"),
	
	/**
	 *  资金托管
	 */
	CUSTOD("2"),
	 ;
	
	
	private String value;

    public String getValue() {
        return value;
    }
    
    OPModeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<OPModeEnum, String> map ;
    
    static{
    	map = new EnumMap<OPModeEnum, String>(OPModeEnum.class);
    	map.put(OPModeEnum.OFF_LINE , "线下运营");
    	map.put(OPModeEnum.ON_LINE , "线上运营");
    	map.put(OPModeEnum.CUSTOD , "资金托管");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static OPModeEnum getEnum(String value) {
    	OPModeEnum tmpKey = null;
        for (OPModeEnum tmpEnum : OPModeEnum.values()) {
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
        return OPModeEnum.map.get(OPModeEnum.getEnum(value));
    }
}

package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 性别
 * @author srainsk
 *
 */
public enum OpenAccountStatusEnum {
	/**
	 *正常  
	 */
	Normal("1"),
	/**
	 * 冻结
	 */
	Frozen("2"),
	/**
	 * 销户
	 */
	CancelTheAccount("3")
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    OpenAccountStatusEnum(String value) {
        this.value = value;
    }
    
    public final static Map<OpenAccountStatusEnum, String> map ;
    
    static{
    	map = new EnumMap<OpenAccountStatusEnum, String>(OpenAccountStatusEnum.class);
    	map.put(OpenAccountStatusEnum.Normal , "正常");
    	map.put(OpenAccountStatusEnum.Frozen , "冻结");
    	map.put(OpenAccountStatusEnum.CancelTheAccount , "销户");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static OpenAccountStatusEnum getEnum(String value) {
    	OpenAccountStatusEnum tmpKey = null;
        for (OpenAccountStatusEnum tmpEnum : OpenAccountStatusEnum.values()) {
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
        return OpenAccountStatusEnum.map.get(OpenAccountStatusEnum.getEnum(value));
    }
}

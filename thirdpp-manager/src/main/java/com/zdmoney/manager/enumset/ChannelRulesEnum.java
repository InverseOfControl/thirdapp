package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 优先级
 * @author srainsk
 *
 */
public enum ChannelRulesEnum {
	
	/**
	 *  按业务系统指定通道策略
	 */
	CHANNEL_RULES_BIZSYS("1"),
	/**
	 * 按银行指定通道策略
	 */
	CHANNEL_RULES_BANKS("2"),
	
	/**
	 * 按路由规则指定通道策略
	 */
	CHANNEL_RULES_PAY("3"),
	;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    ChannelRulesEnum(String value) {
        this.value = value;
    }
    
    public final static Map<ChannelRulesEnum, String> map ;
    
    static{
    	map = new EnumMap<ChannelRulesEnum, String>(ChannelRulesEnum.class);
    	map.put(ChannelRulesEnum.CHANNEL_RULES_BIZSYS , "按业务系统指定通道");
    	map.put(ChannelRulesEnum.CHANNEL_RULES_BANKS , "按银行指定通道");
    	map.put(ChannelRulesEnum.CHANNEL_RULES_PAY , "按路由规则指定通道");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static ChannelRulesEnum getEnum(String value) {
    	ChannelRulesEnum tmpKey = null;
        for (ChannelRulesEnum tmpEnum : ChannelRulesEnum.values()) {
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
        return ChannelRulesEnum.map.get(ChannelRulesEnum.getEnum(value));
    }
}

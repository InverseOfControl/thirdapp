package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

/**
 * 证件
 * @author srainsk
 *
 */
public enum IdTypeEnum {
	/**
	 * 0=身份证
	1=户口簿
	2=护照
	3=军官证
	4=士兵证
	5=港澳居民来往内地通行证
	6=台湾同胞来往内地通行证
	7=临时身份证
	8=外国人居留证
	9=警官证
	X=其他证件
	Y=驾驶证
	Z=回乡证
	 */
	
	/**
	 *  身份证
	 */
	SHEN_FEN_ZHENG("0"),
	/**
	 * 户口薄
	 */
	HU_KOU_BO("1"), 
	/**
	 * 护照
	 */
	HU_ZHAO("2"), 
	/**
	 * 军官证
	 */
	JUN_GUAN_ZHENG("3"), 
	/**
	 * 士兵证
	 */
	SHI_BING_ZHENG("4"), 
	/**
	 * 港澳居民来往内地通行证
	 */
	GANG_AO_TONG_XING_ZHENG("5"), 
	/**
	 * 台湾同胞来往内地通行证
	 */
	TAI_WAN_TONG_XING_ZHENG("6"), 
	/**
	 * 临时身份证
	 */
	LIN_SHI_SHEN_FEN_ZHENG("7"), 
	/**
	 * 外国人居留证
	 */
	WAI_GUO_REN_JU_LIU_ZHENG("8"), 
	/**
	 * 警官证
	 */
	JING_GUAN_ZHENG("9"), 
	/**
	 * 其他证件
	 */
	QI_TA_ZHENG_JIAN("X"), 
	
	/**
	 * 驾驶证
	 */
	JIA_SHI_ZHENG("Y"), 
	/**
	 * 回乡证
	 */
	HUI_XIANG_ZHENG("Z"), 
	 ;
	
	private String value;

    public String getValue() {
        return value;
    }
    
    IdTypeEnum(String value) {
        this.value = value;
    }
    
    public final static Map<IdTypeEnum, String> map ;
    
    static{
    	map = new EnumMap<IdTypeEnum, String>(IdTypeEnum.class);
    	map.put(IdTypeEnum.SHEN_FEN_ZHENG , "身份证");
    	map.put(IdTypeEnum.HU_KOU_BO, "户口薄");
    	map.put(IdTypeEnum.HU_ZHAO, "护照");
    	map.put(IdTypeEnum.JUN_GUAN_ZHENG, "军官证");
    	map.put(IdTypeEnum.SHI_BING_ZHENG, "士兵证");
    	map.put(IdTypeEnum.GANG_AO_TONG_XING_ZHENG, "港澳居民来往内地通行证");
    	map.put(IdTypeEnum.TAI_WAN_TONG_XING_ZHENG, "台湾同胞来往内地通行证");
    	map.put(IdTypeEnum.LIN_SHI_SHEN_FEN_ZHENG, "临时身份证");
    	map.put(IdTypeEnum.WAI_GUO_REN_JU_LIU_ZHENG, "外国人居留证");
    	map.put(IdTypeEnum.JING_GUAN_ZHENG, "警官证");
    	map.put(IdTypeEnum.QI_TA_ZHENG_JIAN, "其他证件");
    	map.put(IdTypeEnum.JIA_SHI_ZHENG, "驾驶证");
    	map.put(IdTypeEnum.HUI_XIANG_ZHENG, "回乡证");
    }
    
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return 
     */
    public static IdTypeEnum getEnum(String value) {
    	IdTypeEnum tmpKey = null;
        for (IdTypeEnum tmpEnum : IdTypeEnum.values()) {
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
        return IdTypeEnum.map.get(IdTypeEnum.getEnum(value));
    }
}

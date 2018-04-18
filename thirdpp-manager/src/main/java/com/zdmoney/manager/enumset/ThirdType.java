package com.zdmoney.manager.enumset;

import java.util.EnumMap;
import java.util.Map;

public enum ThirdType {
	
	/** 通联 支付*/
	ALLINPAY("0"),
	
	/** 富友支付 */
	FUIOUPAY("2" ),
	/** 上海银联支付 */
	SHUNIONPAY("4" ),
	
	/** 用友畅捷支付 */
	YONGYOUUNIONPAY("6"),
	
	/**
	 * 上海银联支付（实名认证）
	 */
	YINLIANPAY_SHIMINGRENZHENG("8"),
	
	/**
	 * 上海银联支付（代付）
	 */
	YINLIANPAY_DAIFU("10"),
	
	/**
	 * 招商银行资金托管
	 */
	CMBCHINA_TRUST("12"),
	;
	private final String value;
	
	
 

	public String getValue() {
		return value;
	}

	ThirdType(String value) {
        this.value = value;
    }

	 public final static Map<ThirdType, String> 	map ;
	  static{
	    	map = new EnumMap<ThirdType, String>(ThirdType.class);
	    	map.put(ThirdType.ALLINPAY , "通联支付");
	    	map.put(ThirdType.FUIOUPAY , "富友支付");
	    	map.put(ThirdType.SHUNIONPAY , "上海银联支付");
	    	map.put(ThirdType.YONGYOUUNIONPAY , "用友畅捷支付");
	    	map.put(ThirdType.YINLIANPAY_SHIMINGRENZHENG , "上海银联支付（实名认证）");
	    	map.put(ThirdType.YINLIANPAY_DAIFU , "上海银联支付（代付）");
	    	map.put(ThirdType.CMBCHINA_TRUST , "招商银行资金托管");
	    }
	  public static ThirdType getEnum(String value) {
		  ThirdType tmpKey = null;
	        for (ThirdType tmpEnum : ThirdType.values()) {
	            if (tmpEnum.value.equals(value)) {
	                tmpKey = tmpEnum;
	                break;
	            }
	        }
	        return tmpKey;
	    }
	  public static String getEnumDesc(final String value) {
	        return ThirdType.map.get(ThirdType.getEnum(value));
	    }
}

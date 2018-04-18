package com.zendaimoney.thirdpp.channel.util.fuioupay;

import java.math.BigDecimal;
import java.util.Map;

public class FuioupayUtil {


	private static final int UNIT = 100;


	public static BigDecimal yuanConvertFen(BigDecimal amount) {
		BigDecimal decUnit = BigDecimal.valueOf(UNIT);
		return amount.multiply(decUnit).setScale(0);
	}


	/**
	 * @param parametersMap 
	 * @return
	 */
	public static String getParameter(Map<String, String> parametersMap) {
		StringBuffer sb = new StringBuffer();
		for (String key : parametersMap.keySet()) {
			// 去除报文中 "xml=" 字符串
			if ("xml".equals(key)) {
				
				sb.append(parametersMap.get(key)+"&");
			} else {
				sb.append(key + "=" +parametersMap.get(key)+"&");
			}
		}
		return sb.substring(0,sb.length()-1);
	}

}

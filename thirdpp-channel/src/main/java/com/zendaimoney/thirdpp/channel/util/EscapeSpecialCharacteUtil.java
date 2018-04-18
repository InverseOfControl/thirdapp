package com.zendaimoney.thirdpp.channel.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 特殊字符转义工具类
 * @author mencius
 *
 */
public class EscapeSpecialCharacteUtil {
	
	public static Map<String, String> specialCharMap = new HashMap<String, String>();

	public static Map<String, String> escapeCharMap = new HashMap<String, String>();
	
	static{
		specialCharMap.put("\"", "&quot;"); // "
		specialCharMap.put("&", "&amp;"); // &
		specialCharMap.put("<", "&lt;"); // <
		specialCharMap.put(">", "&gt;"); // >
		specialCharMap.put("\'", "&apos;"); //'
		specialCharMap.put(" ",	"&nbsp;"); // 空格
		specialCharMap.put("|", "&brvbar;"); // |
		
		for (String key : specialCharMap.keySet()) {
			escapeCharMap.put(specialCharMap.get(key), key);
		}
		
	}
	
	/**
	 * 转换字符至转义字符或者从转义字符恢复至普通字符
	 * @param message
	 * @param specialCharMap
	 */
	public static String convert(String message, Map<String, String> specialCharMap) {
		
		if (!StringUtils.isBlank(message)) {
			
			for (String	key : specialCharMap.keySet()) {
				
				if (message.contains(key)) {
					message = message.replace(key, specialCharMap.get(key));
				}
			}
		}
		
		return message;
	}

}

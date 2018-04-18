package com.zdmoney.manager.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

/** 
 *
 * @author LeiJun
 * @version 2014年10月28日 下午3:50:11 
 */
public class PropertiesUtil {
	static Logger log = Logger.getLogger(PropertiesUtil.class);

	public static Map<String, String> listMap;

	static {
		if (listMap == null) {
			listMap = new HashMap<String, String>();
			Properties props = new Properties();
			try {
				props.load(PropertiesUtil.class
						.getResourceAsStream("/conf/config.properties"));
				Set<Map.Entry<Object, Object>> entrySet = props.entrySet();
				for (Map.Entry<Object, Object> entry : entrySet) {
					if (!entry.getKey().toString().startsWith("#")) {
						listMap.put(((String) entry.getKey()).trim(),
								((String) entry.getValue()).trim());
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String getValue(String key) {
		if (null == listMap) {
			return "";
		}
		return listMap.get(key);
	}

}

package com.zendaimoney.thirdpp.alarm.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Assert {

	private static Logger logger = LoggerFactory.getLogger(Assert.class);

	/**
	 * 验证手机号码是否不合法
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isNotMobile(String mobile) {
		Matcher m = null;
		// String condition = "^0?(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$";
		String condition = "^0?1\\d{10}$";
		Pattern p = Pattern.compile(condition);
		if (mobile == null || mobile.equals("")) {
			return true;
		}
		m = p.matcher(mobile);
		if (!m.matches()) {
			if (logger.isDebugEnabled()) {
				logger.debug("手机号码不合法:" + mobile);
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 验证电子邮件是否不合法
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isNotEmail(String email) {
		Matcher m = null;
		String condition = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(condition);
		if (email == null || email.equals("")) {
			return true;
		}
		m = p.matcher(email);
		if (!m.matches()) {
			if (logger.isDebugEnabled()) {
				logger.debug("电子邮件不合法" + email);
			}
			return true;
		}
		return false;
	}

	/**
	 * 验证是否为黑名单
	 * 
	 * @param value
	 * @param blackList
	 * @return
	 */
	public static boolean isBlack(String value, List<String> blackList) {
		if (blackList != null && blackList.contains(value)) {
			if (logger.isDebugEnabled()) {
				logger.debug("该手机号码或电子邮件为黑名单:" + value);
			}
			return true;
		}
		return false;
	}

	public static boolean isBlack(String value, String source,
			Map<String, List<String>> blackListMap) {
		List<String> black = null;
		if (blackListMap != null && !blackListMap.isEmpty()) {
			if (blackListMap.containsKey(source)) {
				black = blackListMap.get(source);
				if (black != null && black.contains(value)) {
					if (logger.isDebugEnabled()) {
						logger.debug("手机号码或电子邮件或客户号为黑名单:" + value + " ,来源是:"
								+ source);
					}
					return true;
				}
			}
		}
		return false;
	}
}

package com.zdmoney.manager.utils;

import java.util.Map;

import javax.servlet.http.HttpSession;

/** 
 *
 * @author 00225641
 * @version 2014年12月20日 下午3:04:16 
 */
public class PermissionUtil {

	/**
	 * 根据配置的权限url判断该用户是否有权限访问
	 * @param session
	 * @param permUrl
	 * @return
	 */
	public static boolean isHavePermission(HttpSession session,String permUrl){
		Map<String, Object> permMap = (Map<String, Object>) session.getAttribute("permMap");
		if(permMap.containsValue(permUrl)){
			return true;
		}
		return false;
	}
}

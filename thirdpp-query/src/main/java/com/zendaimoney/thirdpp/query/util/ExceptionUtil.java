package com.zendaimoney.thirdpp.query.util;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * Exception工具类
 * 
 */
public class ExceptionUtil {

	/**
	 * 返回错误信息字符串
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public static String getExceptionMessage(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	
	
	/**
	 * 返回错误信息部分字符串,这个字符串需要入库
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public static String getExceptionPartyMessage(Exception e) {
		String s = null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		s = sw.toString().substring(0,600);
		return s;
	}
	
	

}

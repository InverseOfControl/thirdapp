package com.zdmoney.manager.utils;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/****
 * 用于JSON字符串转换用的工具类
 * @author Administrator
 */
public class JsonDateFormatUtil {
	
	private static final String format_type1="yyyy-MM-dd HH:mm:ss";//转换
	private static final String format_type2="yyyy-MM-dd";//转换
	/***
	 * 
	 * @param config
	 */
	public static void formatDateForJsonConfig_type1(JsonConfig  config)
	{
			//处理oracle.sql.TIMESTAMP
		    config.registerJsonValueProcessor(oracle.sql.TIMESTAMP.class,new JsonValueProcessor() {
	    	public Object processObjectValue(String key, Object value,JsonConfig arg2){
	    	if(value==null){return "";};
	        String rtn_str = "";
		    	if (value instanceof oracle.sql.TIMESTAMP ) {
				            try {
					    		oracle.sql.TIMESTAMP tst = (oracle.sql.TIMESTAMP)value;  
					            Date  date = new Date(tst.timestampValue().getTime());  
					            rtn_str = new SimpleDateFormat(format_type1).format((Date)date);
							} catch (SQLException e) {
								e.printStackTrace();
							}  
				    return rtn_str;
		    	}
	    	return value.toString();
	    	}
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return null;
			}});
		    
		    //处理java.sql.Timestamp
			config.registerJsonValueProcessor(java.sql.Timestamp.class,new JsonValueProcessor() {
		    	public Object processObjectValue(String key, Object value,JsonConfig arg2){
			    if(value==null){return "";};
			    String rtn_str = "";
		    	if (value instanceof java.sql.Timestamp) {
		        java.sql.Timestamp tst= (java.sql.Timestamp)value;   
		        Date  date = new Date(tst.getTime());  
		        rtn_str = new SimpleDateFormat(format_type1).format(date);
		    	return rtn_str;
		    	}
		    	return value.toString();
		    	}
				@Override
				public Object processArrayValue(Object arg0, JsonConfig arg1) {
					return null;
				}});
		
		    
		    //处理java.util.Date
			config.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() {
		    	public Object processObjectValue(String key, Object value,JsonConfig arg2){
			    if(value==null){return "";};
			    String rtn_str = "";
		    	if (value instanceof Date ) {
		    	String str = new SimpleDateFormat(format_type1).format((Date) value);
		    	return rtn_str;
		    	}
		    	return value.toString();
		    	}
				@Override
				public Object processArrayValue(Object arg0, JsonConfig arg1) {
					return null;
				}});
			
		   
	}
	public static void formatDateForJsonConfig_type2(JsonConfig  config)
	{
			//处理oracle.sql.TIMESTAMP
		    config.registerJsonValueProcessor(oracle.sql.TIMESTAMP.class,new JsonValueProcessor() {
	    	public Object processObjectValue(String key, Object value,JsonConfig arg2){
	    	if(value==null){return "";};
	        String rtn_str = "";
		    	if (value instanceof oracle.sql.TIMESTAMP ) {
				            try {
					    		oracle.sql.TIMESTAMP tst = (oracle.sql.TIMESTAMP)value;  
					            Date  date = new Date(tst.timestampValue().getTime());  
					            rtn_str = new SimpleDateFormat(format_type2).format((Date)date);
							} catch (SQLException e) {
								e.printStackTrace();
							}  
				    return rtn_str;
		    	}
	    	return value.toString();
	    	}
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				return null;
			}});
		    
		    //处理java.sql.Timestamp
			config.registerJsonValueProcessor(java.sql.Timestamp.class,new JsonValueProcessor() {
		    	public Object processObjectValue(String key, Object value,JsonConfig arg2){
			    if(value==null){return "";};
			    String rtn_str = "";
		    	if (value instanceof java.sql.Timestamp) {
		        java.sql.Timestamp tst= (java.sql.Timestamp)value;   
		        Date  date = new Date(tst.getTime());  
		        rtn_str = new SimpleDateFormat(format_type2).format(date);
		    	return rtn_str;
		    	}
		    	return value.toString();
		    	}
				@Override
				public Object processArrayValue(Object arg0, JsonConfig arg1) {
					return null;
				}});
		
		    
		    //处理java.util.Date
			config.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() {
		    	public Object processObjectValue(String key, Object value,JsonConfig arg2){
			    if(value==null){return "";};
			    String rtn_str = "";
		    	if (value instanceof Date ) {
		    	String str = new SimpleDateFormat(format_type2).format((Date) value);
		    	return rtn_str;
		    	}
		    	return value.toString();
		    	}
				@Override
				public Object processArrayValue(Object arg0, JsonConfig arg1) {
					return null;
				}});
			
		   
	}
	

	   
}

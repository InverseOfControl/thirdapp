package com.zdmoney.manager.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.omg.CORBA.portable.ApplicationException;

 

public class ConverNullString {
	public void nullConverNullString(Object obj) throws ApplicationException { 
		if (obj != null) { 

			Class classz = obj.getClass(); 
			// 获取所有该对象的属性值 
			Field fields[] = classz.getDeclaredFields(); 
			// 遍历属性值，取得所有属性为 "" 值的 
			for (Field field : fields) { 
				try { 
					if (Modifier.isStatic(field.getModifiers())){
						continue;
					}
					java.lang.reflect.Type t = field.getGenericType(); 
					if (!t.toString().equals("boolean")) { 
						Method m = classz.getMethod("get" 
								+ change(field.getName())); 
						Object name = m.invoke(obj);// 调用该字段的get方法 
						if (name == "") { 

							Method mtd = classz.getMethod("set" 
									+ change(field.getName()), 
									new Class[] { String.class });// 取得所需类的方法对象 
							mtd.invoke(obj, new Object[] { null });// 执行相应赋值方法 
						} 
					} 

				} catch (Exception e) { 
					e.printStackTrace(); 

				} 
			} 
		} 
	}
		/** 
		 * @param src 
		 *            源字符串 
		 * @return 字符串，将src的第一个字母转换为大写，src为空时返回null 
		 */ 
		public static String change(String src) { 
			if (src != null) { 
				StringBuffer sb = new StringBuffer(src); 
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0))); 
				return sb.toString(); 
			} else { 
				return null; 
			} 

		}
	}

package com.zendaimoney.trust.channel.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.StringUtils;

import com.zendaimoney.trust.channel.annotations.CmbAnnotation;
import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.entity.cmb.Header;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.pub.vo.req.TrustBizReqVo;

/**
 * 对象转换工具
 * @author mencius
 *
 */
public class ConvertUtils {
	
	// 十六进制字符编码
	private static String hexString="0123456789ABCDEF"; 
	
	// 日志工具类
	private static final LogPrn logger = new LogPrn(ConvertUtils.class);
	
	/**
	 * 拼接报文
	 * @param header 报文头
	 * @param bizReqVo 对象数据
	 * @return message
	 * @throws UnsupportedEncodingException 
	 */
	public static String generateMessage(Header header, TrustBizReqVo bizReqVo) throws UnsupportedEncodingException {
		
		StringBuffer message = new StringBuffer();
		// 招商银行签名私钥
		String key = ConfigUtil.getInstance().getCmbConfig().getMacKey();
		
		// 由业务请求对象转换为报文体
		String body = objToMessage(bizReqVo);
		
		// 修改报文头属性值
		header.setLength(String.valueOf(body.length()));
		header.setMac(AnxiMacUtils.Ansi_X99_Get_MAC(key, null, strToHex(body, Constants.ENCODE_GBK)));

		// 拼接报文头
		message.append(objToMessage(header));
		// 拼接报文体
		message.append(body);
		return message.toString();
	}
	
	/**
	 * 
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static String objToMessage(Object request) {
		
		try {
			// 获取当前传入对象的class
			Class source = request.getClass();
			
			// 获取该对象的属性
			Field[] fields = source.getDeclaredFields();
			
			// 对象实现序列化后 有默认的一个多余属性
			String[] values = new String[fields.length - 1];
			
			// 遍历属性集合
			for (Field field : fields) {
				
				fieldDeal(request, field, values);
				
			}
			return toPackage(values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new PlatformException(PlatformErrorCode.DTO_DECODE_ERROR, PlatformErrorCode.DTO_DECODE_ERROR.getDefaultMessage());
		}
	}
	
	/**
	 * 对属性进行验证及补位等处理
	 * @param request
	 * @param field
	 * @throws Exception 
	 */
	public static void fieldDeal(Object request, Field field, String[] values) throws Exception {
		
		// 设置为私有域可访问权限
		field.setAccessible(true);
		// 获取属性注解对象
		CmbAnnotation annotation = field.getAnnotation(CmbAnnotation.class);
		
		if (annotation == null) {
			return;
		}
		// 报文位置索引
		int index = annotation.index();
		// 字段占位长度
		int length = annotation.length();
		// 是否在右侧补位
		boolean right = annotation.rightFill();
		// 填充物
		String filler = annotation.filler();
		
		// 是否需要转hex(16进制)
		boolean hex = annotation.hex();
		
		// 获得当前对象属性值
		String value = getFieldValue(field, request);
		
		// 补位后的属性值
		String target = null;
		
		// 判断是否需要转换16进制
		if (hex) {
			target = strToHex(value, Constants.ENCODE_GBK);
		} else {
			target = value;
		}
		// 判断数据长度是否超过规定的长度，如果超过则抛异常返回
		if (target.length() > length) {
			throw new PlatformException(PlatformErrorCode.VALIDATE_OVERLENGTH, PlatformErrorCode.VALIDATE_OVERLENGTH.getDefaultMessage());
		}
		
		// 判断是否在右侧补位
		if (right) {
			// 判断属性值域内是否包含中文字符
			if (ThirdppStrUtil.isChinese(target)) {
				// 如果包含N个中文字符(一个中文 占两位长度)，则值域长度减少N位(length = length - N)  
				length = length - ThirdppStrUtil.lengthOfChinese(target);
			}
			// 右侧补 空字符串 至达到长度length
			target = StringUtils.rightPad(target, length, filler);
		} else {
			// 左侧补 0 至达到长度length
			target = StringUtils.leftPad(target, length, filler);
		}
		// 将处理后的属性值 存储在数组内
		values[index - 1] = target;
	}
	
	/**
	 * 组装报文内容(将对象属性值域数组拼接成字符串)
	 * @param values
	 * @return message 报文
	 */
	public static String toPackage(String[] values) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < values.length; i ++) {
			buffer.append(values[i]);
		}
		return buffer.toString();
	}
	
	
	/**
	 * 报文字符串转换对象
	 * @param message
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	public static void messageToObj(Message message, Object resp) throws IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
		
		String messageStr = message.getMessage();
		
		// 如果报文为空 则抛异常结束处理
		if (StringUtils.isBlank(messageStr)) {
			throw new PlatformException(PlatformErrorCode.CMB_PARSE_ERROR, PlatformErrorCode.CMB_PARSE_ERROR.getDefaultMessage());
		}
		// 获取当前传入对象的class
		Class source = resp.getClass();
		// 获取该对象的属性
		Field[] fields = source.getDeclaredFields();
		Field[] sortFields = new Field[fields.length - 1];
		
		for (Field field : fields) {
			// 设置为私有域可访问权限
			field.setAccessible(true);
			// 获取属性注解对象
			CmbAnnotation annotation = field.getAnnotation(CmbAnnotation.class);
			
			if (annotation == null) {
				continue;
			}
			// 报文位置索引
			int index = annotation.index();
			
			sortFields[index - 1] = field;
		}
		
		for (Field field : sortFields) {
			// 设置为私有域可访问权限
			field.setAccessible(true);
			// 获取属性注解对象
			CmbAnnotation annotation = field.getAnnotation(CmbAnnotation.class);
			
			if (annotation == null) {
				continue;
			}
			// 值域长度
			int length = annotation.length();
			
			boolean hex = annotation.hex();
			String value = messageStr.substring(0, length);
			
			// 如果获得的是16进制则转换数据进制格式
			if (hex) {
				
				// 由16进制转换为字符串 
				value = hexToStr(value.trim(), Constants.ENCODE_GBK);
			}
			messageStr = messageStr.substring(length, messageStr.length());
			
			setFieldValue(field, resp, value);
		}
		
		// 更新被截取后的报文内容
		message.setMessage(messageStr);
	}
	
	/**
	 * 报文字符串转换对象
	 * @param message
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("rawtypes")
	public static void messageToObj(String message, Object resp) throws IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
		
		// 获取当前传入对象的class
		Class source = resp.getClass();
		// 获取该对象的属性
		Field[] fields = source.getDeclaredFields();
		Field[] sortFields = new Field[fields.length - 1];
		
		for (Field field : fields) {
			// 设置为私有域可访问权限
			field.setAccessible(true);
			// 获取属性注解对象
			CmbAnnotation annotation = field.getAnnotation(CmbAnnotation.class);
			
			if (annotation == null) {
				continue;
			}
			// 报文位置索引
			int index = annotation.index();
			
			sortFields[index - 1] = field;
		}
		
		for (int i = 0; i < sortFields.length; i ++){
			
			Field field = sortFields[i];
			
			// 设置为私有域可访问权限
			field.setAccessible(true);
			// 获取属性注解对象
			CmbAnnotation annotation = field.getAnnotation(CmbAnnotation.class);
			
			if (annotation == null) {
				continue;
			}
			// 值域长度
			int length = annotation.length();
			
			// message 截取开始位置
			int subStart = length;
			
			// 是否为十六进制
			boolean hex = annotation.hex();
			// 被截取数值 根据定义的长度
			String value = null;
			
			// 判断当前field是否已遍历到最后一个，如果是 则不需截取字符串
			if (i == sortFields.length - 1) {
				value = message;
			} else {
				value = message.substring(0, length);
			}
			
			// 字符串内包含中文(一个中文 占两位长度)
			if (ThirdppStrUtil.isChinese(value)) { 
				value = message.substring(0, length - ThirdppStrUtil.lengthOfChinese(value));
				
				// message 截取位置向左移动N位 (N个汉字)
				subStart = subStart - ThirdppStrUtil.lengthOfChinese(value);
			}
			// 如果获得的是16进制则转换数据进制格式
			if (hex) {
				
				// 由16进制转换为字符串 
				value = hexToStr(value.trim(), Constants.ENCODE_GBK);
			}
			
			message = message.substring(subStart, message.length());
			// 对对象属性重新赋值
			setFieldValue(field, resp, value);
		}
	}
	
	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 * @param str
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static String strToHex(String str, String encode) throws UnsupportedEncodingException
    {
	    //根据默认编码获取字节数组
	    byte[] bytes=str.getBytes(encode);
	    StringBuilder sb=new StringBuilder(bytes.length*2);
	    //将字节数组中每个字节拆解成2位16进制整数
	    for(int i=0;i<bytes.length;i++)
	    {
		    sb.append(hexString.charAt((bytes[i]&0xf0)>>4));
		    sb.append(hexString.charAt((bytes[i]&0x0f)>>0));
	    }
	    return sb.toString();
    }
    
    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     * @param bytes
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String hexToStr(String bytes, String encode) throws UnsupportedEncodingException
    {
	    ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
	    //将每2位16进制整数组装成一个字节
	    for(int i=0;i<bytes.length();i+=2)
	    baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
	    return new String(baos.toByteArray(), encode);
    }
    
    /**
     * 元转分 输出字符串
     * @param amount
     * @return
     */
    public static String yuanConvertFen(BigDecimal amount) {
		return amount.movePointRight(2).setScale(0, RoundingMode.DOWN).toString();
	}
    
    /**
     * 分转元 输出金额
     * @param str
     * @return
     */
    public static BigDecimal fenConvertYuan(String str) {
    	return new BigDecimal(str).movePointLeft(2).setScale(2, RoundingMode.DOWN);
    }
    
    /**
	 * 获取对象属性值并根据属性类型转换
	 * @param field
	 * @param request
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static String getFieldValue(Field field, Object request) throws IllegalArgumentException, IllegalAccessException{
		// 获得当前对象属性值
		Object value = field.get(request);
		
		String fieldType = field.getType().getSimpleName();
		
		if ("int".equalsIgnoreCase(fieldType)) {
			return String.valueOf(value == null ? "0" : value.toString());
		} else if ("BigDecimal".equalsIgnoreCase(fieldType)) {
			return yuanConvertFen(value == null ? BigDecimal.ZERO : new BigDecimal(value.toString()));
		} else {
			return value == null ? "" : value.toString();
		}
	}
	
	/**
	 * 根据属性类型将数值转换并为对象赋值
	 * @param field
	 * @param request
	 * @param target
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setFieldValue(Field field, Object request, String target) throws IllegalArgumentException, IllegalAccessException{
		
		String fieldType = field.getType().getSimpleName();
		
		if ("int".equalsIgnoreCase(fieldType)) {
			
			field.set(request, Integer.valueOf(target));
		} else if ("BigDecimal".equalsIgnoreCase(fieldType)) {
			field.set(request, fenConvertYuan(target));
		} else {
			field.set(request, target.trim());
		}
		
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
		
//		Header header = new Header();
//		header.setPacketMark("CMBA");
//		header.setMac("779F0A084090EA0D");
//		String message = ConvertUtils.objToMessage(header);
//		logger.info(message);
//		
//		String str = "CMBMB99    1231458742555556                    1234567890                                                                                                                                                      xxxxxxxxxx                                                                                                                                                                                                        ";
//		UsraResp resp = new UsraResp();
//		messageToObj(str, resp);
//		System.out.println(resp);
		
//		System.out.println("message:" + message + "\n, length:" + message.length());
//		
//		String str = message.substring(0, 4);
//		message = message.substring(4, message.length());
//		System.out.println("str: " + str + "\n,length:" + str.length());
//		System.out.println("message:" + message + "\n, length:" + message.length());
		
//		System.out.println(yuanConvertFen(new BigDecimal("100.101")));
//		System.out.println(fenConvertYuan("10010"));
		
//		UsraDetailReq req = new UsraDetailReq();
//		req.setUserNo("1000002"); // 用户号
//		req.setUserName("mencius"); // 用户姓名
//		req.setUserType("P"); // 用户类型
//		req.setIdNo("411421198806123370"); // 证件号码
//		req.setIdType("P01"); // 证件类型
//		req.setMobile("18221374867"); // 手机号码
//		req.setRetCode(Constants.CmbStatus.CMBMB99.getCode());
//		
//		System.out.println(ConvertUtils.objToMessage(req));
		
		String chrgMessage = "通联";
		String hexed = strToHex(chrgMessage, "UTF-8");
		System.out.println(hexed);
	}
}

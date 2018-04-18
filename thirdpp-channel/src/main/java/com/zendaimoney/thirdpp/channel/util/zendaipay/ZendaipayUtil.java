package com.zendaimoney.thirdpp.channel.util.zendaipay;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;


import com.zendaimoney.thirdpp.channel.dto.req.zendaipay.collect.query.CollectQueryReq;
import com.zendaimoney.thirdpp.channel.dto.req.zendaipay.collect.trade.CollectReq;
import com.zendaimoney.thirdpp.channel.util.LogPrn;

/**
 * 证大爱特通道基础处理工具类
 * @author 00233197
 *
 */
public class ZendaipayUtil {
	
	// 日志工具类
	private static final LogPrn logger = new LogPrn(ZendaipayUtil.class);
	private static final int UNIT = 100;
	
	/**
	 * 字符串转换为Unicode
	 * **/
	public static String stringToUnicode(String content) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			stringBuffer.append("\\u" + Integer.toHexString(c));
		}
		return stringBuffer.toString();
	}
	
    /**
     * Unicode转换为字符串
     * **/
	public static String unicodeToString(String content) {
		StringBuffer stringBuffer = new StringBuffer();
		String hex[] = content.split("\\\\u");
		for (int i = 1; i < hex.length; i++) { 
			int data = Integer.parseInt(hex[i], 16); 
			stringBuffer.append((char) data);
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 单位由 元 转换成 分
	 * @param amount
	 * @return
	 */
	public static BigDecimal yuanConvertFen(BigDecimal amount) {
		BigDecimal decUnit = BigDecimal.valueOf(UNIT);
		return amount.multiply(decUnit).setScale(0);
	}
	
	/**
	 * 实现Map<String,String> toString方法
	 */
	public static String mapToString(Map<String, String> messageMap){
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<String,String>> iterator = messageMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,String> entry = iterator.next();
			sb.append(entry.getKey()).append("=");
			if(iterator.hasNext()){
				sb.append(entry.getValue()).append(",");
			}else{
				sb.append(entry.getValue());
			}
		}
		return sb.toString();
	}
	
	//对象转换为String
	public static String emptyToString(Object object){
		if(object == null || StringUtils.isBlank(object.toString())){
			return "";
		}
		return object.toString().trim();
	}
	
	//字符串转换为二进制数组
	public static byte[] messageToByte(String message){
		try {
			return message.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将参与签名的字段内容按顺序连接起来，组成一个签名字符串明文
	 * @param signFieldsArray
	 * @param messageMap
	 * @return
	 */
	public static String buildSignMessage(CollectReq req,String key){
		
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("transNo", req.getTransNo());
		parameters.put("borrowNo", req.getBorrowNo());
		parameters.put("offerAmount", req.getOfferAmount());
		
		
		StringBuilder signBuilder = new StringBuilder();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			String k = entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)  && !"sign".equals(k) && !"key".equals(k)) {
				signBuilder.append(k + "=" + v + "&");
			}
		}
		logger.info("传递的参数为:"+signBuilder.toString());
		//拼接key进行MD5签名
		signBuilder.append("key=" + key);
		return signBuilder.toString();
	
		
	}
	public static String buildQuerySignMessage(CollectQueryReq req,String key){
		
		SortedMap<String, Object> parameters = new TreeMap<String, Object>();
		parameters.put("transNo", req.getTransNo());
		
		
		StringBuilder signBuilder = new StringBuilder();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			String k = entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v)  && !"sign".equals(k) && !"key".equals(k)) {
				signBuilder.append(k + "=" + v + "&");
			}
		}
		logger.info("传递的参数为:"+signBuilder.toString());
		//拼接key进行MD5签名 ZDAT123456
		signBuilder.append("key=" + key);
		return signBuilder.toString();
	
		
	}
	
	
	/**
	 * 数据签名
	 * **/
	public static String Sign(String signBuilder) throws Exception{
		
		String sign = MD5Util.md5(signBuilder.toString(), "UTF-8").toUpperCase();
		return sign;
	}
	
	/**
	 * 判断是否需要Unicode
	 * @param fieldName
	 * @return
	 */
	public static boolean isUnicode(String fieldName){
		boolean verifyResult=false;

		String[] unicodeArray={"usrName","priv1","userNme","message","purpose"};

		for (String propertyName : unicodeArray) {
		  if(propertyName.equals(fieldName)){
			  verifyResult=true;
			  break;
		  }
		}	
		return verifyResult;
	}
	
	/**
	 * @param parametersMap 
	 * @return
	 */
	public String getParameter(Map<String, String> parametersMap) {
		StringBuffer sb = new StringBuffer();
		for (String key : parametersMap.keySet()) {
			sb.append(key + "=" +parametersMap.get(key)+"&");
		}
		return sb.substring(0,sb.length()-1);
	}
	
	public static String generateMethodName(String type, String fieldName) {
		StringBuffer result = new StringBuffer();
		char[] temp = fieldName.toCharArray();
		temp[0] = Character.toUpperCase(temp[0]);
		return result.append(type).append(temp).toString();
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(String xmlValue) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new InputSource(new StringReader(xmlValue)));
		Element root = document.getRootElement();
        // 获取所有子元素
        List<Element> childList = root.elements();
        Map<String, String> messageMap = new HashMap<String, String>();
        for (Element element : childList) {
        	messageMap.put(element.getName(), element.getTextTrim());
        }
        
		return messageMap;
	}
	
	public static String unicodeStringHandle(String fieldName, String value){
		boolean unicode = isUnicode(fieldName);
		if(unicode){
			value= unicodeToString(value);
		}
		return value;
	}
	
	
	
	public static void main(String[] args) {
	}
	
}

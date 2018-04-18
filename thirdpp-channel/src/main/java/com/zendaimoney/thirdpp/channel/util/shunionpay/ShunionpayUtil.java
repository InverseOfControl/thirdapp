package com.zendaimoney.thirdpp.channel.util.shunionpay;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;

import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.Constants;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.channel.util.MD5DigestUtils;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

/**
 * 银联基础处理工具类
 * @author mencius
 *
 */
public class ShunionpayUtil {
	
	// 日志工具类
	private static final LogPrn logger = new LogPrn(ShunionpayUtil.class);
	private static final int UNIT = 100;
	
	/**银联代收成功交易状态**/
	public static final String CHINAUNIONPAY_COLLECTION_SUCCESS_STATUS="1001";
	/**银联代收成功交易响应码**/
	public static final String CHINAUNIONPAY_COLLECTION_SUCCESS_CODE="00";
	/**银联代付成功交易状态**/
	public static final String CHINAUNIONPAY_PAYING_SUCCESS_STATUS="s";
	/**银联代付中间交易状态**/
	public static final String CHINAUNIONPAY_PAYING_UNKNOW_STATUS="2,3,4,5,7,8";
	/**银联代付失败交易状态**/
	public static final String CHINAUNIONPAY_PAYING_FAIL_STATUS="6,9";

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
	public static String buildSignMessage(String[] signFieldsArray, Map<String,String> messageMap){
		StringBuffer messageString=new StringBuffer();
		for (String fieldName : signFieldsArray) {
			String initValue = messageMap.get(fieldName);
			messageString.append(emptyToString(initValue));
		}
		return messageString.toString();
		
	}
	
	public static String cardTypeRemake(String fieldName,String value){
		if(("certType".equalsIgnoreCase(fieldName))&&(!"".equals(value))){
			value="0"+value;
		}
		return value;
	}
	
	
	/**
	 * 银联数据签名
	 * **/
	public static String Sign(String plainContext, SysThirdChannelInfo channelInfo) throws Exception{
		
		// 获取银联通道证书签名路径
		String path= ConfigUtil.getInstance().getShunionpayConfig().getKeyPath() + channelInfo.getCertName();
		//获取keyFile
		String keyFilePath=Thread.currentThread().getContextClassLoader().getResource(path).getPath();
		PrivateKey signKey=new PrivateKey();
		
		// 根据银联通道的商户ID，签名文件路径  ，构建银联私Key对象
		boolean signTrue = signKey.buildKey(channelInfo.getMerchantNo(), 0, keyFilePath);
		if(!signTrue){
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR, "未找到业务系统相对应的MerPrK文件,请检查!!!");
		}
		SecureLink signSecureLink=new SecureLink(signKey);
		String signDataBase64String=new String(Base64.encode(plainContext.getBytes("GBK")));
		String chkValue=signSecureLink.Sign(signDataBase64String);
		return chkValue;
	}
	
	/**
	 * 银联数据签名
	 * 
	 */
	public static String Sign(String plainContext, SysThirdChannelInfo channelInfo, String path) throws Exception{
		
		//获取keyFile
		String keyFilePath=Thread.currentThread().getContextClassLoader().getResource(path).getPath();
		PrivateKey signKey=new PrivateKey();
		
		// 根据银联通道的商户ID，签名文件路径  ，构建银联私Key对象
		boolean signTrue = signKey.buildKey(channelInfo.getMerchantNo(), 0, keyFilePath);
		if(!signTrue){
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR, "未找到业务系统相对应的MerPrK文件,请检查!!!");
		}
		SecureLink signSecureLink=new SecureLink(signKey);
		String signDataBase64String=new String(Base64.encode(plainContext.getBytes("GBK")));
		String chkValue=signSecureLink.Sign(signDataBase64String);
		return chkValue;
	}
	
	/**
	 * 验证数据签名
	 * **/
	public static boolean verifySign(String plainContext,String chkVal) throws Exception{
		  PrivateKey verifyKey=new PrivateKey();
		  String path=ConfigUtil.getInstance().getShunionpayConfig().getKeyPath() + ConfigUtil.getInstance().getShunionpayConfig().getPublicKey();
		  //获取keyFile
		  String publicKeyPath=Thread.currentThread().getContextClassLoader().getResource(path).getPath();
		  boolean isTrue = verifyKey.buildKey(ShunionpayConstants.PGID, 0, publicKeyPath);
		  if(!isTrue){
			  throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR, "未找到对应的PgPubk文件,请检查!!!");
		  }
		  SecureLink verifySecureLink=new SecureLink(verifyKey);
		  String signDataBase64String=new String(Base64.encode(plainContext.getBytes("GBK")));
		  boolean flag = verifySecureLink.verifyAuthToken(signDataBase64String, chkVal);
	      return flag;	
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
	
	/**
	 * 第三方响应数据封装为Map对象
	 * @throws Exception 
	 * **/
	public static Map<String,String> getResponseMap(String message, ChannelCategory requestType) throws PlatformException{
		Map<String, String> messageMap = new HashMap<String, String>();
		
		//上海银联代付查询
		if(ChannelCategory.BATCH_QUERY == requestType){
			String[] splitData = message.split("\\|",17);
			String[] keyArray = new String[]{"code","merId","merDate","merSeqId","cpDate","cpSeqId","bankName","cardNo","usrName","transAmt","feeAmt","prov","city","purpose","stat","backDate","chkValue"};
			for (int i=0; i<keyArray.length; i++) {
				String value=emptyToString(splitData[i]);
				messageMap.put(keyArray[i],value.replaceAll("\\|",""));
			}
		}else if(ChannelCategory.QUERY == requestType){
			String[] oneSplit = message.split("&");
			for (String string : oneSplit) {
				String[] twoSplit = string.split("=", 2);
				messageMap.put(twoSplit[0], unicodeStringHandle(twoSplit[0], emptyToString(twoSplit[1])));
			}
		}else if(ChannelCategory.TRADE == requestType){
			String[] oneSplit = message.split("&");
			for (String string : oneSplit) {
				String[] twoSplit = string.split("=", 2);
				messageMap.put(twoSplit[0], unicodeStringHandle(twoSplit[0], emptyToString(twoSplit[1])));
			}
		} else if (ChannelCategory.AUTH == requestType){
			
			if (!message.contains("&")) {
				// 响应报文中只有描述信息的情况
				messageMap.put("respmsg", message);
			} else {
				
				String[] oneSplit = message.split("&");
				for (String string : oneSplit) {
					putAuthMap(messageMap, string, "=");
				}
				
			}
		}else{
			String[] oneSplit = message.split("&");
			for (String string : oneSplit) {
				String[] twoSplit = string.split("=", 2);
				messageMap.put(twoSplit[0], unicodeStringHandle(twoSplit[0], emptyToString(twoSplit[1])));
			}
		}
		return messageMap;
	}
	
	/**
	 * 
	 * @param messageMap 存储对象Map
	 * @param value 待解析对象
	 * @param symbolSplit 分隔符字符
	 */
	public static void putAuthMap(Map<String, String> messageMap, String value, String symbolSplit){
		
		if (!StringUtils.isBlank(value)) {
			
			String[] splitValue = value.split(symbolSplit, 2);
			
			// 银联认证响应信息包含bizType信息，与common定义的bizType冲突，需要转换为 thirdBizType
			if ("bizType".equalsIgnoreCase(splitValue[0])) {
				if (splitValue.length == 2) {
					messageMap.put("thirdBizType", unicodeStringHandle(splitValue[0], emptyToString(splitValue[1])));
				} else {
					
					messageMap.put("thirdBizType", "");
				}
			} else {
				
				if (splitValue.length == 2) {
					
					messageMap.put(splitValue[0], unicodeStringHandle(splitValue[0], emptyToString(splitValue[1])));
				} else {
					
					messageMap.put(splitValue[0], "");
				}
			}
		}
		
	}
	
	
	
	public static String unicodeStringHandle(String fieldName, String value){
		boolean unicode = isUnicode(fieldName);
		if(unicode){
			value= unicodeToString(value);
		}
		return value;
	}
	
	/**
	 * 私钥拼接到待签名字符串后面，形成新的字符串，利用 MD5 的签名函数对这个新的字符串进行签名运算，从而得到 32位签名结果字符串
	 * signMethod与signature 两个参数外
	 * @param signMessage
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String shunionAuthSign(Map<String, String> signMap, String secretKey) throws UnsupportedEncodingException {
		
		StringBuffer buffer = new StringBuffer();
		List<String> fields = new ArrayList<String>();
		fields.addAll(signMap.keySet());
		Collections.sort(fields, AUTH_FIELDSORT_ASC); // 对签名字段进行升序排列
//		signMethod与signature
		for (String field : fields) {
			
			if ("signMethod".equalsIgnoreCase(field) || "signature".equalsIgnoreCase(field)) {
				continue;
			}
			
			if (!StringUtils.isBlank(signMap.get(field))) {
				
				if (fields.indexOf(field) != (fields.size() - 1)) {
					buffer.append(field).append("=").append(signMap.get(field)).append("&");
				} else {
					buffer.append(field).append("=").append(signMap.get(field));
				}
			}
		}
		
		logger.info("AuthSign: " + buffer);
		if (buffer.lastIndexOf("&") == buffer.length() - 1){
			return MD5DigestUtils.sign(buffer.substring(0, buffer.length() - 1), secretKey);
		}
		
		return MD5DigestUtils.sign(buffer.toString(), secretKey);
		
	}
	
	/**
	 * 银联实名认证参数升序排序
	 */
	public static Comparator<String> AUTH_FIELDSORT_ASC = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			
			if (o1.compareTo(o2) >= 0) {
				return 1;
			}
			return -1;
		}
	};
	
	
	public static void main(String[] args) {
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("appSysId", "90000");
		paramMap.put("bizType", "00");
		paramMap.put("cardNo", "6226620607792207");
		paramMap.put("cardPhone", "");
		paramMap.put("certNo", "231002198903302089");
		paramMap.put("certType", "01");
		paramMap.put("channelId", "");
		paramMap.put("dcType", "0");
		paramMap.put("email", "");
		paramMap.put("merId", "");
		paramMap.put("merName", "");
		paramMap.put("mobile", "18221374856");
		paramMap.put("pin", "");
		paramMap.put("save", "false");
		paramMap.put("usrName", "刘敏");
		paramMap.put("usrSysId", "");
		paramMap.put("validWayId", "");
		paramMap.put("signMethod", "MD5");
		paramMap.put("signature", "");
		
		try {
			String signature = shunionAuthSign(paramMap, "");
			
			System.out.println(signature.length() + "位 : " + signature);
		} catch (UnsupportedEncodingException e) {
		}
	}
	
}

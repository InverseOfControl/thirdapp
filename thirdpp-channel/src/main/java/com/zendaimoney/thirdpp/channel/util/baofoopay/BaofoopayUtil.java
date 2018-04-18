package com.zendaimoney.thirdpp.channel.util.baofoopay;

import com.zendaimoney.thirdpp.channel.entity.SysThirdChannelInfo;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * 基础处理工具类
 * @author 00233197
 *
 */
public class BaofoopayUtil {
	
	// 日志工具类
	private static final LogPrn logger = new LogPrn(BaofoopayUtil.class);
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
	 * 宝付数据签名
	 * **/
	public static String Sign(String plainContext, SysThirdChannelInfo channelInfo) throws Exception{
		String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        plainContext = s + plainContext;
		// 获取宝付通道证书签名路径
		String path= ConfigUtil.getInstance().getBaofoopayConfig().getKeyPath() + channelInfo.getCertName();
		//获取keyFile
		String keyFilePath=Thread.currentThread().getContextClassLoader().getResource(path).getPath();

		String pfxPwd = channelInfo.getCertPwd();
		String base64str = SecurityUtil.Base64Encode(plainContext);
		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,keyFilePath,pfxPwd);
		return data_content;

	}
	

	
	/**
	 * 验证数据签名
	 * **/
	public static String verifySign(String plainContext,String chkVal) throws PlatformException{

		String path=ConfigUtil.getInstance().getBaofoopayConfig().getKeyPath() + ConfigUtil.getInstance().getBaofoopayConfig().getPublicKey();
		//获取keyFile
		String publicKeyPath=Thread.currentThread().getContextClassLoader().getResource(path).getPath();
		String postString = null;
		try {
			postString = RsaCodingUtil.decryptByPubCerFile(plainContext, publicKeyPath);
			if(StringUtils.isEmpty(postString)){//判断解密是否正确。如果为空则宝付公钥不正确
				logger.error("=====解密失败,检查解密公钥是否正确！");
				throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,"=====解密失败,检查解密公钥是否正确！");
			}
		}catch(Exception e){
			logger.error("=====解密失败,检查返回报文是否正确！");
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,"=====解密失败,检查返回报文是否正确！");
		}
		try {
            postString = SecurityUtil.Base64Decode(postString);
        }catch (Exception e){
			logger.error("=====解密失败,Base64转换失败！");
            throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,"=====解密失败,Base64转换失败！");
        }
		return postString;
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
        message = JXMConvertUtil.XmlConvertJson(message);
        Map<String,String> returnData = new HashMap<>();
        Map<String,Object> arrayData = JXMConvertUtil.JsonConvertHashMap(message);
        for (String in : arrayData.keySet()) {
            //map.keySet()返回的是所有key的值
            Object str = arrayData.get(in);//得到每个key多对用value的值
            if(str!=null){
                returnData.put(in,String.valueOf(str));
            }
        }
		return returnData;
	}
    /**
     * 第三方响应数据封装为Map对象
     * @throws Exception
     * **/
    public static Map<String,String> getResponseJson(String message, ChannelCategory requestType) throws PlatformException{
        message = JXMConvertUtil.XmlConvertJson(message);
        Map<String,String> returnData = new HashMap<>();
        Map<String,Object> arrayData = JXMConvertUtil.JsonConvertHashMap(message);
        for (String in : arrayData.keySet()) {
            //map.keySet()返回的是所有key的值
            Object str = arrayData.get(in);//得到每个key多对用value的值
            if(str!=null){
                returnData.put(in,String.valueOf(str));
            }
        }
        return returnData;
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

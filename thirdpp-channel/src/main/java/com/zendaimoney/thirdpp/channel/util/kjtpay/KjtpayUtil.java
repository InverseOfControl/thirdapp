package com.zendaimoney.thirdpp.channel.util.kjtpay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.allinpay.XmlTools;
import com.itrus.cryptorole.CryptoException;
import com.itrus.cryptorole.NotSupportException;
import com.itrus.cryptorole.bc.RecipientBcImpl;
import com.itrus.cryptorole.bc.SenderBcImpl;
import com.itrus.util.Base64;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.pub.vo.BizReqVo;
import com.zendaimoney.thirdpp.channel.util.ConfigUtil;
import com.zendaimoney.thirdpp.channel.util.ThirdPPCacheContainer;
import com.zendaimoney.thirdpp.common.enums.ChannelCategory;

public class KjtpayUtil {

	private static final int UNIT = 100;

	//public static SenderBcImpl sender;
    //public static RecipientBcImpl recipient;
    public static String pfxFileName = "D:/testDemoCer/200000055990准生产.pfx";
    public static String certFileName = "D:/opt/pay/config/basis/mag/cafiles/123.cer";
    public static String keyPassword = "123456";

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    /**
     * 转码
     * @param sArray
     * @return
     */
      public static Map<String, String> encode(Map<String, String> sArray) {

          Map<String, String> result = new HashMap<String, String>();

          if (sArray == null || sArray.size() <= 0) {
              return result;
          }
          String charset = sArray.get("_input_charset");
          for (String key : sArray.keySet()) {
              String value = sArray.get(key);
              if (value != null && !value.equals("") ) {
              	try {
                      value = URLEncoder.encode(value, charset);
                  } catch (UnsupportedEncodingException e) {
                      e.printStackTrace();
                  }
              }
              
              result.put(key, value);
          }

          return result;
      }
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

   

    public static String buildRequest(String merId,Map<String, String> sPara,String keyFilePath,String certFilePath,String certPwd) throws Exception {
      	SenderBcImpl sender = ThirdPPCacheContainer.kjtSenderMap.get(merId);
    	if(sender==null){
			sender = initSender(merId,keyFilePath,certFilePath,certPwd);
    	}
    	String prestr = createLinkString(sPara, false); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = sender.signMessage(prestr);
        return mysign;
    }

    /**
     * 生成要请求给钱包的参数数组
     *
     * @param sParaTemp         请求前的参数数组
     * @return                  要请求的参数数组
     */
    public static Map<String, String> buildRequestPara(String merId,Map<String, String> sParaTemp,String signType, String key,String inputCharset,String keyFilePath,String certFilePath,String certPwd) throws Exception {
    	// 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        if(StringUtils.isBlank(signType))return sPara;
        // 生成签名结果
        String mysign = buildRequest(merId,sPara,keyFilePath,certFilePath,certPwd);
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", signType);

        return encode(sPara);
    }
    public static SenderBcImpl initSender(String merId,String keyFilePath,String certFilePath,String certPwd) throws Exception{

		SenderBcImpl sender = new SenderBcImpl();
		//RecipientBcImpl recipient = new RecipientBcImpl();
    	
		sender.initCertWithKey(keyFilePath, certPwd);
		//recipient.initCertWithKey(keyFilePath, certPwd);
		
		InputStream streamCert = new FileInputStream(certFilePath);
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		X509Certificate X509Cert = (X509Certificate) factory.generateCertificate(streamCert);
		sender.addRecipientCert(X509Cert);

		ThirdPPCacheContainer.kjtSenderMap.put(merId,sender);
		//ThirdPPCacheContainer.kjtRecipientMap.put(merId,recipient);
		return sender;

    }
    //天威加密
    public static String encryptData(String merId,String keyFilePath,String certFilePath,String certPwd,String oriMessage,String inputCharset)throws Exception{

		SenderBcImpl sender = ThirdPPCacheContainer.kjtSenderMap.get(merId);

    	if(sender==null){
			sender = initSender(merId,keyFilePath,certFilePath,certPwd);
    	}
    	
    	String str = null;
    	byte[] encryMsg = null;
		encryMsg = sender.encryptMessage(oriMessage.getBytes(inputCharset));

    	str = Base64.encode(encryMsg);
    	return str;
    }
    //天威解密
    /*public static String decryptData(String oriMessage,String inputCharset){
    	String str = null;
    	byte[] decryMsg = null;
		try {
			decryMsg = recipient.decryptMessage(Base64.decode(oriMessage.getBytes(inputCharset)));
			str = new String(decryMsg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CryptoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
    }*/
    
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(String xmlValue) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new InputSource(new StringReader(xmlValue)));
		Element root = document.getRootElement();
        // 获取所有子元素
        List<Element> childList = root.elements();
        Map<String, String> messageMap = new TreeMap<String, String>();
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
		
		if(ChannelCategory.QUERY == requestType){
			String[] oneSplit = message.split("&");
			for (String string : oneSplit) {
				String[] twoSplit = string.split("=", 2);
				messageMap.put(twoSplit[0], emptyToString(twoSplit[1]));
			}
		}else if(ChannelCategory.TRADE == requestType){
			String[] oneSplit = message.split("&");
			for (String string : oneSplit) {
				String[] twoSplit = string.split("=", 2);
				messageMap.put(twoSplit[0], emptyToString(twoSplit[1]));
			}
		}else{
			String[] oneSplit = message.split("&");
			for (String string : oneSplit) {
				String[] twoSplit = string.split("=", 2);
				messageMap.put(twoSplit[0],emptyToString(twoSplit[1]));
			}
		}
		return messageMap;
	}
	
	/**
	 * 第三方响应数据封装为Map对象
	 * @throws Exception 
	 * **/
	public static Map<String,String> getTradeListMap(String message) throws PlatformException{
		Map<String, String> messageMap = new HashMap<String, String>();
		String[] oneSplit = message.split("^");
		for (String string : oneSplit) {
			String[] twoSplit = string.split("=", 2);
			messageMap.put(twoSplit[0], emptyToString(twoSplit[1]));
		}
		
		return messageMap;
	}
	
	//对象转换为String
	public static String emptyToString(Object object){
		if(object == null || StringUtils.isBlank(object.toString())){
			return "";
		}
		return object.toString().trim();
	}
}
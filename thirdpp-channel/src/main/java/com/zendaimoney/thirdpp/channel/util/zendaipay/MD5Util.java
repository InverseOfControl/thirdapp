package com.zendaimoney.thirdpp.channel.util.zendaipay;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/** 单向加密算法MD5&SHA&MAC
 * @description: TODO 
 * @author Somnus
 * date 2015年4月3日 下午4:17:51  
 */
public class MD5Util {
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_SHA = "SHA";
    /** 
     * MAC算法可选以下多种算法 
     *  
     * <pre> 
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512 
     * </pre> 
     */  
    public static final String KEY_MAC = "HmacMD5";
  
    /**
     * md5加密  
     * @param str
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String md5(String str, String charsetname) throws Exception {  
        return encrypt(str, KEY_MD5, charsetname);  
    }  
  
    /**
     * sha加密  
     * @param str
     * @return
     * @throws NoSuchAlgorithmException 
     */
    public static String sha(String str, String charsetname) throws Exception {  
        return encrypt(str, KEY_SHA, charsetname);  
    }  
  
    /** 
     * md5或者sha-1加密 
     *  
     * @param str 
     *            要加密的内容 
     * @param algorithmName 
     *            加密算法名称：md5或者sha-1，不区分大小写 
     * @return 
     * @throws NoSuchAlgorithmException 
     */  
    private static String encrypt(String str, String algorithmName, String charsetname) throws Exception {  
        MessageDigest m = MessageDigest.getInstance(algorithmName);  
        m.update(str.getBytes(charsetname));  
        byte s[] = m.digest();  
        return Hex.encodeHexString(s);  
    }
    
    /** 
     * 初始化HMAC密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String initMacKey(){  
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
            SecretKey secretKey = keyGenerator.generateKey();  
            return Base64.encodeBase64String(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;  
    }  
  
    /** 
     * HMAC加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     * @throws Exception 
     */  
    public static String encryptHMAC(byte[] data, String key) throws Exception{  
        SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(key), KEY_MAC); 
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);
        byte s[] = mac.doFinal(data);  
        return Hex.encodeHexString(s);
    }
  
    public static void main(String[] args) throws Exception {  
        //md5加密测试  
        String md5_1 = md5("Somnus","UTF-8");  
        System.out.println(md5_1);  
        System.out.println("md5 length: " + md5_1.length());  
        //sha加密测试  
        String sha_1 = sha("Somnus","UTF-8");  
        System.out.println(sha_1);  
        System.out.println("sha length: " + sha_1.length());
        //HMAC加密 测试
        String key = initMacKey();  
        System.err.println("Mac密钥:" + key);
        
        String hmac = encryptHMAC("Somnus".getBytes(),key);
        System.out.println(hmac);  
        System.out.println("hmac length: " + hmac.length());
    }
}

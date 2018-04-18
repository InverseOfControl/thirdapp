package com.zendaimoney.thirdpp.channel.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author mencius
 * 
 */
public class MD5DigestUtils {

	public static String encrypt(String src, String encode) {
		src = src.trim();

		String Digest = "";
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance("md5");
			currentAlgorithm.reset();
			byte[] mess = src.getBytes(encode);
			byte[] hash = currentAlgorithm.digest(mess);
			for (int i = 0; i < hash.length; i++) {
				int v = hash[i];
				if (v < 0)
					v = 256 + v;
				if (v < 16)
					Digest += "0";
				Digest += Integer.toString(v, 16).toUpperCase() + "";
			}
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD5加密异常");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Digest;
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String encode) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes(encode)));
		} catch (Exception ex) {
		}
		return resultString;
	}
	
	
	/**
	 * 签名
	 * 
	 * @param signedMsg
	 * @param key
	 * @return
	 */
	public static String sign(String signedMsg, String key) {
		try {
			MessageDigest digit = MessageDigest.getInstance("MD5");
			digit.update((signedMsg + key).getBytes("UTF-8"));
			return byte2hex(digit.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * byte array to hex
	 * 
	 * @param b
	 *            byte array
	 * @return hex string
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp;
		for (int i = 0; i < b.length; i++) {
			stmp = Integer.toHexString(b[i] & 0xFF).toUpperCase();
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString();
	}
	
	/**
	 * md5加密-用友
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("utf-8"));
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) {
		System.out.println(MD5DigestUtils.encrypt("1", "UTF-8"));
		
		System.out.println(MD5DigestUtils.MD5Encode("1", "UTF-8"));
		
		System.out.println(MD5DigestUtils.md5("asd"));
		System.out.println(MD5DigestUtils.sign("asd", ""));
	}
}

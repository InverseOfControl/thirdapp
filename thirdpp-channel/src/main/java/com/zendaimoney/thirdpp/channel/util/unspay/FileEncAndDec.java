package com.zendaimoney.thirdpp.channel.util.unspay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileEncAndDec {

	/**
	 * 解密并解压数据
	 * 
	 * @param input
	 *            待处理数据
	 * @param privateKey
	 *            密钥
	 * @return 解密并解压后的数据
	 */
	public static String decodeAESFileContent(String input, String privateKey) {
		try {
			return AESEncrypt.decrypt(
					new String(DeflaterUtil.uncompress(Base64Util
							.base64Decode(input)), "UTF-8"), privateKey);
		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密并压缩数据
	 * 
	 * @param input
	 *            待处理数据
	 * @param privateKey
	 *            密钥
	 * @return 加密并压缩后的数据
	 */
	public static String encodeAESFileContent(String input, String privateKey) {
		try {
			return new String(
					Base64Util.base64Encode(DeflaterUtil.compress(AESEncrypt
							.encrypt(input, privateKey).getBytes())), "UTF-8");
		} catch (UnsupportedEncodingException ue) {
			ue.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 组装代扣订单数据，商户根据自身系统替换参数list格式及参数名称
	 * 
	 * @param fileContent
	 *            系统订单数据
	 * @param privateKey
	 *            密钥
	 * @return 订单数据组装结果
	 */
	public static String buildFileContent(Map<String, String> fileContent,
			String privateKey) {
		StringBuffer bodyStr = new StringBuffer();

		// 订单号|户名|账号|身份证号|手机号|金额|目的
		bodyStr.append(fileContent.get("orderId")).append("|");
		bodyStr.append(fileContent.get("accName")).append("|");
		bodyStr.append(fileContent.get("accNo")).append("|");
		bodyStr.append(fileContent.get("idCardNo")).append("|");
		bodyStr.append(fileContent.get("phoneNo")).append("|");
		bodyStr.append(fileContent.get("amount")).append("|");
		bodyStr.append(fileContent.get("purpose"));

		return encodeAESFileContent(bodyStr.toString(), privateKey);

	}

	/**
	 * 解析代扣批量查询结果订单数据，商户根据自身系统替换参数list格式及参数名称
	 * 
	 * @param input
	 *            接口返回结果
	 * @param privateKey
	 *            密钥
	 * @return 解密处理过的数据
	 */
	public static List<Map<String, String>> parseFileContent(String input,
			String privateKey) {
		String fileContentStr = decodeAESFileContent(input, privateKey);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String[] reqMessage = fileContentStr.split("\\n");
		for (int i = 0; i < reqMessage.length; i++) {
			String[] result = reqMessage[i].split("\\|");
			// 订单号|户名|账号|身份证号|手机号|金额|目的
			Map<String, String> fileContent = new HashMap<String, String>();
			fileContent.put("orderId", result[0]);
			fileContent.put("amount", result[1]);
			fileContent.put("status", result[2]);
			fileContent.put("msg", result[3]);
			list.add(fileContent);
		}

		return list;
	}
}

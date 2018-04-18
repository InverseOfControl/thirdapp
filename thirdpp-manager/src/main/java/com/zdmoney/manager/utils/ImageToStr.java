package com.zdmoney.manager.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;

public class ImageToStr {

	public static void main(String[] args){
		System.out.println(GetImageStr("D:\\idCard_2.jpg"));
	}
	
	
	
	/**
	 * Java使用Base64编码处理图片转String（方便传输）使用 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * 
	 * @param imgFilePath
	 * @return
	 */
	public static String GetImageStr(String imgFilePath) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Base64 encoder = new Base64();
		// 返回Base64编码过的字节数组字符串
		return encoder.encodeToString(data);
	}
	
	
	
	
	
	/**
	 * 对字节数组字符串进行Base64解码并生成图片
	 * 
	 * @param imgStr
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	/*
	 * public static boolean GenerateImage(String imgStr, String filePath,
	 * String fileName) { if (imgStr == null){ // 图像数据为空 return false; }
	 * BASE64Decoder decoder = new BASE64Decoder(); try { // Base64解码 byte[]
	 * bytes = decoder.decodeBuffer(imgStr); for (int i = 0; i < bytes.length;
	 * ++i) { if (bytes[i] < 0) {// 调整异常数据 bytes[i] += 256; } } String
	 * imgFilePath = FileUtils.getFilePath(filePath, fileName); // 生成图片
	 * OutputStream out = new FileOutputStream(imgFilePath); out.write(bytes);
	 * out.flush(); out.close(); return true; } catch (Exception e) { return
	 * false; } }
	 *//**
	 * 获取文件路径
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 */
	/*
	 * public static String getFilePath(String dir, String fileName) { String
	 * fileSeparator = System.getProperty(“file.separator”); if
	 * (!dir.endsWith(fileSeparator)) { dir += fileSeparator; } File file = new
	 * File(dir); if (!file.isDirectory()) { // 如果文件夹不存在就新建 file.mkdirs(); }
	 * return dir + fileName; }
	 */
}

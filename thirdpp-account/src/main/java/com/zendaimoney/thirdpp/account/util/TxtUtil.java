package com.zendaimoney.thirdpp.account.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.account.entity.BizsysAccountInfo;

public class TxtUtil {
	
	private static final Log logger = LogFactory.getLog(TxtUtil.class);
	
	public static void writerTxt(File file, List<String> contents, String fileEncoding) throws IOException{
		BufferedWriter fw = null;
		try {
			if (StringUtils.isBlank(fileEncoding)) {
				fileEncoding = Constants.ENCODE_CHARACTER_UTF8;
			}
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), fileEncoding)); // 指定编码格式，以免读取时中文字符异常
			for (String content : contents) {
				fw.append(content);
				fw.newLine();
			}
			fw.flush(); //全部写入缓存中的内容
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error("关闭BufferedWriter流失败", e);
				}
			}
		}
	}

	public void readTxt(String filePath) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8")); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,
			String str = null;
			while ((str = reader.readLine()) != null) {
				System.out.println(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		TxtUtil txtUtil = new TxtUtil();
		// 如果目录下面没有这个文件，会自动的创建文件，然后向文件中写数据
		// 但是路径不需要事先创建好
		File file = new File("D:/accountFile/4/20029000000474104/20151023/teat.txt");
		/*List<String> contents = new ArrayList<String>();
		contents.add("任务号,业务流水号,业务类型");
		contents.add("cdn, cdn");
		txtUtil.writerTxt(file, contents);*/
		
		System.out.println(file.getPath());
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getParent());
		BizsysAccountInfo statistic = new  BizsysAccountInfo();
		statistic.setBizFlow("123");
		statistic.setSuccessAmount(new BigDecimal("12.5"));
		
		Class<BizsysAccountInfo> cla = BizsysAccountInfo.class;
		try {
			Field field = cla.getDeclaredField("bizFlow");
			Field fiel2 = cla.getDeclaredField("successAmount");
			field.setAccessible(true);
			fiel2.setAccessible(true);
			try {
				System.out.println(field.get(statistic).toString());
				System.out.println(fiel2.get(statistic).toString());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
}

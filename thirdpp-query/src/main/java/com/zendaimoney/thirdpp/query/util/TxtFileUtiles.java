package com.zendaimoney.thirdpp.query.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

/**
 * 文件操作
 * @author mencius
 *
 */
public class TxtFileUtiles {

	// 日志工具类
	private static Log logger = LogFactory.getLog(TxtFileUtiles.class);
		
	/**
	 * 写文件备份数据 
	 * @param path 指定备份文件路径目录
	 * @param lists 待备份数据
	 * @throws Exception 
	 */
	public void writerTxt(String path, List<Document> lists) throws Exception {
		// 创建写入流 对象
		BufferedWriter fw = null;
		try {
			
			// 指定生成文件对象
			File file = new File(path);
			
			// 判断文件路径是否存在，如不存在 则创建目录
			if (!file.exists()) {
				file.mkdirs();
			}
			
			String fileName =  CalendarUtils.getFormatNow() + ".txt";
			File backFile = new File(path + fileName);
			logger.info("备份文件路径：" + backFile.getPath());
			
			// 初始化写入流对象  编码UTF-8
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(backFile, true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
 
			// 统计
			int count = 0;
			for (Document document : lists) {
				
				if (count == 0) {
					StringBuffer head = new StringBuffer();
					for (String str : document.keySet()) {
						head.append(str + "|");
					}
					fw.append(head);
					fw.newLine();
				}
				
				// 每行数据存放的对象
				StringBuffer record = new StringBuffer();
				for (String str : document.keySet()) {
					record.append(document.get(str) + "|");
				}
				fw.append(record);
				fw.newLine();
				count ++;
				
				// 如果记录达到1W条，则刷入文件中 ，防止内存溢出
				if (count == 10000) {
					fw.flush(); // 全部写入缓存中的内容
					count = 1;
				}
			}
			fw.flush(); // 全部写入缓存中的内容
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new Exception(e.getMessage());
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		Thread.currentThread().getContextClassLoader();
		TxtFileUtiles fileUtiles = new TxtFileUtiles();
		String path = "E://backup//";
		
		List<Document> list = new ArrayList<Document>();
		List<Document> lists = MongoDBUtils.query("UNKNOW_TRADE_COLLECTION", null, 10);
		
		for (int i = 0; i < 100000; i++) {
			list.addAll(lists);
		}
		
		System.out.println(list.size());
		
		try {
			fileUtiles.writerTxt(path, list);
		} catch (Exception e) {
		}
	}

}

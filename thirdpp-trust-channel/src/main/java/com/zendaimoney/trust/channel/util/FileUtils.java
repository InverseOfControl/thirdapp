package com.zendaimoney.trust.channel.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.zendaimoney.trust.channel.action.BatchActionAbstract;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;

/**
 * 文件操作工具类
 * @author mencius
 *
 */
@SuppressWarnings("rawtypes")
public class FileUtils {

	// 日志工具类
	private static final LogPrn logger = new LogPrn(BatchActionAbstract.class);
		
	/**
	 * 写文件数据 
	 * @param path 指定路径目录
	 * @param lists 数据
	 * @throws Exception 
	 */
	public void writerTxt(String path, String fileName, List lists, String encode) throws Exception {
		
		// 创建写入流 对象
		BufferedWriter fw = null;
		try {
			
			// 指定生成文件对象
			File file = new File(path);
			
			// 判断文件路径是否存在，如不存在 则创建目录
			if (!file.exists()) {
				file.mkdirs();
			}
			
			File batchFile = new File(path + fileName);
			if (batchFile.exists()) {
				batchFile.delete();
			}
			
			// 初始化写入流对象
			fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(batchFile, true), encode)); // 指定编码格式，以免读取时中文字符异常
 
			// 统计
			int count = 0;
			for (Object object : lists) {
				
				// 写入每行数据
				fw.append(object.toString());
				
				// 换行分割操作
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
	
	/**
	 * 读文件收集数据至list集合
	 * @param path
	 * @param encode
	 * @return
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	public List readTxt(String path, String encode) {
		
		List list = new ArrayList();
		
		try {
			
			File file = new File(path);
			
			if (file.isFile() && file.exists()) { //判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encode);//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				
				String lineTxt = null;
				
				while ((lineTxt = bufferedReader.readLine()) != null) {
					
					list.add(lineTxt);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 解析报文异常
			throw new PlatformException(PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR, PlatformErrorCode.CHANNEL_FILE_PARSE_ERROR.getDefaultMessage());
		}
		return list;
	}
	
	/**
	 * 删除文件操作
	 * @param path
	 * @return true/false
	 */
	public static boolean delete(String path) {
		
		File file = new File(path);
		
		if (file.isFile() && file.exists()) {
			logger.info("本地临时文件删除成功");
			return file.delete();
		}
		
		logger.info("本地临时文件路径：" + path + " 非法，删除文件失败");
		return false;
	} 
	
	public static void main(String[] args) {
		
//		Thread.currentThread().getContextClassLoader();
//		TxtFileUtils fileUtiles = new TxtFileUtils();
//		String path = "E://backup//";
//		
//		List list = new ArrayList();
		
	}

}

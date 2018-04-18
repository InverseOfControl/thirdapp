package com.zendaimoney.thirdpp.query.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.zendaimoney.thirdpp.query.conf.BackUpConfig;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.util.CalendarUtils;
import com.zendaimoney.thirdpp.query.util.MongoDBUtils;
import com.zendaimoney.thirdpp.query.util.TxtFileUtiles;

/**
 * 
 * mongo数据备份service
 * @author mencius
 *
 */
@Service
@Transactional
public class DataBackUpService {

	// 日志工具类
	private static Log logger = LogFactory.getLog(DataBackUpService.class);
	
	
	/**
	 * 数据备份入口
	 */
	public void backup() {
		
		try {
			String path = BackUpConfig.backUpConfig.getBackupPath() + CalendarUtils.getShortFormatNow() + "/";
			
			// 待备份数据 条件
			int sort = 1; // sort 1:升序； -1：降序
			Document queryDoc = new Document();
			queryDoc.put("source", ServerConfig.systemConfig.getAppName());
			queryDoc.put("backup", "1");
			
			// 筛选日期为N天前的所有可以备份的数据
			queryDoc.append("createTime", new Document("$lte", getDateByAddDay(-BackUpConfig.backUpConfig.getBeforeDayNum()))); // 时间戳 小于等于开始日期
			
			logger.info("备份数据筛选条件：" + queryDoc);
			
			// sort 1:升序； -1：降序
			Document sortDoc = new Document("createTime", sort);
			
			int backSize = BackUpConfig.backUpConfig.getBackSize();// 备份批量
			
			while (true) {
				
				logger.info("mongo 数据备份开始...");
				List<Document> documents = MongoDBUtils.query(MongoConfig.mongoConfig.getCollectionName(), queryDoc, backSize, sortDoc);
				if (documents != null && !documents.isEmpty() && documents.size() > 0) {
					
					TxtFileUtiles fileUtiles = new TxtFileUtiles();
					fileUtiles.writerTxt(path, documents);
					
					for (Document document : documents) {
						MongoDBUtils.delete(MongoConfig.mongoConfig.getCollectionName(), document);
					}
				} else {
					break;
				}
			}
			logger.info("mongo 数据备份结束...");
		} catch (Exception e) {
			logger.error("mongo 数据备份结束..." + e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * @param num
	 * @return
	 */
	public static String getDateByAddDay(int num) {
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.add(Calendar.DAY_OF_MONTH, num);
		String date = CalendarUtils.parsefomatCalendar(calendar, CalendarUtils.LONG_FORMAT_LINE);
		return date;
	}
	
	public static void main(String[] args) {
		
		System.out.println(getDateByAddDay(-30));
//		DataBackUpService backUpService = new DataBackUpService();
//		
//		backUpService.backup();
	}
}

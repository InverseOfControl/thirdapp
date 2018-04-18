package com.zendaimoney.thirdpp.query.thread;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;
import com.zendaimoney.thirdpp.query.service.impl.DataBackupHandlerImpl;
import com.zendaimoney.thirdpp.query.util.Constants;

public class QueryDataMoveDealThread implements Runnable {

	// 日志工具类
	public static Log logger = LogFactory.getLog(QueryDataMoveDealThread.class);
	
	/**
	 * 应用程序名称
	 */
	private String appName;
	
	private String collectionName;
	
	private long moveSleepTime;
	
	private CollectionHandler collectionHandler;
	
	/**
	 * 
	 * @param collectionName
	 */
	public QueryDataMoveDealThread(String collectionName) {
		
		this.appName = ServerConfig.systemConfig.getAppName();
		this.moveSleepTime = MongoConfig.mongoConfig.getMoveSleepTime();
		this.collectionName = MongoConfig.mongoConfig.getCollectionName();
		collectionHandler = new DataBackupHandlerImpl(collectionName); 
	}
		
	@Override
	public void run() {
		
		// 查询条件
		Document paraDoc = new Document();
		paraDoc.put("source", appName);
		paraDoc.put("backup", new Document("$ne", "1"));
		int sort = 1; // sort 1:升序； -1：降序
		int backupSize = MongoConfig.mongoConfig.getMoveSize();
		while (true) {
			
			try {
				List<Document> documents = collectionHandler.query(paraDoc, backupSize, sort);
				
				for (Document document : documents) {
					
					try {
						MongoQueryVO vo = new MongoQueryVO();
						vo.fromDbObject(document);
						vo.setDealFlag(Constants.TPP_QUERY_DEALFLAG_INIT);
						collectionHandler.moveAndDelete(collectionName, vo);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					// 暂停 moveSleepTime 
					Thread.sleep(moveSleepTime);
				} catch (Exception e) {
					logger.error("业务处理线程休眠异常", e);
				}
			}
		}
	}

}

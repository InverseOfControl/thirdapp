package com.zendaimoney.thirdpp.query.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.exception.PlatformException;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;
import com.zendaimoney.thirdpp.query.mongo.DBManager;
import com.zendaimoney.thirdpp.query.mongo.MongoDataSource;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;

/**
 * MongoDB 操作工具
 * @author mencius
 * 2015-06-10
 */
public class MongoDBUtils {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(MongoDBUtils.class);

	/**
	 * 获取MongoDB的collection操作对象
	 * @param collectionName
	 * @return
	 */
	private static MongoCollection<Document> getMongoCollection(String collectionName) {
		
		DBManager dbManager = MongoDataSource.dbMap.get(MongoDataSource.DEFAULT);
		if (dbManager == null) {
			dbManager = new DBManager();
		}
		MongoDatabase db =  dbManager.getDB(MongoConfig.mongoConfig.getDbName());
		return db.getCollection(collectionName);
	}
	
	public static Document getDocumentById(String _id) {
		
		
		return null;
	}
	
	/**
	 *	查询MongoDB数据库 
	 * @param dbName 数据库名
	 * @param collectionName 集合名
	 * @param map 查询参数
	 * @param limit 记录数
	 * @param sort 1:升序； -1：降序
	 * @return
	 */
	public static List<Document> query(String collectionName, Map<String, Object> map, int limit, int sort){
		
		logger.debug("[查询MongoDB开始]：map=" + map + ",limit:" + limit + ",sort:" + sort);
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[查询MongoDB开始]：collection=" + collectionName + " 不存在");
			return null;
		} else {
			FindIterable<Document> iterable = null;
			Document queryDoc = new Document();
			if (map != null){
				queryDoc.putAll(map);
			}
			if (sort == 1 || sort == -1) {
				
				iterable = mc.find(queryDoc).limit(limit).sort(new Document("createTime", sort));
			} else {
				iterable = mc.find(queryDoc).limit(limit);
			}
			
			MongoCursor<Document> cursor = iterable.iterator();
			while (cursor.hasNext()) {
				
				Document document = cursor.next();
				queryVOs.add(document);
			}
		}
		return queryVOs;
	}
	
	/**
	 *	查询MongoDB数据库 
	 * @param dbName 数据库名
	 * @param collectionName 集合名
	 * @param map 查询参数
	 * @param limit 记录数
	 * @param sort 1:升序； -1：降序
	 * @return
	 */
	public static List<Document> queryNotlimit(String collectionName, Map<String, Object> map, int sort){
		
		logger.debug("[查询MongoDB开始]：map=" + map + ",limit:" + ",sort:" + sort);
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[查询MongoDB开始]：collection=" + collectionName + " 不存在");
			return null;
		} else {
			FindIterable<Document> iterable = null;
			Document queryDoc = new Document();
			if (map != null){
				queryDoc.putAll(map);
			}
			if (sort == 1 || sort == -1) {
				
				iterable = mc.find(queryDoc).sort(new Document("timestamp", sort));
			} else {
				iterable = mc.find(queryDoc);
			}
			
			MongoCursor<Document> cursor = iterable.iterator();
			while (cursor.hasNext()) {
				
				Document document = cursor.next();
				queryVOs.add(document);
			}
		}
		return queryVOs;
	}
	
	/**
	 *	查询MongoDB数据库 
	 * @param dbName 数据库名
	 * @param collectionName 集合名
	 * @param map 查询参数
	 * @param limit 记录数
	 * @return
	 */
	public static List<Document> query(String collectionName, Map<String, Object> map, int limit){
		
		logger.debug("[查询MongoDB开始]：collectionName=" + collectionName + ",param:[" + map + ",limit:" + limit + "]");
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[查询MongoDB开始]：collection=" + collectionName + " 不存在");
			return null;
		} else {
			
			Document queryDoc = new Document();
			if (map != null && !map.isEmpty()){
				queryDoc.putAll(map);
			}
			FindIterable<Document> iterable = mc.find(queryDoc).limit(limit);
			
			MongoCursor<Document> cursor = iterable.iterator();
			while (cursor.hasNext()) {
				
				Document document = cursor.next();
				queryVOs.add(document);
			}
		}
		return queryVOs;
	}
	
	/**
	 *	查询MongoDB数据库 
	 * @param dbName 数据库名
	 * @param collectionName 集合名
	 * @param startTime 开始日期
	 * @param limit 记录数
	 * @param sort 1:升序； -1：降序
	 * @return
	 */
	public static List<Document> query(String collectionName,Document queryDoc, int limit, Document sortDoc){
		
		logger.debug("[查询MongoDB开始]：collectionName=" + collectionName + ",param:[queryDoc:" + queryDoc + ",limit:" + limit + ",sort:" + sortDoc + "]");
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[查询MongoDB开始]：collection=" + collectionName + " 不存在");
			return null;
		} else {
			FindIterable<Document> iterable = mc.find(queryDoc).sort(sortDoc).limit(limit);
			
			// 如果结果集不为空
			if (iterable != null) {
				
				MongoCursor<Document> cursor = iterable.iterator();
				while (cursor.hasNext()) {
					Document document = cursor.next();
					queryVOs.add(document);
				}
			}
		}
		return queryVOs;
	}
	
	/**
	 *	查询MongoDB数据库 
	 * @param dbName 数据库名
	 * @param collectionName 集合名
	 * @param startTime 开始日期
	 * @param limit 记录数
	 * @param sort 1:升序； -1：降序
	 * @return
	 */
	public static List<Document> query(String collectionName, String startTime, int limit, int sort){
		
		logger.debug("[查询MongoDB开始]：collectionName=" + collectionName + ",param:[ startTime:" + startTime + ",limit:" + limit + ",sort:" + sort + "]");
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[查询MongoDB开始]：collection=" + collectionName + " 不存在");
			return null;
		} else {
			FindIterable<Document> iterable = null;
			Document queryDoc = new Document();
			
			// sort 1:升序； -1：降序
			Document sortDoc = new Document("timestamp", sort);
			
			if (StringUtils.isEmpty(startTime)) {
				iterable = mc.find().sort(sortDoc).limit(limit);
			} else {
				queryDoc.append("updateFlag", "0"); // 处理标识
				queryDoc.append("timestamp", new Document("$gte", startTime)); // 时间戳 大于等于开始日期
				iterable = mc.find(queryDoc).sort(sortDoc).limit(limit);
			}
			
			// 如果结果集不为空
			if (iterable != null) {
				
				MongoCursor<Document> cursor = iterable.iterator();
				while (cursor.hasNext()) {
					Document document = cursor.next();
					queryVOs.add(document);
				}
			}
		}
		return queryVOs;
	}
	
	/**
	 * 插入一条记录
	 * @param dbName
	 * @param collectionName
	 * @param document
	 */
	public static void add(String collectionName, Document queryVO) {
		
		logger.debug("[MongoDB-add]：collection=" + collectionName + ",Entity:" + queryVO);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		if (mc == null) {
			logger.debug("[MongoDB]： collection=" + collectionName + " 不存在");
		} else {
			
			// 加入时间戳
			queryVO.put("timestamp", CalendarUtils.getFormatNow());
			// 修改处理标识：0 待处理；1 已处理
			queryVO.put("dealFlag", Constants.TPP_QUERY_DEALFLAG_INIT);

			// 执行单条记录插入操作
			mc.insertOne(queryVO);
		}
		logger.debug("[MongoDB添加]：入库完成");
	}
	
	
	/**
	 * 插入一条记录
	 * @param dbName
	 * @param collectionName
	 * @param document
	 */
	public static void addWaitingQuery(String collectionName, Document queryVO) {
		
		logger.info("[MongoDB-add]：collection=" + collectionName + ",Entity:" + queryVO);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		if (mc == null) {
			logger.debug("[MongoDB]： collection=" + collectionName + " 不存在");
		} else {
			
			// 修改处理标识：0 待处理；1 已处理
			queryVO.put("backup", "0");

			// 执行单条记录插入操作
			mc.insertOne(queryVO);
		}
		logger.info("[MongoDB添加]：入库完成");
	}
	
	/**
	 * 插入批量记录
	 * @param dbName
	 * @param collectionName
	 * @param documents
	 */
	public static void addBatch(String collectionName, List<Document> queryVOs) {
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		if (mc == null) {
			logger.debug("[MongoDB-addBatch]：collection=" + collectionName + " 不存在");
		} else {
			
			for (int i = 0; i < queryVOs.size(); i ++) {
				// 加入时间戳
				queryVOs.get(i).put("timestamp", CalendarUtils.getFormatNow());
				// 修改处理标识：0 待处理；1 已处理
				queryVOs.get(i).put("updateFlag", "0");
			}
			
			// 执行批量插入操作
			mc.insertMany(queryVOs);
			
		}
	}
	
	/**
	 * 移除一条记录
	 * @param dbName
	 * @param collectionName
	 * @param document
	 * return result
	 */
	public static long delete(String collectionName, Document document) {
		
		logger.debug("[MongoDB-delete] collection=" + collectionName + ",document:" + document);
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		if (mc == null) {
			logger.debug("[查询MongoDB开始]： collection=" + collectionName + " 不存在");
		} else {
			DeleteResult deleteResult = mc.deleteOne(document);
			logger.debug("[MongoDB移除]" + deleteResult);
			return deleteResult.getDeletedCount();
		}
		logger.debug("[MongoDB-delete]：失败");
		return 0;
	}
	
	/**
	 * 移除批量记录
	 * @param dbName
	 * @param collectionName
	 * @param documents
	 * return result
	 */
	public static long deleteMany(String collectionName, Document document) {
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		if (mc == null) {
			logger.debug("[MongoDB批量移除]：collection=" + collectionName + " 不存在");
		} else {
			
			DeleteResult deleteResult = mc.deleteMany(document);
			return deleteResult.getDeletedCount();
		}
		
		return 0;
	}
	
	/**
	 * 更新操作
	 * @param dbName
	 * @param collectionName
	 * @param document
	 * @return
	 */
	public static boolean update(String collectionName, Document document, String target) {
		
		logger.debug("[MongoDB-update]：document[" + document + "],target=" + target);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[MongoDB开始]：collection=" + collectionName + " 不存在");
		} else {
			
			Document updateDoc = new Document();
			updateDoc.put("_id", document.get("_id"));
			
			Document setDoc = new Document();
			setDoc.put("dealFlag", target);
			setDoc.put("timestamp", CalendarUtils.getMillFormatNow());
			
			UpdateResult result = mc.updateOne(updateDoc, new Document("$set", setDoc));
			logger.info("[MongoDB更新]：修改结果 result" + result);
			if (result.getModifiedCount() == 1) {
				return true;
			}
		}
		logger.debug("[MongoDB-update]：修改失败");
		return false;
	}
	
	/**
	 * 更新操作
	 * @param dbName
	 * @param collectionName
	 * @param document
	 * @return
	 */
	public static boolean updateCount(String collectionName, Document document, String target) {
		
		logger.debug("[MongoDB-update]：document[" + document + "],target=" + target);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[MongoDB开始]：collection=" + collectionName + " 不存在");
		} else {
			
			Document updateDoc = new Document();
			updateDoc.put("_id", document.get("_id"));
			MongoQueryVO vo = new MongoQueryVO();
			vo.fromDbObject(document);
			
			Document setDoc = new Document();
			setDoc.put("dealFlag", target);
			setDoc.put("timestamp", CalendarUtils.getMillFormatNow());
			setDoc.put("queryCount", vo.getQueryCount() + 1);
			
			
			UpdateResult result = mc.updateOne(updateDoc, new Document("$set", setDoc));
			logger.info("[MongoDB更新]：修改结果 result" + result);
			if (result.getModifiedCount() == 1) {
				return true;
			}
		}
		logger.debug("[MongoDB-update]：修改失败");
		return false;
	}
	
	/**
	 * 更新操作
	 * @param dbName
	 * @param collectionName
	 * @param document
	 * @return
	 */
	public static boolean update(String collectionName, Document document, Document setDoc, String target) {
		
		logger.debug("[MongoDB-update]：document[" + document + "], setDoc[" + setDoc + "] target=" + target);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		if (mc == null) {
			logger.debug("[MongoDB开始]：collection=" + collectionName + " 不存在");
		} else {
			
			Document updateDoc = new Document();
			updateDoc.put("_id", document.get("_id"));
			updateDoc.put("bizType", document.get("bizType"));
			updateDoc.put("updateFlag", document.get("updateFlag"));
			
			UpdateResult result = mc.updateOne(updateDoc, new Document("$set", setDoc));
			logger.debug("[MongoDB更新]：修改结果 result" + result);
			if (result.getModifiedCount() == 1) {
				return true;
			}
		}
		logger.debug("[MongoDB-update]：修改失败");
		return false;
		
	}
	
	/**
	 * 移动并清除记录操作
	 * @param dbName
	 * @param collectionName
	 * @param queryVO
	 */
	public static void moveAndDelete(String collectionName, Document document) {
		
		// 当前mongo集合属性
		CollectionConfig collectionConfig = MongoConfig.collectionMap.get(collectionName);
		// 下一mongo集合属性
		CollectionConfig nextCollection = MongoConfig.collectionMap.get(collectionConfig.getNextCollection());
		
		logger.debug("[MongoDB-moveAndDelete] 从" +collectionName + "中移动至下一个" + collectionConfig.getNextCollection() + ", document：" + document);
		
		// 下一集合不存在，则执行操作：记录从当前集合下移除
		if (nextCollection == null) {
			updateCount(collectionName, document, Constants.TPP_QUERY_DEALFLAG_INIT);
		} else {
			// 将该记录插入到 下一mongo集合下 next
			add(nextCollection.getCollectionName(), document);
			
			// 查询条件
			Document deleteDocument = new Document();
			if (document.containsKey("_id")) {
				deleteDocument.put("_id", document.get("_id"));
			}
			// 移动完成后将当前mongo集合下移除该条记录
			delete(collectionName, deleteDocument);
		}
	}
	
	/**
	 * 更新集合中的数据 ：时间戳及处理状态置为 target
	 */
	public static boolean updateOne(String collectionName, MongoQueryVO vo, String target) {
		
		logger.info("[MongoDB更新]：collectionName=" + collectionName + ", MongoQueryVO=" + vo + ",target=" + target);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document updateDoc = new Document();
		updateDoc.put("_id", vo.get_id());
		updateDoc.put("dealFlag", vo.getDealFlag());
		
		Document setDoc = new Document();
		setDoc.put("dealFlag", target);
		setDoc.put("timestamp", CalendarUtils.getMillFormatNow());
		
		UpdateResult result = mc.updateOne(updateDoc, new Document("$set", setDoc));
		logger.info("[MongoDB更新]：修改结果 result" + result);
		
		if (result.getModifiedCount() == 1) {
			return true;
		}
		
		logger.info("[MongoDB-update]：修改失败");
		return false;
	}
	
	/**
	 * 加载指定路径下配置文件
	 * 
	 * @param path
	 */
	public void loadFromFile(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				logger.error("File " + path + " is not exists");
				throw new PlatformException("File " + path + " is not exists");
			}
			Ini conf = new Ini(file);
			this.populateAttributes(conf);
			logger.debug("Load file " + path + " sucessful");
		} catch (IOException e) {
			logger.error("Parse configuration failed,path=" + path);
			throw new PlatformException("Parse configuration failed,path="
					+ path, e);
		} catch (Exception e) {
			logger.error("Parse configuration failed,path=" + path);
			throw new PlatformException("Parse configuration failed,path="
					+ path, e);
		}

	}

	private void populateAttributes(Ini conf) {
		this.populateMongoDBConf(conf);
	}
	
	
	/**
	 * 
	 * @param conf
	 */
	private void populateMongoDBConf(Ini conf) {
		
		Section section = conf.get("mongodb");
		if (section != null) {
			
			MongoConfig.getMongoConfig().setDbName(section.get("dbName"));
			MongoConfig.getMongoConfig().setConnectionDescriptor(section.get("connectionDescriptor"));
			MongoConfig.getMongoConfig().setPoolSize(Integer.valueOf(section.get("poolSize")));
			MongoConfig.getMongoConfig().setConnectTimeout(Integer.valueOf(section.get("connectTimeout")));
			MongoConfig.getMongoConfig().setAutoConnectRetry(Boolean.valueOf(section.get("autoConnectRetry")));
			MongoConfig.getMongoConfig().setThreadsMultiplier(Integer.valueOf(section.get("threadsMultiplier")));
			
		}
		
	}

	
	public static void main(String[] args) {
//		String dbName = "TPP";
//		
//		String collectionName = "UNKNOW_TRADE_FINAL_COLLECTION";
//		
//		MongoQueryVO queryVO = new MongoQueryVO();
//		queryVO.setBizTypeNo(BizType.BROKER_PAY.getCode());
//		queryVO.setBizTypeNo("002");
//		queryVO.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
//		queryVO.setPaySysNo("0");
//		queryVO.setTradeFlow("0018082244506164");
//		queryVO.setAmount("1302.00");
//		queryVO.setTimestamp(CalendarUtils.getFormatNow());
//		queryVO.setUpdateFlag("0");
//		queryVO.setSource("query-app1");
//		queryVO.setCreateTime(CalendarUtils.getFormatNow());
//		
//		MongoQueryVO queryVO2 = new MongoQueryVO();
//		queryVO2.setBizTypeNo(BizType.BROKER_PAY.getCode());
//		queryVO2.setBizTypeNo("002");
//		queryVO2.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
//		queryVO2.setPaySysNo("0");
//		queryVO2.setTradeFlow("0015837710420612");
//		queryVO2.setAmount("1302.00");
//		queryVO2.setTimestamp(CalendarUtils.getFormatNow());
//		queryVO2.setUpdateFlag("0");
//		queryVO2.setSource("query-app1");
//		queryVO2.setCreateTime(CalendarUtils.getFormatNow());
//		
//		List<Document> documents = new ArrayList<Document>();
//		documents.add(queryVO.toDbObject());
//		documents.add(queryVO2.toDbObject());
//		
//		MongoDBUtils dbUtils = new MongoDBUtils();
		
		// 插入一条记录
//		dbUtils.add(dbName, collectionName, queryVO.toDbObject());
		
		// 插入批量记录
//		dbUtils.addBatch(dbName, collectionName, documents);
		
//		Document deleteDocument = new Document();
//		deleteDocument.put("id", queryVO2.getId());
//		deleteDocument.put("source", "query-broker-collect-app1");
		// 删除一条记录
//		dbUtils.delete(dbName, collectionName, deleteDocument);
		
//		dbUtils.moveAndDelete(dbName, collectionName, deleteDocument);
		
//		long num = dbUtils.deleteMany(dbName, collectionName, deleteDocument);
//		System.out.println("删除 " + num + "条记录");
		
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("createTime", queryVO.getCreateTime());
//		// $gt 代表大于， $lt 代表小于
//		map.put("createTime", new Document("$gt",  "20150609"));
//		map.put("bizTypeNo", queryVO.getBizTypeNo());
		
//		dbUtils.query(collectionName, null, 100);
		
//		Document paraDoc = new Document();
//		paraDoc.put("source", "query-app1");
//		paraDoc.put("updateFlag", "0");// 待处理
//		System.out.println(dbUtils.query(dbName, collectionName, paraDoc, null, 100, 1));
		
		
//		queryVO.setUpdateFlag("1");
//		queryVO.set_id("55891ca4bab2591640e3d9f5");
//		System.out.println("更新结果：" + dbUtils.update(dbName, collectionName, queryVO.toDbObject(), "0"));
//		
//		dbUtils.moveAndDelete(dbName, collectionName, queryVO.toDbObject());
		
//		Map<String, Object> queryDoc = new HashMap<String, Object>();
//		queryDoc.put("bizType", "000");
//		System.out.println(dbUtils.query(dbName, "TPP_CONFIG", queryDoc, 1));
		
	}
}

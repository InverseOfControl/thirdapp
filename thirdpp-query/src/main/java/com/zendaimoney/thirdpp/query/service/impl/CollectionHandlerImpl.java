package com.zendaimoney.thirdpp.query.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.zendaimoney.thirdpp.query.abs.CollectionHandlerAbstract;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;
import com.zendaimoney.thirdpp.query.util.CalendarUtils;
import com.zendaimoney.thirdpp.query.util.Constants;

public class CollectionHandlerImpl extends CollectionHandlerAbstract implements CollectionHandler {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(CollectionHandlerImpl.class);
	
	public CollectionHandlerImpl(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public String getCollectionName() {
		return this.collectionName;
	}
	
	/**
	 * 入mongo库
	 */
	@Override
	public void add(String collectionName, MongoQueryVO vo) {
		
		logger.info("[MongoDB添加]：collectionName=" + collectionName + ",MongoQueryVO=" + vo);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
			
		// 加入时间戳
		vo.setTimestamp(CalendarUtils.getMillFormatNow());
		// 处理状态原始值为： 0
		vo.setDealFlag(Constants.TPP_QUERY_DEALFLAG_INIT);
		// 初始化查询次数：0
		vo.setQueryCount(0);
		
		// 执行单条记录插入操作
		mc.insertOne(vo.toDbObject());
		
		logger.info("[MongoDB添加]：入库完成");
	}

	/**
	 * 从集合中移除数据
	 */
	@Override
	public long delete(String collectionName, MongoQueryVO vo) {
		
		logger.info("[MongoDB移除]:collectionName=" + collectionName + ",MongoQueryVO=" + vo);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document delete = new Document();
		delete.put("_id", vo.get_id());
		delete.put("bizTypeNo", vo.getBizTypeNo());
		
		DeleteResult deleteResult = mc.deleteOne(delete);
		
		logger.info("[MongoDB移除]" + deleteResult);
		return deleteResult.getDeletedCount();
	}

	/**
	 * 更新集合中的数据 ：时间戳及处理状态置为 target
	 */
	@Override
	public boolean update(String collectionName, MongoQueryVO vo, String target) {
		
		logger.info("[MongoDB更新]：collectionName=" + collectionName + ", MongoQueryVO=" + vo + ",targe=" + target);
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
	 * 更新集合中的数据 ：查询次数、时间戳及处理状态置为 target
	 */
	public boolean updateQueryCount(String collectionName, MongoQueryVO vo, String target) {
		
		logger.info("[MongoDB更新]：collectionName=" + collectionName + ", MongoQueryVO=" + vo + ",targe=" + target);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document updateDoc = new Document();
		updateDoc.put("_id", vo.get_id());
		
		Document setDoc = new Document();
		setDoc.put("dealFlag", target);
		setDoc.put("timestamp", CalendarUtils.getMillFormatNow());
		setDoc.put("queryCount", vo.getQueryCount() + 1);
		
		UpdateResult result = mc.updateOne(updateDoc, new Document("$set", setDoc));
		logger.info("[MongoDB更新]：修改结果 result" + result);
		
		if (result.getModifiedCount() == 1) {
			return true;
		}
		
		logger.info("[MongoDB-update]：修改失败");
		return false;
	}

	/**
	 * 移动并清除集合中的数据
	 */
	@Override
	public void moveAndDelete(String collectionName, MongoQueryVO vo) {
		
		logger.info("[MongoDB-moveAndDelete]:collectionName=" + collectionName + ",MongoQueryVO=" + vo);
		// 当前mongo集合属性
		CollectionConfig collectionConfig = MongoConfig.collectionMap.get(collectionName);
		// 下一mongo集合属性
		CollectionConfig nextCollection = MongoConfig.collectionMap.get(collectionConfig.getNextCollection());
		
		// 下一集合不存在，则执行操作：记录从当前集合下移除
		if (nextCollection == null) {
			updateQueryCount(collectionName, vo, Constants.TPP_QUERY_DEALFLAG_INIT);
		} else {
				
			// 将该记录插入到 下一mongo集合下 next
			add(nextCollection.getCollectionName(), vo);
			
			// 移动完成后将当前mongo集合下移除该条记录
			delete(collectionName, vo);
		}
	}

	/**
	 * 查询集合中的数据
	 */
	@Override
	public List<Document> query(Document queryDoc,
			String startTime, int limit, int sort) {
		
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		FindIterable<Document> iterable = null;
		
		// sort 1:升序； -1：降序
		Document sortDoc = new Document("timestamp", sort);
		
		if (StringUtils.isEmpty(startTime)) {
			iterable = mc.find(queryDoc).sort(sortDoc).limit(limit);
		} else {
			queryDoc.append("timestamp", new Document("$lte", startTime)); // 时间戳 小于等于开始日期
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
		
		return queryVOs;
	}

	@Override
	public List<Document> query(Map<String, Object> map,
			int limit) {
		
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document queryDoc = new Document();
		if (map != null){
			queryDoc.putAll(map);
		}
		FindIterable<Document> iterable = mc.find(queryDoc).limit(limit);
		
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			
			Document document = cursor.next();
			queryVOs.add(document);
		}
		
		return queryVOs;
	}

	@Override
	public List<Document> query(Map<String, Object> map,
			int limit, int sort) {
		
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		FindIterable<Document> iterable = null;
		Document queryDoc = new Document();
		if (map != null){
			queryDoc.putAll(map);
		}
		if (sort == 1 || sort == -1) {
			
			iterable = mc.find(queryDoc).limit(limit).sort(new Document("timestamp", sort));
		} else {
			iterable = mc.find(queryDoc).limit(limit);
		}
		
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			
			Document document = cursor.next();
			queryVOs.add(document);
		}
		
		return queryVOs;
	}
	
	
	public static void main(String[] args) {
		
		String startTime = CalendarUtils.getTimeByMillAgo(CalendarUtils.getFormatNow(CalendarUtils.LONG_FORMAT_LINE), Long.valueOf(5*60*60*1000), CalendarUtils.LONG_FORMAT_LINE);
		
		System.out.println(startTime);
	}

}

package com.zendaimoney.thirdpp.query.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * mongo数据备份service
 * @author mencius
 *
 */
@Transactional
public class DataBackupHandlerImpl extends CollectionHandlerAbstract implements
		CollectionHandler {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(DataBackupHandlerImpl.class);
	
	public DataBackupHandlerImpl(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public List<Document> query(Document queryDoc, String startTime, int limit,
			int sort) {
		return null;
	}

	@Override
	public List<Document> query(Map<String, Object> map, int limit) {
		
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
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
		
		return queryVOs;
	}

	/**
	 * 查询数据：
	 */
	@Override
	public List<Document> query(Map<String, Object> map, int limit, int sort) {
		
		List<Document> queryVOs = new ArrayList<Document>();
		
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document queryDoc = new Document();
		if (map != null && !map.isEmpty()){
			queryDoc.putAll(map);
		}
		
		// 查询暂未备份的数据
		FindIterable<Document> iterable = mc.find(queryDoc).limit(limit).sort(new Document("createTime", sort));
		
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			
			Document document = cursor.next();
			queryVOs.add(document);
		}
		
		return queryVOs;
	}

	/**
	 * 添加数据至集合内
	 */
	@Override
	public void add(String collectionName, MongoQueryVO vo) {
		
		logger.info("[MongoDB添加]：collectionName=" + collectionName + ",MongoQueryVO=" + vo);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
			
		// 加入时间戳
		vo.setTimestamp(CalendarUtils.getMillFormatNow());

		// 执行单条记录插入操作
		mc.insertOne(vo.toDbObject());
		
		logger.info("[MongoDB添加]：入库完成");
	}

	/**
	 * 移除集合内的数据
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
	 * 更新集合数据 并修改备份状态为 target
	 */
	@Override
	public boolean update(String collectionName, MongoQueryVO vo, String target) {
		
		logger.info("[MongoDB更新]：collectionName=" + collectionName + ", MongoQueryVO=" + vo + ",targe=" + target);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document updateDoc = new Document();
		updateDoc.put("_id", vo.get_id());
		
		Document setDoc = new Document();
		setDoc.put("backup", Constants.TPP_QUERY_DEALFLAG_DEALED);
		
		UpdateResult result = mc.updateOne(updateDoc, new Document("$set", setDoc));
		logger.info("[MongoDB更新]：修改结果 result" + result);
		
		if (result.getModifiedCount() == 1) {
			return true;
		}
		
		logger.info("[MongoDB-update]：修改失败");
		return false;
	}

	@Override
	public void moveAndDelete(String collectionName, MongoQueryVO vo) {
		logger.info("[MongoDB-moveAndUpdate]:collectionName=" + collectionName + ",MongoQueryVO=" + vo);
		// 获取第一个待处理的集合信息
		CollectionConfig targetCollection = MongoConfig.getCollectionConfig("1", vo.getOpMode());
		
		if (targetCollection != null) {
			
			// 将该记录插入到 下一mongo集合下
			add(targetCollection.getCollectionName(), vo);
			
			// 修改备份数据为backup字段为1，表示可以备份
			update(collectionName, vo, Constants.TPP_QUERY_DEALFLAG_INIT);
		}
	}
	

	@Override
	public String getCollectionName() {
		return collectionName;
	}

}

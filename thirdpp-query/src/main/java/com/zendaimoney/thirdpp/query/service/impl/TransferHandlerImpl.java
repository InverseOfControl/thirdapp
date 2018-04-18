package com.zendaimoney.thirdpp.query.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.zendaimoney.thirdpp.query.abs.CollectionHandlerAbstract;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.dao.TppTradeWaitingQueryDao;
import com.zendaimoney.thirdpp.query.entity.TradeWaitingQuery;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;

/**
 * mongo数据转移service
 * @author mencius
 *
 */
@Service
@Transactional
public class TransferHandlerImpl extends CollectionHandlerAbstract implements
		CollectionHandler {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(TransferHandlerImpl.class);
	
	@Autowired
	public TppTradeWaitingQueryDao tradeWaitingQueryDao;
	
	public TransferHandlerImpl(String collectionName) {
		this.collectionName = collectionName;
		
		tradeWaitingQueryDao = (TppTradeWaitingQueryDao) ServerConfig.getBean(TppTradeWaitingQueryDao.class.getName());
	}
	
	/**
	 * 数据转移
	 */
	public void transfer() {
		
		// 当前mongo集合属性
		CollectionConfig collectionConfig = MongoConfig.collectionMap.get(collectionName);
		
		// 查询条件对象
		Document queryDoc = new Document();
		queryDoc.put("queryCount", new Document("$gte", collectionConfig.getMaxQueryNum()));
		int sort = 1; // sort 1:升序； -1：降序
		
		try {
			List<Document> documents = query(queryDoc, ServerConfig.transferConfig.getMaxResideNum(), sort);
			
			List<TradeWaitingQuery> waitingQueries = new ArrayList<TradeWaitingQuery>();
			
			if (documents != null && !documents.isEmpty()) {
				
				// 遍历集合
				for (Document document :documents) {
					MongoQueryVO queryVO = new MongoQueryVO();
					queryVO.fromDbObject(document);
					TradeWaitingQuery waitingQuery = new TradeWaitingQuery();
					// 对象赋值
					BeanUtils.copyProperties(waitingQuery, queryVO);
					
					// 赋值进程名
					waitingQuery.setQueryModuleName(queryVO.getSource());
					
					waitingQueries.add(waitingQuery);
				}
				
				// 批量入waiting表
				tradeWaitingQueryDao.batchInsert(waitingQueries);
				
				// 将查询抓取的数据 全部移除
				deleteAll(documents);
			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public List<Document> query(Document queryDoc, String startTime, int limit,
			int sort) {
		return null;
	}

	/**
	 * 移除全部mongo数据
	 * @param documents
	 */
	public void deleteAll(List<Document> documents) {
		
		for (Document document :documents) {
			MongoQueryVO queryVO = new MongoQueryVO();
			queryVO.fromDbObject(document);
			
			// 删除该记录
			delete(collectionName, queryVO);
		}
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
	 * 移除集合内的数据
	 */
	@Override
	public long delete(String collectionName, MongoQueryVO vo) {
		
		logger.info("[MongoDB移除]:collectionName=" + collectionName + ",MongoQueryVO=" + vo);
		MongoCollection<Document> mc = getMongoCollection(collectionName);
		
		Document delete = new Document();
		delete.put("_id", vo.get_id());
		
		DeleteResult deleteResult = mc.deleteOne(delete);
	
		logger.info("[MongoDB移除]" + deleteResult);
		return deleteResult.getDeletedCount();
	}

	

	@Override
	public String getCollectionName() {
		return collectionName;
	}

	@Override
	public void add(String collectionName, MongoQueryVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(String collectionName, MongoQueryVO vo, String target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveAndDelete(String collectionName, MongoQueryVO vo) {
		// TODO Auto-generated method stub
		
	}

}

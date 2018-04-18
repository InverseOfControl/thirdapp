package com.zendaimoney.thirdpp.query.service;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;

public interface CollectionHandler {
	
	/**
	 * 查询
	 * @param queryDoc 查询条件
	 * @param startTime 开始时间
	 * @param limit 处理条数
	 * @param sort 排序方式
	 * @return 结果集
	 */
	List<Document> query(Document queryDoc, String startTime, int limit, int sort);
	
	/**
	 * 查询
	 * @param map 查询条件
	 * @param limit 处理条数
	 * @return
	 */
	List<Document> query(Map<String, Object> map, int limit);
	
	/**
	 * 查询
	 * @param map 查询条件
	 * @param limit 处理条数
	 * @param sort 排序方式
	 * @return
	 */
	List<Document> query(Map<String, Object> map, int limit, int sort);
	
	/**
	 * 新增数据
	 * @param collectionName
	 * @param vo
	 */
	void add(String collectionName, MongoQueryVO vo);
	
	/**
	 * 删除记录
	 * @param collectionName
	 * @param vo
	 * @return
	 */
	long delete(String collectionName, MongoQueryVO vo);
	
	/**
	 * 更新记录
	 * @param collectionName
	 * @param vo
	 * @param target
	 * @return
	 */
	boolean update(String collectionName, MongoQueryVO vo, String target);
	
	/**
	 * 移动并删除记录
	 * @param collectionName
	 * @param vo
	 */
	void moveAndDelete(String collectionName, MongoQueryVO vo);
	
}

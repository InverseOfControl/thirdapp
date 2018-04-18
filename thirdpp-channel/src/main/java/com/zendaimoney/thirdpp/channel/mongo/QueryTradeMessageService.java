package com.zendaimoney.thirdpp.channel.mongo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.zendaimoney.thirdpp.channel.conf.MongoConfig;
import com.zendaimoney.thirdpp.channel.pub.vo.QueryReqVo;
import com.zendaimoney.thirdpp.channel.util.MongoDBUtils;

/**
 * 查询接口第三方报文service.
 * 
 * @author 00231257
 *
 */
@Service
public class QueryTradeMessageService {

	// 日志工具类
	public static Log logger = LogFactory.getLog(QueryTradeMessageService.class);

	/**
	 * 查询第三方系统报文存储集合名称
	 */
	public static final String COLLECTION_NAME = "QUERY_MESSAGE_COLLECTION";

	@Autowired
	private DBManager dBManager;

	public String getCollectionName() {
		return COLLECTION_NAME;
	}

	/**
	 * 插入一条记录
	 * 
	 * @param dbName
	 * @param document
	 */
	public void add(QueryReqVo queryReqVo, String message) {
		
		Document document = null;
		QueryThirdMessageVO queryThirdMessageVO = new QueryThirdMessageVO(queryReqVo.getTradeFlow(), message);
		document = queryThirdMessageVO.toDbObject();
		MongoCollection<Document> mc = MongoDBUtils.getMongoCollection(
				MongoConfig.mongoConfig.getDbName(), COLLECTION_NAME);
		// 执行单条记录插入操作
		mc.insertOne(document);
	}

}

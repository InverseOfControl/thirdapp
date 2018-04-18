package com.zendaimoney.thirdpp.trade.mongo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.zendaimoney.thirdpp.trade.conf.MongoConfig;
import com.zendaimoney.thirdpp.trade.util.MongoDBUtils;
import com.zendaimoney.thirdpp.trade.util.ThirdPPCacheContainer;

/**
 * 不确定交易service.
 * 
 * @author 00231257
 *
 */
@Service
public class UnKnowTradeService {

	// 日志工具类
	public static Log logger = LogFactory.getLog(UnKnowTradeService.class);

	public static final String COLLECTION_NAME = "UNKNOW_TRADE_COLLECTION";

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
	public void add(UnknowTradeVO unknowTradeVO) {
		Random r = new Random();
		Document document = null;
		List<String> list = null;
		String[] array = null;
		String appName = null;
		// 选择source
		if (unknowTradeVO != null) {
			appName = ThirdPPCacheContainer.sysQueryInfoMap.get(unknowTradeVO
					.getPaySysNo() + unknowTradeVO.getBizTypeNo());
			if (appName != null) {
				array = appName.split(",");
				list = Arrays.asList(array);
				if (list != null && list.size() > 0) {
					unknowTradeVO.setSource(list.get(r.nextInt(list.size())));
				}
			}
		}
		document = unknowTradeVO.toDbObject();
		MongoCollection<Document> mc = MongoDBUtils.getMongoCollection(
				MongoConfig.mongoConfig.getDbName(), COLLECTION_NAME);
		// 执行单条记录插入操作
		mc.insertOne(document);
	}

}

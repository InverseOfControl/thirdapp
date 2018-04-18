package com.zendaimoney.thirdpp.query.abs;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.mongo.DBManager;
import com.zendaimoney.thirdpp.query.mongo.MongoDataSource;

public abstract class CollectionHandlerAbstract {

	public String collectionName;
	
	public abstract String getCollectionName();
	
	/**
	 * 获取MongoDB的collection操作对象
	 * @param dbName
	 * @param collectionName
	 * @return
	 */
	public MongoCollection<Document> getMongoCollection(String collectionName) {
		
		DBManager dbManager = MongoDataSource.dbMap.get(MongoDataSource.DEFAULT);
		if (dbManager == null) {
			dbManager = new DBManager();
		}
		MongoDatabase db =  dbManager.getDB(MongoConfig.mongoConfig.getDbName());
		return db.getCollection(collectionName);
	}
	
}

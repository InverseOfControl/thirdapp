package com.zendaimoney.thirdpp.query.mongo;

import java.util.HashMap;
import java.util.Map;


public class MongoDataSource {
	
	public static Map<String, DBManager> dbMap = new HashMap<String, DBManager>();
	
	public static final String DEFAULT = "default";
	
	public static MongoDataSource getInstance() {
		return InnerHolder.INSTANCE;
	}
	
	private static class InnerHolder {
		static final MongoDataSource INSTANCE = new MongoDataSource();
	}
	
	/**
	 * 获取默认的mongo数据源
	 * @return DBManager
	 */
	public DBManager getDbManager() {
		
		if(dbMap.containsKey(DEFAULT)) {
			return dbMap.get(DEFAULT);
		}
		return null;
	}
	
	
}

package com.zendaimoney.thirdpp.trade.mongo;

import java.net.UnknownHostException;
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
	
	public synchronized DBManager getDBManager(String host, String port, String poolSize) throws NumberFormatException, UnknownHostException {
		String key = host+":"+port;
		if(dbMap.containsKey(key)) {
			return dbMap.get(key);
		} else {
			DBManager instance = new DBManager();
			instance.init(host, Integer.parseInt(port), Integer.parseInt(poolSize));
			dbMap.put(key, instance);
			return instance;
		}
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

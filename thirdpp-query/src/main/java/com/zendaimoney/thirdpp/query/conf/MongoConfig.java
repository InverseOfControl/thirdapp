package com.zendaimoney.thirdpp.query.conf;

import java.util.HashMap;
import java.util.Map;

import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;


/**
 * @author user
 *
 */
public class MongoConfig {

	/**
	 * 数据集合名称
	 */
	private String dbName;
	
	/**
	 * mongo 服务地址列表
	 */
	private String connectionDescriptor;
	
	/**
	 * 线程池大小
	 */
	private int poolSize;
	
	/**
	 * 线程连接倍数
	 */
	private int threadsMultiplier;
	
	/**
	 * 连接超时（毫秒）
	 */
	private int connectTimeout;
	
	/**
	 * 是否重连
	 */
	private boolean autoConnectRetry;
	
	/**
	 * 周围系统入库 集合名称
	 */
	private String collectionName;
	
	/**
	 * 数据移动休眠时间
	 */
	private Long moveSleepTime;
	
	/**
	 * 数据移动记录数
	 */
	private int moveSize;
	
	/**
	 * 批量备份往前推N天
	 */
	private int beforeDayNum;
	
	public static MongoConfig mongoConfig;
	
	public static Map<String, CollectionConfig> collectionMap = new HashMap<String, CollectionConfig>();
	
	public static void setMongoConfig(MongoConfig mongoConfig) {
		MongoConfig.mongoConfig = mongoConfig;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getConnectionDescriptor() {
		return connectionDescriptor;
	}

	public void setConnectionDescriptor(String connectionDescriptor) {
		this.connectionDescriptor = connectionDescriptor;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public int getThreadsMultiplier() {
		return threadsMultiplier;
	}

	public void setThreadsMultiplier(int threadsMultiplier) {
		this.threadsMultiplier = threadsMultiplier;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public static MongoConfig getMongoConfig() {
		if (mongoConfig == null) {
			mongoConfig = new MongoConfig();
		}
		return mongoConfig;
	}

	public boolean getAutoConnectRetry() {
		return autoConnectRetry;
	}

	public void setAutoConnectRetry(boolean autoConnectRetry) {
		this.autoConnectRetry = autoConnectRetry;
	}
	
	public static void setCollectionMap(
			Map<String, CollectionConfig> collectionMap) {
		MongoConfig.collectionMap = collectionMap;
	}
	
	public static Map<String, CollectionConfig> getCollectionMap() {
		return collectionMap;
	}
	
	public String getCollectionName() {
		return collectionName;
	}
	
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public Long getMoveSleepTime() {
		return moveSleepTime;
	}
	
	public void setMoveSleepTime(Long moveSleepTime) {
		this.moveSleepTime = moveSleepTime;
	}
	
	public int getMoveSize() {
		return moveSize;
	}
	
	public void setMoveSize(int moveSize) {
		this.moveSize = moveSize;
	}
	
	public int getBeforeDayNum() {
		return beforeDayNum;
	}
	
	public void setBeforeDayNum(int beforeDayNum) {
		this.beforeDayNum = beforeDayNum;
	}
	
	/**
	 * 根据集合名称索引查询 对应的配置信息
	 * @param code
	 * @return CollectionConfig
	 */
	public static CollectionConfig getCollectionConfig(String code) {
		
		for (String collectionName : collectionMap.keySet()) {
			
			CollectionConfig collectionConfig = collectionMap.get(collectionName);
			
			if (code.equals(collectionConfig.getCode())) {
				return collectionConfig;
			}
		}
		return null;
	}
	
	/**
	 * 根据集合名称索引查询 对应的配置信息
	 * @param code
	 * @return CollectionConfig
	 */
	public static CollectionConfig getCollectionConfig(String code, String opMode) {
		
		for (String collectionName : collectionMap.keySet()) {
			
			CollectionConfig collectionConfig = collectionMap.get(collectionName);
			
			if (code.equals(collectionConfig.getCode()) && collectionConfig.getOpMode().equals(opMode)) {
				return collectionConfig;
			}
		}
		return null;
	}
	
}

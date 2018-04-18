package com.zendaimoney.thirdpp.channel.conf;



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
	private long connectTimeout;
	
	/**
	 * 是否重连
	 */
	private boolean autoConnectRetry;
	
	public static MongoConfig mongoConfig;
	
	
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

	public long getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(long connectTimeout) {
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
	
	
}

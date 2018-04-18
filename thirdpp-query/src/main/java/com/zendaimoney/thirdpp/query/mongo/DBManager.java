package com.zendaimoney.thirdpp.query.mongo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.util.MongoDBUtils;

@Component
public class DBManager {

	// 日志工具类
	protected static Log logger = LogFactory.getLog(DBManager.class);
	
	public static MongoClient client;
	
	static {
		
		// 如果已经初始化，则无需重新读配置信息
		if (!MongoDataSource.dbMap.containsKey(MongoDataSource.DEFAULT)) {
			
			String path = "";
			if (StringUtils.isEmpty(ServerConfig.systemConfig.getGlobalConfigPath())) {
				
				path = Thread.currentThread().getContextClassLoader().getResource("conf/mongo.properties").getPath();
			} else {
				
				File mongoFile = new File(ServerConfig.systemConfig.getGlobalConfigPath() + File.separator + "mongo.properties");
				path = mongoFile.getAbsolutePath();
			}
			logger.info("mongoPath:" + path);
			
			MongoDBUtils dbUtils = new MongoDBUtils();
			dbUtils.loadFromFile(path);
			
			// 初始化
			init();
		}
	}
	
	/**
	 * 启动加载配置文件时执行 
	 */
	public static void init() {
		DBManager manager = new DBManager();
		manager.config(MongoConfig.mongoConfig);
	}
	
	public void config(MongoConfig config) {
		
		if (config != null) {
			
			if (!StringUtils.isEmpty(config.getConnectionDescriptor())) {
				String[] hosts = config.getConnectionDescriptor().split(",");
				List<ServerAddress> seeds = new ArrayList<ServerAddress>();
				for (String host : hosts) {
					String[] hostPortPair = host.split(":");
					int port = 27017;
					if (hostPortPair.length > 1) {
						try {
							port = Integer.parseInt(hostPortPair[1]);
						} catch (Exception e) {
							
						}
						seeds.add(new ServerAddress(hostPortPair[0], port));
					}
				}
				
				// client配置信息 (version3.0)
				MongoClientOptions clientOptions = new MongoClientOptions.Builder().connectTimeout(MongoConfig.mongoConfig.getConnectTimeout())
						.connectionsPerHost(MongoConfig.mongoConfig.getPoolSize()).socketKeepAlive(MongoConfig.mongoConfig.getAutoConnectRetry())
						.threadsAllowedToBlockForConnectionMultiplier(MongoConfig.mongoConfig.getThreadsMultiplier()).build();
				
				// 采取默认Mongo配置
				client = new MongoClient(seeds, clientOptions);
				
				if (client != null) {
					MongoDataSource.dbMap.put(MongoDataSource.DEFAULT, this);
				}
			}
		}
	}
	
	public MongoDatabase getDB(String dbname) {
		
		MongoDatabase database = client.getDatabase(dbname);
		return database; 
	}
	
}

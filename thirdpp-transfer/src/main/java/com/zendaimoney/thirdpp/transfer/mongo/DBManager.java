package com.zendaimoney.thirdpp.transfer.mongo;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.zendaimoney.thirdpp.transfer.conf.MongoConfig;
import com.zendaimoney.thirdpp.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.transfer.util.MongoDBUtils;

@Component
public class DBManager {

	// 日志工具类
	protected static Log logger = LogFactory.getLog(DBManager.class);
	// db的ip
	public static String ip = "172.16.230.67";
	// db的端口
	public static int port = 30000;
	// db的名称
	public String dbName = "TPP";
	// 连接池大小
	public static int connectionsPerHost = 90;
	// 连接池的乘数
	public static int poolMultiplier = 20;
	// 连接超时时长
	public static int timeout = 30000;
	
	public static MongoClient client;
	
	boolean connected = false;
	
	static {
		
		// 如果已经初始化，则无需重新读配置信息
		if (!MongoDataSource.dbMap.containsKey(MongoDataSource.DEFAULT)) {
			
			String path = "" ;
			if (StringUtils.isEmpty(ServerConfig.systemConfig.getGlobalConfigPath())) {
				path = Thread.currentThread().getContextClassLoader().getResource("conf/mongo.properties").getPath();
			} else {
				File mongoFile = new File(ServerConfig.systemConfig.getGlobalConfigPath() + File.separator + "mongo.properties");
				path = mongoFile.getAbsolutePath();
			}
			
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
	
	public void init(final String ip, int port, int poolSize) {
		this.init(ip, poolSize);
	}

	public void init(String hostStr, int poolSize) {
		if (client == null) {
			
			if (hostStr.contains(",")) {
				String[] hosts = hostStr.split(",");
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
//				MongoClientOptions clientOptions = new MongoClientOptions.Builder().build(); 
				MongoClientOptions clientOptions = new MongoClientOptions.Builder().connectTimeout(timeout)
						.connectionsPerHost(connectionsPerHost).socketKeepAlive(true)
						.threadsAllowedToBlockForConnectionMultiplier(poolMultiplier).build();
				
				
				// 采取默认Mongo配置
				client = new MongoClient(seeds, clientOptions);
				
				if (client != null) {
					MongoDataSource.dbMap.put(MongoDataSource.DEFAULT, this);
				}
			} else {
				
				// client配置信息 (version3.0)
				MongoClientOptions clientOptions = new MongoClientOptions.Builder().connectTimeout(timeout)
						.connectionsPerHost(connectionsPerHost).socketKeepAlive(true)
						.threadsAllowedToBlockForConnectionMultiplier(poolMultiplier).build();
				// 采取默认Mongo配置
				client = new MongoClient(new ServerAddress(new InetSocketAddress(StringUtils.isEmpty(hostStr) ? ip : hostStr, port)), clientOptions);
			}
		}
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
				MongoClientOptions clientOptions = new MongoClientOptions.Builder().connectTimeout(timeout)
						.connectionsPerHost(connectionsPerHost).socketKeepAlive(true)
						.threadsAllowedToBlockForConnectionMultiplier(poolMultiplier).build();
				
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
	
	public static void main(String[] args) {
		DBManager manager = MongoDataSource.dbMap.get(MongoDataSource.DEFAULT);
		
		MongoDatabase database = manager.getDB("TPP");
		
		
		MongoCollection<Document> collection = database.getCollection("TPP_CONFIG");
		
		System.out.println(collection.count());
		
		FindIterable<Document> iterable = collection.find();
		
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			
			Document document = cursor.next();
			System.out.println(document);
			System.out.println(document.toJson());
		}
	}

}

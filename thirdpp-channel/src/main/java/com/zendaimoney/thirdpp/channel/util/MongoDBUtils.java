package com.zendaimoney.thirdpp.channel.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zendaimoney.thirdpp.channel.conf.MongoConfig;
import com.zendaimoney.thirdpp.channel.exception.PlatformErrorCode;
import com.zendaimoney.thirdpp.channel.exception.PlatformException;
import com.zendaimoney.thirdpp.channel.mongo.DBManager;
import com.zendaimoney.thirdpp.channel.mongo.MongoDataSource;

/**
 * MongoDB 操作工具
 * 
 * @author mencius 2015-06-10
 */
public class MongoDBUtils {

	// 日志工具类
	public static Log logger = LogFactory.getLog(MongoDBUtils.class);

	private MongoConfig mongoConfig;

	/**
	 * 获取MongoDB的collection操作对象
	 * 
	 * @param dbName
	 * @param collectionName
	 * @return
	 */
	public static MongoCollection<Document> getMongoCollection(String dbName,
			String collectionName) {

		DBManager dbManager = MongoDataSource.dbMap
				.get(MongoDataSource.DEFAULT);
		if (dbManager == null) {
			dbManager = new DBManager();
		}
		MongoDatabase db = dbManager.getDB(dbName);
		return db.getCollection(collectionName);
	}

	/**
	 * 加载指定路径下配置文件
	 * 
	 * @param path
	 */
	public void loadFromFile(String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				logger.error("File " + path + " is not exists");
				throw new PlatformException(
						PlatformErrorCode.READ_CONFIG_ERROR, "File " + path
								+ " is not exists");
			}
			Ini conf = new Ini(file);
			this.populateAttributes(conf);
			logger.info("Load file " + path + " sucessful");
		} catch (IOException e) {
			logger.error("Parse configuration failed,path=" + path);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		} catch (Exception e) {
			logger.error("Parse configuration failed,path=" + path);
			throw new PlatformException(PlatformErrorCode.READ_CONFIG_ERROR,
					ExceptionUtil.getExceptionMessage(e));
		}

	}

	private void populateAttributes(Ini conf) {
		this.populateMongoDBConf(conf);
	}

	/**
	 * 
	 * @param conf
	 */
	private void populateMongoDBConf(Ini conf) {

		Section section = conf.get("mongodb");
		if (section != null) {
			mongoConfig = new MongoConfig();
			mongoConfig.setDbName(section.get("dbName"));
			mongoConfig.setConnectionDescriptor(section
					.get("connectionDescriptor"));
			mongoConfig.setPoolSize(Integer.valueOf(section.get("poolSize")));
			mongoConfig.setConnectTimeout(Long.valueOf(section
					.get("connectTimeout")));

			MongoConfig.setMongoConfig(mongoConfig);
		}

	}

}

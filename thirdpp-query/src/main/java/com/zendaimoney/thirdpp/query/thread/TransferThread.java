package com.zendaimoney.thirdpp.query.thread;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.query.conf.MongoConfig;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.conf.TransferConfig;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;
import com.zendaimoney.thirdpp.query.service.impl.TransferHandlerImpl;

/**
 * mongo记录转移线程
 * @author mencius
 *
 */
public class TransferThread implements Runnable {

	// 日志工具类
	public static Log logger = LogFactory.getLog(TransferThread.class);
	
	public TransferConfig transferConfig = ServerConfig.transferConfig;
	
	/**
	 * 线程入口
	 */
	@Override
	public void run() {
		
		// 获取mongo集合map
		Map<String, CollectionConfig> collectionMap =  MongoConfig.getCollectionMap();
		Thread.currentThread().setName("transfer");
		String threadInfo = Thread.currentThread().getName() + "-"+ Thread.currentThread().getId();
		while (true) {
			
			try {
				// 遍历集合map
				for (String key : collectionMap.keySet()) {
					
					try {
						// 获取集合配置信息
						CollectionConfig collectionConfig = collectionMap.get(key);
						
						// 只处理FINAL集合
						if (collectionConfig != null && StringUtils.isBlank(collectionConfig.getNextCollection())) {
							TransferHandlerImpl handler = new TransferHandlerImpl(collectionConfig.getCollectionName());
							
							// 数据转移处理
							handler.transfer();
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						continue;
					}
				}
				
				logger.info(threadInfo);
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				try {
					// 暂停 SleepTime 
					Thread.sleep(transferConfig.getSleepTime());
				} catch (Exception e) {
					logger.error("业务处理线程休眠异常", e);
				}
			}
		}
	}

}

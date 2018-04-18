package com.zendaimoney.thirdpp.query.thread;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;

import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.exception.PlatformException;
import com.zendaimoney.thirdpp.query.mongo.CollectionConfig;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.mq.producter.AmqpProductService;
import com.zendaimoney.thirdpp.query.service.CollectionHandler;
import com.zendaimoney.thirdpp.query.service.impl.CollectionHandlerImpl;
import com.zendaimoney.thirdpp.query.util.CalendarUtils;
import com.zendaimoney.thirdpp.query.util.Constants;

public class QueryThread implements Runnable {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(QueryThread.class);
	
	private CollectionHandler collectionHandler;
	
	private AmqpProductService amqpProductService;
	
	/**
	 * 应用程序名称
	 */
	private String appName;
	
	/**
	 * 集合名称
	 */
	private CollectionConfig collectionConfig;

	// 线程正常休眠时间
	private long sleepTime;
	// 有待发数据时线程休眠时间
	private long notEmptySleepTime;
	// 运行异常时线程休眠时间
	private long errorSleepTime;
	// 运行失败次数峰值(超过该峰值，系统自动告警)
	private long maxWarnNum;
	// 程序运行失败次数，根据系统运行情况进行统计
	private long errorNum = 0;
	
	public QueryThread(String appName, CollectionConfig collectionConfig, AmqpProductService amqpProductService) {
		this.appName = appName;
		this.collectionConfig = collectionConfig;
		// 线程正常休眠时间
		sleepTime = collectionConfig.getSleepTime(); // 休眠时间
		// 有待发数据时线程休眠时间
		notEmptySleepTime = ServerConfig.systemConfig.getNotEmptySleepTime();
		// 运行异常时线程休眠时间
		errorSleepTime = ServerConfig.systemConfig.getErrorSleepTime();
		// 运行失败次数峰值(超过该峰值，系统自动告警)
		maxWarnNum = ServerConfig.systemConfig.getMaxWarnNum();
		this.amqpProductService = amqpProductService;
		
		collectionHandler = new CollectionHandlerImpl(collectionConfig.getCollectionName());
	}

	@SuppressWarnings({ "static-access" })
	@Override
	public void run() {
		
		Thread.currentThread().setName("Thread-" + Thread.currentThread().getId() +"-[" + collectionConfig.getCollectionName() + "]");
		String threadInfo = Thread.currentThread().getName();
		// 线程休眠时间
		long threadSleepTime = sleepTime;
		while (true) {
			try {
				// 查询条件
				Document paraDoc = new Document();
				paraDoc.put("source", appName);
				paraDoc.put("dealFlag", Constants.TPP_QUERY_DEALFLAG_INIT);
				
				if (StringUtils.isBlank(collectionConfig.getNextCollection())) {
					
					paraDoc.put("queryCount", new Document("$lt", collectionConfig.getMaxQueryNum()));// 最大查询次数
				}
				int sort = 1; // sort 1:升序； -1：降序
				
				// 开始时间(根据每个集合设置的数据驻留时间，计算查询开始时间：当前时间-驻留时间)
				String startTime = CalendarUtils.getTimeByMillAgo(CalendarUtils.getMillFormatNow(), Long.valueOf(collectionConfig.getResideTime()), CalendarUtils.LONG_FORMAT_MILL);
				
				// 获取该MongoDB集合下的前N条记录
				List<Document> lists = collectionHandler.query(paraDoc, startTime, collectionConfig.getDealSize(), sort);
				
				for (int i = 0; i < lists.size(); i++) {
					MongoQueryVO vo = new MongoQueryVO();
					vo.fromDbObject(lists.get(i));
					try {
						// 争夺资源
						boolean lock = collectionHandler.update(collectionConfig.getCollectionName(), vo, Constants.TPP_QUERY_DEALFLAG_DEALED);
						if (lock) {
							// 发送MQ消息
							amqpProductService.push(vo, collectionConfig.getCollectionName());
						}
					} catch (Exception e) {
						collectionHandler.moveAndDelete(collectionConfig.getCollectionName(), vo);
					}
				}
				// 统计本次处理量
				if (lists != null) {
					
					logger.info(threadInfo + ",deal size:" + lists.size());
				}
				
				// 运行失败次数清0
				errorNum = 0;
			} catch (PlatformException e) {
				logger.error(threadInfo + "业务处理线程运行异常", e);
				threadSleepTime = errorSleepTime;
				errorNum++;
			} catch (Exception ex) {
				logger.error(threadInfo + "系统运行异常", ex);
				threadSleepTime = errorSleepTime;
				errorNum++;
			} finally {
				try {
					if (errorNum >= maxWarnNum) {
						logger.warn(threadInfo + "失败次数达到最大值" + maxWarnNum + ",发送告警.");
						errorNum = 0;
						// 发送告警
					}
					// 线程休眠
					Thread.currentThread().sleep(threadSleepTime);
					
				} catch (Exception e) {
					logger.error(threadInfo + "业务处理线程休眠异常", e);
				}

			}
		}
	}

}

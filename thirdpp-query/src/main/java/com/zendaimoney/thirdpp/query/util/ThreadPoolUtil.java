package com.zendaimoney.thirdpp.query.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.query.dao.ThreadPoolInfoDao;
import com.zendaimoney.thirdpp.query.entity.ThreadPoolInfo;
import com.zendaimoney.thirdpp.query.exception.PlatformException;

@Component
public class ThreadPoolUtil {

	private static Logger logger = Logger.getLogger(ThreadPoolUtil.class);
	
	@Resource(name = "threadPoolInfoDao")
	private ThreadPoolInfoDao poolInfoDao;
	
	public static Map<String, ThreadPoolExecutor> excutorMap = new ConcurrentHashMap<String, ThreadPoolExecutor>();
	
	/**
	 * 各线程池初始化方法
	 * 
	 * @param bizType
	 *            业务类型(可为空)
	 * @param infType
	 *            接口类型(必传)
	 * @param infType
	 *            通道编码  (可为空) 
	 * @throws SQLException 
	 */
	public void init() throws SQLException {
		// 根据业务类型、接口类型、支付通道编码从TPP_SYS_T_THREAD_POOL表各生效的线程池参数
		List<ThreadPoolInfo> list = poolInfoDao.queryThreadPoolInfos(null, null, null);
		for (int i = 0; i < list.size(); i++) {
			ThreadPoolInfo poolModel = list.get(i);
			ThreadPoolExecutor executor = new ThreadPoolExecutor(
					poolModel.getMinSize(), poolModel.getMaxSize(), 500,
					TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
							poolModel.getQueueSize()),
					new ThreadPoolExecutor.AbortPolicy());
			// 业务类型+接口类型+支付通道编码作为线程池的key
			String key = poolModel.getBizType().trim() + poolModel.getInfType().trim()
					+ poolModel.getPaySysNo().trim();
			excutorMap.put(key, executor);
			logger.debug("线程池初始化key=" + key + "，min=" + poolModel.getMinSize()
					+ ",max=" + poolModel.getMaxSize() + ",queueSize="
					+ poolModel.getQueueSize());
		}
	}

	/**
	 * 各线程池初始化方法
	 * 
	 * @param bizType
	 *            业务类型(可为空)
	 * @param infType
	 *            接口类型(必传)
	 * @param infType
	 *            通道编码  (可为空) 
	 * @throws SQLException 
	 */
	public void init(String bizType, String infType, String paySysNo) throws SQLException {
		// 根据业务类型、接口类型、支付通道编码从TPP_SYS_T_THREAD_POOL表各生效的线程池参数
		List<ThreadPoolInfo> list = poolInfoDao.queryThreadPoolInfos(bizType, infType, paySysNo);
		for (int i = 0; i < list.size(); i++) {
			ThreadPoolInfo poolModel = list.get(i);
			ThreadPoolExecutor executor = new ThreadPoolExecutor(
					poolModel.getMinSize(), poolModel.getMaxSize(), 500,
					TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
							poolModel.getQueueSize()),
					new ThreadPoolExecutor.CallerRunsPolicy());
			// 业务类型+接口类型+支付通道编码作为线程池的key
			String key = poolModel.getBizType().trim() + poolModel.getInfType().trim()
					+ poolModel.getPaySysNo().trim();
			excutorMap.put(key, executor);
			logger.debug("线程池初始化key=" + key + "，min=" + poolModel.getMinSize()
					+ ",max=" + poolModel.getMaxSize() + ",queueSize="
					+ poolModel.getQueueSize());
		}
	}

	/**
	 * 更新各线程池参数
	 * 
	 * @param bizType
	 *            业务类型(可为空)
	 * @param infType
	 *            接口类型(必传)
	 * @param infType
	 *            通道编码(可为空)
	 * @throws SQLException 
	 */
	public void update(String bizType, String infType, String paySysNo) throws SQLException {
		// 根据业务类型、接口类型、支付通道编码从TPP_SYS_T_THREAD_POOL表各生效的线程池参数
		List<ThreadPoolInfo> list = poolInfoDao.queryThreadPoolInfos(bizType, infType, paySysNo);
		for (int i = 0; i < list.size(); i++) {
			ThreadPoolInfo poolModel = list.get(i);
			String key = poolModel.getBizType().trim() + poolModel.getInfType().trim()
					+ poolModel.getPaySysNo().trim();
			ThreadPoolExecutor executor = excutorMap.get(key);
			if (executor == null) { // 线程池不存在，创建新的
				logger.debug("线程池更新重建！key=" + key + "，min="
						+ poolModel.getMinSize() + ",max="
						+ poolModel.getMaxSize() + ",queueSize="
						+ poolModel.getQueueSize());
				executor = new ThreadPoolExecutor(poolModel.getMinSize(),
						poolModel.getMaxSize(), 500, TimeUnit.MILLISECONDS,
						new ArrayBlockingQueue<Runnable>(poolModel
								.getQueueSize()),
						new ThreadPoolExecutor.AbortPolicy());
				excutorMap.put(key, executor);
			} else { // 更新线程池
				executor.setCorePoolSize(poolModel.getMinSize());
				executor.setMaximumPoolSize(poolModel.getMaxSize());
				logger.debug("线程池更新！key=" + key + "，min="
						+ poolModel.getMinSize() + ",max="
						+ poolModel.getMaxSize() + ",queueSize="
						+ poolModel.getQueueSize());
			}
		}
	}


	/**
	 * 任务加入线程池
	 * @param runnable  执行线程
	 * @param bizType	业务类型
	 * @param infType	接口类型
	 * @param paySysNo	支付通道编码
	 * @return
	 * @throws Exception 
	 */
	public static String put(Runnable runnable, String bizType, String infType, String paySysNo) throws PlatformException {
		String key = bizType.trim() + infType.trim() + paySysNo.trim();
		ThreadPoolExecutor executor = excutorMap.get(key);
		try {
			
			if (executor == null) 
				throw new PlatformException("key:" + key + ", 线程池为空 ");
			
			executor.execute(runnable);
			
			logger.debug("key=" + key + "加入线程池成功！活动线程数--->"
					+ executor.getActiveCount() + "|| 队列实际数---->"
					+ executor.getQueue().size() + "|| 最小线程数---->"
					+ executor.getCorePoolSize() + "||最大线程数---->"
					+ executor.getMaximumPoolSize() + "||最大线程峰值---->"
					+ executor.getLargestPoolSize() + "||");
		} catch (RejectedExecutionException e) {
			logger.error("key=" + key + "线程池队列已满！活动线程数--->"
					+ executor.getActiveCount() + "|| 队列实际数---->"
					+ executor.getQueue().size() + "|| 最小线程数---->"
					+ executor.getCorePoolSize() + "||最大线程数---->"
					+ executor.getMaximumPoolSize() + "||最大线程峰值---->"
					+ executor.getLargestPoolSize() + "||");
//			return "fail";
			
			throw new PlatformException("key=" + key + "线程池队列已满！活动线程数--->"
					+ executor.getActiveCount() + "|| 队列实际数---->"
					+ executor.getQueue().size() + "|| 最小线程数---->"
					+ executor.getCorePoolSize() + "||最大线程数---->"
					+ executor.getMaximumPoolSize() + "||最大线程峰值---->"
					+ executor.getLargestPoolSize() + "||");
		}
		return "success";
	}
}

package com.zendaimoney.thirdpp.notify.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.zendaimoney.thirdpp.notify.entity.PoolModel;

public class ThreadPoolUtil {

	private static Logger logger = Logger.getLogger("FILE");

	private static Map<String, ThreadPoolExecutor> excutorMap = new ConcurrentHashMap<String, ThreadPoolExecutor>();

	/**
	 * 各线程池初始化方法
	 * 
	 * @param bizType
	 *            业务类型(可为空)
	 * @param infType
	 *            接口类型(必传)
	 * @param infType
	 *            通道编码  (可为空) 
	 */
	public void init(String bizType, String infType, String paySysNo) {
		// 根据业务类型、接口类型、支付通道编码从TPP_SYS_T_THREAD_POOL表各生效的线程池参数
		List<PoolModel> list = null;
		for (int i = 0; i < list.size(); i++) {
			PoolModel poolModel = list.get(i);
			ThreadPoolExecutor executor = new ThreadPoolExecutor(
					poolModel.getMinSize(), poolModel.getMaxSize(), 500,
					TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(
							poolModel.getQueueSize()),
					new ThreadPoolExecutor.AbortPolicy());
			// 业务类型+接口类型+支付通道编码作为线程池的key
			String key = poolModel.getBizType() + poolModel.getInfType()
					+ poolModel.getPaySysNo();
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
	 */
	public void update(String bizType, String infType, String paySysNo) {
		// 根据业务类型、接口类型、支付通道编码从TPP_SYS_T_THREAD_POOL表各生效的线程池参数
		List<PoolModel> list = null;
		for (int i = 0; i < list.size(); i++) {
			PoolModel poolModel = list.get(i);
			String key = poolModel.getBizType() + poolModel.getInfType()
					+ poolModel.getPaySysNo();
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
	 */
	public static String put(Runnable runnable, String bizType, String infType,
			String paySysNo) {
		String key = bizType + infType + paySysNo;
		ThreadPoolExecutor executor = excutorMap.get(key);
		if (executor == null) {
			logger.error("没有找到线程队列！key = " + key);
			return "fail";
		}
		try {
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
			return "fail";
		}
		return "success";
	}
}

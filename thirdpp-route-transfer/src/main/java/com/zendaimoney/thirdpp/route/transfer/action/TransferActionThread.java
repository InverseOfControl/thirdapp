package com.zendaimoney.thirdpp.route.transfer.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.route.transfer.conf.ServerConfig;
import com.zendaimoney.thirdpp.route.transfer.entity.Response;
import com.zendaimoney.thirdpp.route.transfer.exception.PlatformException;


/**
 * 转发线程
 * 
 * @author 00231257
 * 
 */
public class TransferActionThread implements Runnable {

	// 日志工具类
	public static Log logger = LogFactory.getLog(TransferActionThread.class);

	/**
	 * 转发action
	 */
	private Action action;

	/**
	 * 线程名称
	 */
	private String threadName;

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

	/**
	 * 
	 * @param action
	 * @param thirdType
	 * @param name
	 */
	public TransferActionThread(Action action, String threadName) {
		this.action = action;
		this.threadName = threadName;
		// 线程正常休眠时间
		sleepTime = ServerConfig.systemConfig.getSleepTime();
		// 有待发数据时线程休眠时间
		notEmptySleepTime = ServerConfig.systemConfig.getNotEmptySleepTime();
		// 运行异常时线程休眠时间
		errorSleepTime = ServerConfig.systemConfig.getErrorSleepTime();
		// 运行失败次数峰值(超过该峰值，系统自动告警)
		maxWarnNum = ServerConfig.systemConfig.getMaxWarnNum();
	}

	@Override
	public void run() {
		// 线程休眠时间
		long threadSleepTime = sleepTime;
		Thread.currentThread().setName(this.threadName);
		Response response = null;
        while (true) {
            try {
                response = action.execute(threadName);
                // 如果没有待处理数据，就按照线程正常的休眠时间进行休眠，避免消耗系统资源
                if (response.isEmpty()) {
                    threadSleepTime = sleepTime;
                    // 如果有待处理数据，休眠时间就会越短以便尽快发送完数据
                } else {
                    threadSleepTime = notEmptySleepTime;
                }
                // 运行失败次数清0
                errorNum = 0;
            } catch (PlatformException e) {
                logger.error("业务处理线程运行异常", e);
                threadSleepTime = errorSleepTime;
                errorNum++;
            } finally {
                try {
                    if (errorNum >= maxWarnNum) {
                        logger.warn("失败次数达到最大值" + maxWarnNum + ",发送告警.");
                        errorNum = 0;
                        // 发送告警
                    }
                    Thread.sleep(threadSleepTime);
                } catch (Exception e) {
                    logger.error("业务处理线程休眠异常", e);
                }

            }
        }
    }
}

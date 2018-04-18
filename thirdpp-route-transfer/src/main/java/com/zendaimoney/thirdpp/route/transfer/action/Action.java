package com.zendaimoney.thirdpp.route.transfer.action;


import com.zendaimoney.thirdpp.route.transfer.entity.Response;
import com.zendaimoney.thirdpp.route.transfer.exception.PlatformException;


/**
 * 
 * @author 00231257
 * 
 * @param <T1>
 *            任务类
 * @param <T2>
 *            交易明细类
 */
public abstract class Action<K,V> {

	/**
	 * 运行方法
	 * 
	 * @param thirdType
	 *            第三方通道编码
	 * @param name
	 *            转发程序名称
	 * @return
	 */
	public Response execute(String name)
			throws PlatformException {
		return transfer();
	}

	
	/**
	 * 将task数据转移到交易明细表
	 * 
	 * @param name 线程名
	 * @throws PlatformException
	 */
	protected abstract Response transfer() throws PlatformException;


}

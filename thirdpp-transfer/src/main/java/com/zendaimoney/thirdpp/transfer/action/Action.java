package com.zendaimoney.thirdpp.transfer.action;

import java.util.List;

import com.zendaimoney.thirdpp.transfer.entity.Response;
import com.zendaimoney.thirdpp.transfer.exception.PlatformException;

/**
 * 
 * @author 00231257
 * 
 * @param <T1>
 *            任务类
 * @param <T2>
 *            交易明细类
 */
public abstract class Action<K, V> {

	/**
	 * 运行方法
	 * 
	 * @param thirdType
	 *            第三方通道编码
	 * @param name
	 *            转发程序名称
	 * @return
	 */
	public Response execute(String thirdType, String name)
			throws PlatformException {
		return process(preProcess(thirdType, name));
	}

	/**
	 * 业务操作前操作
	 * 
	 * @param thirdType
	 * @param name
	 * @return
	 * @throws PlatformException
	 */
	protected abstract K preProcess(String thirdType, String name)
			throws PlatformException;

	/** 业务核心处理 */
	protected abstract Response process(K info) throws PlatformException;

	/**
	 * 将task数据转移到交易明细表
	 * 
	 * @param task
	 * @throws PlatformException
	 */
	protected abstract void transfer(K info) throws PlatformException;

	/**
	 * 将交易数据发送到通道
	 * 
	 * @param info
	 * @throws PlatformException
	 */
	protected abstract void callChannel(List<V> infos) throws PlatformException;

}

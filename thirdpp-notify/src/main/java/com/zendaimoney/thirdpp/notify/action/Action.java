package com.zendaimoney.thirdpp.notify.action;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.zendaimoney.thirdpp.notify.entity.TotalOrder;
import com.zendaimoney.thirdpp.notify.exception.PlatformException;
import com.zendaimoney.thirdpp.notify.util.LogPrn;

/**
 * 
 * @author 00231257
 * 
 */
public abstract class Action {

	private static final LogPrn logger = new LogPrn(Action.class);

	/**
	 * 查询订单明细
	 * 
	 * @throws PlatformException
	 */
	protected abstract TotalOrder query(long taskId) throws SQLException;

	/**
	 * 修改订单数据
	 * 
	 * @throws PlatformException
	 */
	protected abstract int modify(TotalOrder totalOrder)
			throws DataAccessException;

	/**
	 * 根据交易流水查询taskId
	 * 
	 * @throws PlatformException
	 */
	protected abstract Long queryTaskIdByFlow(String tradeFlow)
			throws SQLException;
	
	/**
	 * 随机查询失败原因
	 * @param taskId
	 * @return
	 * @throws SQLException
	 */
	protected abstract String queryFailReason(Long taskId)
			throws SQLException;

	/** 根据taskId查询执行处理入口 */
	public TotalOrder executeQuery(long taskId) {
		TotalOrder totalOrder = null;
		try {
			totalOrder = query(taskId);
		} catch (PlatformException e) {
			logger.error("====", e);
		} catch (Exception e) {
			logger.error("====", e);
		}
		return totalOrder;
	}

	/**
	 * 更新执行处理入口
	 * 
	 * @throws SQLException
	 */
	public int executeUpdate(TotalOrder totalOrder) throws DataAccessException {
		int updateNum = 0;
		try {
			updateNum = modify(totalOrder);
		} catch (PlatformException e) {
			logger.error("====", e);
		}
		return updateNum;
	}

	/**
	 * 根据流水查询taskId
	 * 
	 * @throws SQLException
	 */
	public Long executeQueryByFlow(String tradeFlow) throws SQLException {
		Long taskId = null;
		try {
			taskId = queryTaskIdByFlow(tradeFlow);
		} catch (PlatformException e) {
			logger.error("====", e);
		}
		return taskId;
	}
	
	public String executeQueryFailReason(Long taskId) throws SQLException {
		String failReason = "";
		try {
			failReason = queryFailReason(taskId);
		} catch (PlatformException e) {
			logger.error("====", e);
		}
		return failReason;
	}

}

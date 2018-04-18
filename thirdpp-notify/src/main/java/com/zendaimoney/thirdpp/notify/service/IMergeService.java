package com.zendaimoney.thirdpp.notify.service;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.zendaimoney.thirdpp.notify.entity.MqMessage;
import com.zendaimoney.thirdpp.notify.entity.TotalOrder;

public interface IMergeService {

	/**
	 * 合单操作
	 * 
	 * @param msg
	 * @throws SQLException
	 */
	TotalOrder merge(MqMessage msg) throws SQLException, DataAccessException;

	/**
	 * 更新task，插入通知表
	 * 
	 * @param bizType
	 * @param order
	 * @throws DataAccessException
	 */
	void updateTaskAndSaveNotify(TotalOrder order) throws DataAccessException,
			SQLException;

	/**
	 * 放入合单消息队列
	 * 
	 * @param sendMessage
	 */
	void putMergeMq(String sendMessage);
	
	/**
	 * 对待处理的合单信息进行处理
	 */
	void doWaitingMerge() throws Exception;
}

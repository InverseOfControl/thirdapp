package com.zendaimoney.trust.channel.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.trust.channel.dao.BatchOperDao;
import com.zendaimoney.trust.channel.entity.BatchOper;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;

/**
 * 批量文件操作service
 * @author mencius
 *
 */
@Service
@Transactional
public class BatchOperService {

	/**
	 * 批量文件操作Dao
	 */
	@Resource(name = "batchOperDao")
	private BatchOperDao batchOperDao;

	/**
	 * 更新批量文件操作
	 * @param batchOper
	 */
	public boolean update(BatchOper batchOper) {
		// 更新结果,返回更新记录
		int result = batchOperDao.update(batchOper);
		// 更新记录为1 更新成功，否则
		if (result == 1) {
			return true;
		} else {
//			throw new PlatformException(PlatformErrorCode.BATCH_OPER_UPDATE_ERROR, PlatformErrorCode.BATCH_OPER_UPDATE_ERROR.getDefaultMessage());
			return false;
		}
	}
}

package com.zendaimoney.thirdpp.transfer.service;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.transfer.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask;

@Service
@Transactional
public class CollectTaskService {

	@Resource(name = "collectTaskDao")
	private CollectTaskDao collectTaskDao;

	/**
	 * collectTask入库操作
	 * 
	 * @param collectTask
	 */
	public void insert(CollectTask collectTask) {
		collectTaskDao.insert(collectTask);
	}

	/**
	 * collectTask修改操作
	 * 
	 * @param collectTask
	 */
	public int update(CollectTask collectTask) {
		return collectTaskDao.update(collectTask);
	}

	/**
	 * 获取待发送任务
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public CollectTask queryWaitingSendTask(HashMap<String, Object> param)
			throws SQLException {
		return collectTaskDao.queryWaitingSendTask(param);
	}

}

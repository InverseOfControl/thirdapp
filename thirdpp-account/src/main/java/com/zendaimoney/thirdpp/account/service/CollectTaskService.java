package com.zendaimoney.thirdpp.account.service;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.account.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.account.entity.CollectTask;

@Transactional
@Service
public class CollectTaskService {

	@Resource(name = "collectTaskDao")
	private CollectTaskDao collectTaskDao;

	public void insert(CollectTask collectTask) {
		collectTaskDao.insert(collectTask);
	}
	
	/**
	 * 
	 * @param collectTask
	 */
	public void update(CollectTask collectTask){
		collectTaskDao.update(collectTask);
	}

	public CollectTask getBizTaskByTaskId(long id) throws SQLException{
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		return collectTaskDao.queryCollectTaskByTaskId(param);
	}
}

package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.trade.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.trade.dao.RequestDao;
import com.zendaimoney.thirdpp.trade.entity.CollectInfo;
import com.zendaimoney.thirdpp.trade.entity.CollectTask;
import com.zendaimoney.thirdpp.trade.entity.Request;

@Transactional
@Service
public class CollectTaskService {

	@Resource(name = "collectTaskDao")
	private CollectTaskDao collectTaskDao;

	@Resource(name = "requestDao")
	private RequestDao requestDao;

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;
	

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

	/**
	 * 异步代收功能
	 * 
	 * @param request
	 * @param tasks
	 * @throws SQLException
	 */
	public void asynCollect(Request request, List<CollectTask> tasks)
			throws SQLException {
		requestDao.insert(request);
		collectTaskDao.batchInsert(tasks);
	}

	/**
	 * 同步代收功能
	 * 
	 * @param request
	 * @param task
	 * @param info
	 * @throws SQLException
	 */
	public void syncCollect(Request request, CollectTask task, CollectInfo info)
			throws SQLException {
		//插入并获取taskid
		long taskId = collectTaskDao.insertReturnKey(task);
		requestDao.insert(request);
		//交易明细中设置taskid
		info.setTaskId(taskId);
		collectInfoDao.insert(info);
	}
	
	/**
	 * 查询重复发送任务，主要基于bizFlow来判断
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public CollectTask queryCollectTaskByBizflowAndBizSysNo(String bizFlow,String bizSysNo)
			throws SQLException {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("bizFlow", bizFlow);
		param.put("bizSysNo", bizSysNo);
		return collectTaskDao.queryCollectTaskByBizflowAndBizSysNo(param);
	}

}

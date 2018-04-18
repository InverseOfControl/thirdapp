package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTCollectTask;

public interface TTppTradeCollectTaskService {
	/**
	 * 查询代扣任务列表
	 * @return
	 */
	List<TppTradeTCollectTask> getTaskList(Map<String, Object> params);
	
	List select_tppTradeTCollectTaskList(Map<String, Object> params);
	
	Integer select_tppTradeTCollectTaskList_count(Map<String, Object> params);
	
	List select_sysAppList(Map<String, Object> params);
	
	List select_summary(Map<String, Object> params);
	
	Integer select_success_count(Map<String, Object> params);
}

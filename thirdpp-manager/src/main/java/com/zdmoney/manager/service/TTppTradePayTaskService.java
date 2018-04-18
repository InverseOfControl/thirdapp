package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTPayTask;

public interface TTppTradePayTaskService {
	/**
	 * 查询代付任务列表
	 * @return
	 */
	List<TppTradeTPayTask> getTaskList(Map<String, Object> params);
	
	List select_tppTradeTPayTaskList(Map<String, Object> params);
	    
	Integer select_tppTradeTPayTaskList_count(Map<String, Object> params);
	
	List select_summary(Map<String, Object> params);
	
	Integer select_success_count(Map<String, Object> params);
}

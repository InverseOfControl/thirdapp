package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTCollectTaskMapper;
import com.zdmoney.manager.models.TppTradeTCollectTask;
import com.zdmoney.manager.service.TTppTradeCollectTaskService;

@Service
public class TTppTradeCollectTaskServiceImpl implements TTppTradeCollectTaskService {
	
	@Autowired
	private TppTradeTCollectTaskMapper tppTradeTCollectTaskMapper;
	/**
	 * 查询代扣任务列表
	 * @return
	 */
	@Override
	public List<TppTradeTCollectTask> getTaskList(Map<String, Object> params) {
		return tppTradeTCollectTaskMapper.getTaskList(params);
	}
	@Override
	public List select_tppTradeTCollectTaskList(Map<String, Object> params) {
		return tppTradeTCollectTaskMapper.select_tppTradeTCollectTaskList(params);
	}
	@Override
	public Integer select_tppTradeTCollectTaskList_count(
			Map<String, Object> params) {
		return tppTradeTCollectTaskMapper.select_tppTradeTCollectTaskList_count(params);
	}
	@Override
	public List select_sysAppList(Map<String, Object> params) {
		return tppTradeTCollectTaskMapper.select_sysAppList(params);
	}
	
	@Override
	public List select_summary(Map<String, Object> params) {
		return tppTradeTCollectTaskMapper.select_summary(params);
	} 
	
	@Override
	public Integer select_success_count(Map<String, Object> params){
		return tppTradeTCollectTaskMapper.select_success_count(params);
	}
}

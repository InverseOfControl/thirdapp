package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTPayTaskMapper;
import com.zdmoney.manager.models.TppTradeTPayTask;
import com.zdmoney.manager.service.TTppTradePayTaskService;

@Service
public class TTppTradePayTaskServiceImpl implements TTppTradePayTaskService {
	
	@Autowired
	private TppTradeTPayTaskMapper tppTradeTPayTaskMapper; 
	@Override
	public List<TppTradeTPayTask> getTaskList(Map<String, Object> params) {
		return tppTradeTPayTaskMapper.getTaskList(params);
	}
	@Override
	public List select_tppTradeTPayTaskList(Map<String, Object> params) {
		return tppTradeTPayTaskMapper.select_tppTradeTPayTaskList(params);
	}
	@Override
	public Integer select_tppTradeTPayTaskList_count(Map<String, Object> params) {
		return tppTradeTPayTaskMapper.select_tppTradeTPayTaskList_count(params);
	}
	@Override
	public List select_summary(Map<String, Object> params) {
		return tppTradeTPayTaskMapper.select_summary(params);
	}
	@Override
	public Integer select_success_count(Map<String, Object> params) {
		return tppTradeTPayTaskMapper.select_success_count(params);
	}

}

package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTPayTask;

public interface TppTradeTPayTaskMapper {
    int insert(TppTradeTPayTask record);

    int insertSelective(TppTradeTPayTask record);
    
    public List<TppTradeTPayTask> getTaskList(Map<String, Object> params);
    
    List select_tppTradeTPayTaskList(Map<String, Object> params);
    
    Integer select_tppTradeTPayTaskList_count(Map<String, Object> params);
    
    List select_summary(Map<String, Object> params);
	
	Integer select_success_count(Map<String, Object> params);
}
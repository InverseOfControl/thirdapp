package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.zdmoney.manager.models.TppTradeTCollectTask;

public interface TppTradeTCollectTaskMapper {
    int insert(TppTradeTCollectTask record);

    int insertSelective(TppTradeTCollectTask record);
    
    public List<TppTradeTCollectTask> getTaskList(Map<String, Object> params);
    
	public List select_tppTradeTCollectTaskList(Map<String, Object> params);
	
	public Integer select_tppTradeTCollectTaskList_count(Map<String, Object> params);
	
	List select_sysAppList(Map<String, Object> params);
	
	List select_summary(Map<String, Object> params);
	
	Integer select_success_count(Map<String, Object> params);
}
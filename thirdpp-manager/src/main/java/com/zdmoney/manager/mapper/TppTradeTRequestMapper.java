package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTRequest;

public interface TppTradeTRequestMapper {
    int insert(TppTradeTRequest record);

    int insertSelective(TppTradeTRequest record);
    
    List<TppTradeTRequest> getRequestList(Map<String, Object> params);
    
    List select_tppTradeTRequestList(Map<String, Object> params);
	
	Integer select_tppTradeTRequestList_count(Map<String, Object> params);
}
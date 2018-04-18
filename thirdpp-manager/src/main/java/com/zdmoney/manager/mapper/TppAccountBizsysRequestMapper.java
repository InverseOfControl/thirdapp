package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountBizsysRequest;

public interface TppAccountBizsysRequestMapper {
	
    int insert(TppAccountBizsysRequest record);

    int insertSelective(TppAccountBizsysRequest record);
    
    public List select_tppAccountBizsysRequestList(Map<String, Object> params);
	
	public int select_tppAccountBizsysRequestList_count(Map<String, Object> params);
}
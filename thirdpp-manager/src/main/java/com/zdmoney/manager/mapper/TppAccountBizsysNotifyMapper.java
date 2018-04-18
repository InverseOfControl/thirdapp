package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountBizsysNotify;

public interface TppAccountBizsysNotifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TppAccountBizsysNotify record);

    int insertSelective(TppAccountBizsysNotify record);

    TppAccountBizsysNotify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppAccountBizsysNotify record);

    int updateByPrimaryKey(TppAccountBizsysNotify record);
    
    public List select_tppAccountBizsysNotifyList(Map<String, Object> params);
	
	public int select_tppAccountBizsysNotifyList_count(Map<String, Object> params);
}
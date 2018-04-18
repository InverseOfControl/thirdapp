package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTWaitingMerge;

public interface TppTradeTWaitingMergeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TppTradeTWaitingMerge record);

    int insertSelective(TppTradeTWaitingMerge record);

    TppTradeTWaitingMerge selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppTradeTWaitingMerge record);

    int updateByPrimaryKey(TppTradeTWaitingMerge record);
    
    List select_tppTradeTWaitingMergeList(Map<String, Object> params);
    
	int select_tppTradeTWaitingMergeList_count(Map<String, Object> params);
}
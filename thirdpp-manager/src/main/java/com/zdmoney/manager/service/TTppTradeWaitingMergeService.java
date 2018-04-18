package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTWaitingMerge;

public interface TTppTradeWaitingMergeService {
	int deleteByPrimaryKey(Long id);

    int insert(TppTradeTWaitingMerge record);

    int insertSelective(TppTradeTWaitingMerge record);

    TppTradeTWaitingMerge selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppTradeTWaitingMerge record);

    int updateByPrimaryKey(TppTradeTWaitingMerge record);
    
    List select_tppTradeTWaitingMergeList(Map<String, Object> params);
    
	int select_tppTradeTWaitingMergeList_count(Map<String, Object> params);
}

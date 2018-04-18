package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTWaitingQuery;

public interface TppTradeTWaitingQueryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TppTradeTWaitingQuery record);

    int insertSelective(TppTradeTWaitingQuery record);

    TppTradeTWaitingQuery selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppTradeTWaitingQuery record);
    
    int updateByPrimaryKeySelectiveHis(TppTradeTWaitingQuery record);

    int updateByPrimaryKey(TppTradeTWaitingQuery record);
    
    List select_tppTradeTWaitingQueryList(Map<String, Object> params);
    
    int select_tppTradeTWaitingQueryList_count(Map<String, Object> params);
    
    int batchCheckedWaitingQuery(Map<String, Object> params);
    
    int batchCheckedWaitingQueryHis(Map<String, Object> params);
    
}
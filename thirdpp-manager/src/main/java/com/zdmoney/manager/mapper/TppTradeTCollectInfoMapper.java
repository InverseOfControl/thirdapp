package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TppTradeTCollectInfo;

public interface TppTradeTCollectInfoMapper {
    int insert(TppTradeTCollectInfo record);

    int insertSelective(TppTradeTCollectInfo record);
    
    public List<TppTradeTCollectInfo> getInfoList(Map<String, Object> params);
    
    public List<TppTradeTCollectInfo> getCollectInfoByTradeFlow(@Param(value="tradeFlow")String tradeFlow);
    
    public List select_tppTradeTCollectInfoList(Map<String, Object> params);
    
    public Integer select_tppTradeTCollectInfoList_count(Map<String, Object> params);
    
    List select_summary(Map<String, Object> params);
}
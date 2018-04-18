package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TppTradeTPayInfo;

public interface TppTradeTPayInfoMapper {
    int insert(TppTradeTPayInfo record);

    int insertSelective(TppTradeTPayInfo record);
    
    List<TppTradeTPayInfo> getInfoList(Map<String, Object> params);
    
    List select_tppTradeTPayInfoList(Map<String, Object> params);
    
    Integer select_tppTradeTPayInfoList_count(Map<String, Object> params);
    
    List select_summary(Map<String, Object> params);
    
    List<TppTradeTPayInfo> getPayInfoByTradeFlow(@Param(value="tradeFlow")String tradeFlow);
}
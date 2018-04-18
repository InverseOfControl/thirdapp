package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTPayInfo;

public interface TTppTradePayInfoService {
	public List<TppTradeTPayInfo> getInfoList(Map<String, Object> params);
	
	List select_tppTradeTPayInfoList(Map<String, Object> params);
    
    Integer select_tppTradeTPayInfoList_count(Map<String, Object> params);
    
    List select_summary(Map<String, Object> params);
    
    List<TppTradeTPayInfo> getPayInfoByTradeFlow(String tradeFlow);
}

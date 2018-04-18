package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TppTradeTNotify;

public interface TTppTradeNotifyService {
	List<TppTradeTNotify> getNotifyList(Map<String, Object> params);
	
	List select_tppTradeTNotifyList(Map<String, Object> params);
    
    Integer select_tppTradeTNotifyList_count(Map<String, Object> params);
    
    List select_tppTradeTNotifyById(@Param(value="notifyId")String notifyId);
    int updateNotifyCount(String id);
    int updateNotifyHisCount(String id);
    int updateNotifyHisAllCount(Map<String, Object> params);
    int updateNotifyAllCount(Map<String, Object> params);
}
 
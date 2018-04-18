package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTRequest;

public interface TTppTradeRequestService {
	List<TppTradeTRequest> getRequestList(Map<String, Object> params);
	
	List select_tppTradeTRequestList(Map<String, Object> params);
	
	Integer select_tppTradeTRequestList_count(Map<String, Object> params);
}

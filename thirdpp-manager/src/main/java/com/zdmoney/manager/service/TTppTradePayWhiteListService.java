package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTPayWhiteList;

public interface TTppTradePayWhiteListService {

	public List getPayWhiteList(Map<String, Object> params);
	
	public int getPayWhiteListCount(Map<String, Object> params);
	
	public List detailWhiteList(Map<String, Object> params);
	
	public void update(TppTradeTPayWhiteList whiteList);
	
	public void insert(TppTradeTPayWhiteList whiteList);
	
	public void delete(List<Integer> idList);
}

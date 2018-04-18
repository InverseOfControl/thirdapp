package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppTradeTPayWhiteList;

public interface TppTradeTPayWhiteListMapper {
	
	List<TppTradeTPayWhiteList> getWhiteList(Map<String, Object> params);
	
	int getPayWhiteListCount(Map<String, Object> params);
	
	List detailWhiteList(Map<String, Object> params);
	
	void update(TppTradeTPayWhiteList whiteList);
	
	void insert(TppTradeTPayWhiteList whiteList);
	
	void delete(List<Integer> idList);
}

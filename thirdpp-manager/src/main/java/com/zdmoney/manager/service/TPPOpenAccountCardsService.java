package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;

/**
 * 
 * @author wyj
 *
 */
public interface TPPOpenAccountCardsService {
	
	List<Map> getOpenAccountCardsList(Map<String, Object> params);
	 
	int getOpenAccountCardsListCount(Map<String, Object> params);
}

package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TPPOpenAccount;

/**
 * 
 * @author wyj
 *
 */
public interface TPPOpenAccountService {
	
	List<Map> getOpenAccountList(Map<String, Object> params);
	 
	int getOpenAccountListCount(Map<String, Object> params);
	
	TPPOpenAccount getOpenAccountById(String id);
}

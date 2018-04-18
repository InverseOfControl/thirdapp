package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TPPOpenAccount;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TppOpenAccountMapper {
	
	List<Map> getOpenAccountList(Map<String, Object> params);
 
	int getOpenAccountListCount(Map<String, Object> params);
	
	TPPOpenAccount getOpenAccountById(String id);
}

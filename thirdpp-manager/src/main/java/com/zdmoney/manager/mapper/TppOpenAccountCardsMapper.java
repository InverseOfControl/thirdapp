package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TppOpenAccountCardsMapper {
	
	List<Map> getOpenAccountCardsList(Map<String, Object> params);
 
	int getOpenAccountCardsListCount(Map<String, Object> params);
}

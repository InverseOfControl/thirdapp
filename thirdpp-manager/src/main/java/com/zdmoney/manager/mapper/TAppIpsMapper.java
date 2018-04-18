package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TAppIps;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TAppIpsMapper {
	
	List<Map> getappIpsList(Map<String, Object> params);

	
	int insert(TAppIps ips);
	
	int updateAppIps(TAppIps ips);
	
	TAppIps selectAppIpsByID(long id);
	
	void batchDeleteIps(List<Integer> listId);
	int getappIpsListCount(Map<String, Object> params);
}

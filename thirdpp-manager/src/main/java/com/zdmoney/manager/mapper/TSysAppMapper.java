package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TSysApp;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TSysAppMapper {
	
	List<Map> getSysAppList(Map<String, Object> params);

	int insert(TSysApp app);
	
	int updateSysApp(TSysApp app);
	
	TSysApp selectSysAppByID(long id);
	int getSysAppListCount(Map<String, Object> params);
	int batchDeleteInfo(List<Integer> list);
	
	List<Map> getSysAppMeanu();
	
	int getAppCodeCount(String code);
	
	 
	
	String getSysAppName(Map<String, Object> params);
	
	int updateSysAppFlag (TSysApp app);
	
	
}

package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TSysApp;

/**
 * 
 * @author wyj
 *
 */
public interface TSysAppService {
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

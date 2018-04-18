package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

 



import com.zdmoney.manager.models.TAreaInfo;
 

/**
 * 
 * @author wyj
 *
 */
public interface TAreaInfoService {
	
	List<Map> getAreaInfoList(Map<String, Object> params);
	int insert(TAreaInfo area);
	
	int updateArea(TAreaInfo area);
	
	TAreaInfo selectAreaByID(long id);
	
	void batchDeleteArea(List<Integer> listId);
	int getAreaListCount(Map<String, Object> params);
	 int getAreaCodeCount(String code);
	 int getAreaCodeObj(TAreaInfo area);
	 int updateSonAreaCode(TAreaInfo area);
	 TAreaInfo selectAreaByCode(String code);
	 Map<String,String> getAreaListMap();
}

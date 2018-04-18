package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import schemasMicrosoftComOfficeOffice.STInsetMode;

import com.zdmoney.manager.models.TAreaInfo;
public interface TAreaInfoMapper {
	List<Map> getAreaInfoList(Map<String, Object> params);
	int insert(TAreaInfo area);
	
	int updateArea(TAreaInfo area);
	
	TAreaInfo selectAreaByID(long id);
	
	void batchDeleteArea(List<Integer> listId);
	int getAreaListCount(Map<String, Object> params);
	int getAreaCodeObj(TAreaInfo area) ;
	 int getAreaCodeCount(String code);
	 int updateSonAreaCode(TAreaInfo area);
	 TAreaInfo selectAreaByCode(String code);
	 List<TAreaInfo> getAreaListMap();
}

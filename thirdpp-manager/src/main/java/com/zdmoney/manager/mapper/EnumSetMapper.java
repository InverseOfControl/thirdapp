package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

public interface EnumSetMapper {
	List select_sysAppList(Map<String, Object> params);
	
	List select_dictionaryByType(Map<String, Object> params);
	
	List select_areaInfoParent(String params);
	List select_merType() ;
	
	List select_infoCategory();
	
	List select_bizType();
	
	List select_allSysAppList();
	List select_infoCategoryByApp(String appCode);
}

package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

public interface EnumSetService {
	List select_sysAppList(Map<String, Object> params);
	
	public List select_dictionaryByType(String dicType);

	List select_areaInfoParent(String params);
	
	List select_merType();
	
	List select_infoCategory();
	List select_infoCategoryByApp(String appCode);
	List select_bizType();
	List select_allSysAppList();
}

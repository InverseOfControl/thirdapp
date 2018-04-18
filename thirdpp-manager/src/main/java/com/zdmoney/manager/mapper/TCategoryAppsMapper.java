package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TCategoryApps;
import com.zdmoney.manager.models.TCategoryBanks;
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TCategoryAppsMapper {
	
	List<Map> getInfoCatefoList(Map<String, Object> params);
	
	int insertInfoCategory(TCategoryApps info);
	
	int insertInfoCategoryApps(TCategoryApps ta); 
	
	void batchDeleteInfo(List<Integer> list);
	
	void batchDeleteInfoApps(String code);
	     
	List<TCategoryApps> infoAppsIDList(List<Integer> list);
	
	int getInfoCatefoListCount(Map<String, Object> params);
	List<Map> getMerchantTypeList();
	TCategoryApps selectCategoryByID(long id) ;
	
	List<TCategoryApps> selectCategoryCodeStr(String code);
	
	 void updateCategory(TCategoryApps info);
	 
	 List<TCategoryApps> selectAppNameStr(String code);	 
	 
	int  getCategoryCodeCount(String code);
	void batchDeleteCode(List<String> list);
	
	List<TCategoryBanks> selectCategoryBanksByCategoryCode(String categoryCode);

	void commonConfigure(TCategoryBanks tCategoryBanks);
}

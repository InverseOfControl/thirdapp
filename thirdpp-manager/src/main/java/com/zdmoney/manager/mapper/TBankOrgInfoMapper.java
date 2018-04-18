package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

 

import com.zdmoney.manager.models.TBankOrgInfo;
 

public interface TBankOrgInfoMapper {
	
	List<Map> getBankOrgInfoList(Map<String, Object> params);

	
	int insert(TBankOrgInfo bankInfo);
	
	int updateBankOrgInfo(TBankOrgInfo bankInfo);
	
	TBankOrgInfo selectBankOrgInfoByID(long id);
	
	int getBankOrgInfoListCount(Map<String, Object> params);
	
	int getBankCodeCount(String bankCode); 
	
	int batchDeleteInfo(List<Integer> list);
	
	String getBankName(String code);
	
	int	getBankLineNo(TBankOrgInfo orgInfo); 
	
	int getBankOrgCount(TBankOrgInfo orgInfo); 
	
	List<TBankOrgInfo>getBankOrgInfoMap();
	String	getBankOrgInID(String bankLineNo);
	
}

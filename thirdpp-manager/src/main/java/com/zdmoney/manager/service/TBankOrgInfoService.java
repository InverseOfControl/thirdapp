package com.zdmoney.manager.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankOrgInfo;

/**
 * 
 * @author wyj
 *
 */
public interface TBankOrgInfoService {
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
	int batchInsert(List<TBankOrgInfo> orgInfo); 
	Map<String, String> getBankOrgInfoMap() ;
	String	getBankOrgInID(String bankLineNo);
	 Map<String, String> getBankLineNoMap();
}

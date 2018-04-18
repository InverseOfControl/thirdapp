package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankInfo;

/**
 * 
 * @author wyj
 *
 */
public interface TBankInfoService {
	
	
	List<Map> getBankInfoList(Map<String, Object> params);
	
	
	int insert(TBankInfo bankInfo);
	
	int updateBankInfo(TBankInfo bankInfo);
	
	TBankInfo selectBankInfoByID(long id);
	int getBankInfoListCount(Map<String, Object> params);
	int batchDeleteInfo(List<Integer> list);
	List<TBankInfo> selectAllBnkInfo();
	int getBankCodeCount(String bankCode);
	Map<String, String>getBnkInfoMap();
}

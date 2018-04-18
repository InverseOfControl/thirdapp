package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TSysPermission;
import com.zdmoney.manager.models.TThirdFieldMapper;

public interface TBankInfoMapper {
	
	List<Map> getBankInfoList(Map<String, Object> params);

	int insert(TBankInfo third);
	
	int updateBankInfo(TBankInfo third);
	
	TBankInfo selectBankInfoByID(long id);
	
	int getBankInfoListCount(Map<String, Object> params);
	
	int batchDeleteInfo(List<Integer> list);
	 
	List<TBankInfo> selectAllBnkInfo();
	
	int getBankCodeCount(String bankCode);
	
	List<String>getBnkInfoMap();
}

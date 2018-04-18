package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TDictionary;

/**
 * 
 * @author wyj
 *
 */
public interface TDictionaryService {
	
	
	List<Map> getDictionaryList(Map<String, Object> params);
	
	
	int insert(TDictionary bankInfo);
	
	int updateDictionary(TDictionary bankInfo);
	int getDictionaryListCount(Map<String, Object> params);
	TDictionary selectDictionaryByID(long id);
	List<TDictionary> getDictionaryParentList();
	List<Map> getDicTypeList(String dicName);
	int batchDeleteInfo(List<Integer> list);
	int getdicCodeCount(TDictionary code);
	String	getDicName(Map<String, Object> parmas);
	String getParentDicName(String parmas);
	int getdicTypeCount(String type);
	
	List<TDictionary> select_DictionarySelective(TDictionary dictionary);
}

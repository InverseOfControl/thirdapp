package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TDictionary;

public interface TDictionaryMapper {
	
	List<Map> getDictionaryList(Map<String, Object> params);
	int insert(TDictionary third);
	int updateDictionary(TDictionary third);
	TDictionary selectDictionaryByID(long id);
	List<TDictionary> getDictionaryParentList();
	int getDictionaryListCount(Map<String, Object> params);
	List<Map> getDicTypeList(String dicType);
	int batchDeleteInfo(List<Integer> list);
	int getdicCodeCount(TDictionary code);
	String	getDicName(Map<String, Object> parmas);
	String getParentDicName(String parmas);
	int getdicTypeCount(String type);
	List<TDictionary> select_DictionarySelective(TDictionary dictionary);
}

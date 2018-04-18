package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TBankInfoMapper;
import com.zdmoney.manager.mapper.TDictionaryMapper;
import com.zdmoney.manager.mapper.TSysPermissionMapper;
import com.zdmoney.manager.models.TBankInfo;
import com.zdmoney.manager.models.TDictionary;
import com.zdmoney.manager.service.TBankInfoService;
import com.zdmoney.manager.service.TDictionaryService;

/**
 * 
 * @author wyj
 *
 */
@Service
public class TDictionaryServiceImpl implements TDictionaryService{
	
	@Autowired
	private TDictionaryMapper dictionaryMapper;

	@Override
	public List<Map> getDictionaryList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getDictionaryList(params);
	}

	@Override
	public int insert(TDictionary dic) {
		// TODO Auto-generated method stub
		return dictionaryMapper.insert(dic);
	}

	@Override
	public int updateDictionary(TDictionary dic) {
		// TODO Auto-generated method stub
		return dictionaryMapper.updateDictionary(dic);
	}

	@Override
	public TDictionary selectDictionaryByID(long id) {
		// TODO Auto-generated method stub
		return dictionaryMapper.selectDictionaryByID(id);
	}

	@Override
	public List<TDictionary> getDictionaryParentList() {
		// TODO Auto-generated method stub
		return dictionaryMapper.getDictionaryParentList();
	}

	@Override
	public int getDictionaryListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getDictionaryListCount(params);
	}

	@Override
	public List<Map> getDicTypeList(String dicType) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getDicTypeList(dicType);
	}

	@Override
	public int batchDeleteInfo(List<Integer> list) {
		// TODO Auto-generated method stub
		return dictionaryMapper.batchDeleteInfo(list);
	}

	@Override
	public int getdicCodeCount(TDictionary code) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getdicCodeCount(code);
	}

	@Override
	public String getDicName(Map<String, Object> parmas) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getDicName(parmas);
	}

	@Override
	public String getParentDicName(String parmas) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getParentDicName(parmas);
	}

	@Override
	public int getdicTypeCount(String type) {
		// TODO Auto-generated method stub
		return dictionaryMapper.getdicTypeCount(type);
	}

	@Override
	public List<TDictionary> select_DictionarySelective(TDictionary dictionary) {
		return dictionaryMapper.select_DictionarySelective(dictionary);
	}

	
}

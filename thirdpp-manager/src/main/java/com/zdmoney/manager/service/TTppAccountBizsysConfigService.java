package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountBizsysConfig;

public interface TTppAccountBizsysConfigService {
	
	int deleteByPrimaryKey(Long id);
	
	void batchDelete(List<Long> list);

    int insert(TppAccountBizsysConfig record);

    int insertSelective(TppAccountBizsysConfig record);

    TppAccountBizsysConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppAccountBizsysConfig record);

    int updateByPrimaryKey(TppAccountBizsysConfig record);
	
	public List select_tppAccountBizsysConfigList(Map<String, Object> params);
	
	public int select_tppAccountBizsysConfigList_count(Map<String, Object> params);
	
	public List select_tppAccountBizsysConfigForUpdateSelective(Map<String, Object> params);
	
	public List select_tppAccountBizsysConfigSelective(Map<String, Object> params);
}

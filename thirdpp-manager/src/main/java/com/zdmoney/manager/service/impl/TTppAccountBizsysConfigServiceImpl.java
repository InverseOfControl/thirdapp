package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountBizsysConfigMapper;
import com.zdmoney.manager.models.TppAccountBizsysConfig;
import com.zdmoney.manager.service.TTppAccountBizsysConfigService;

@Service
public class TTppAccountBizsysConfigServiceImpl implements
		TTppAccountBizsysConfigService {
	
	@Autowired
	private TppAccountBizsysConfigMapper tppAccountBizsysConfigMapper;

	@Override
	public List select_tppAccountBizsysConfigList(Map<String, Object> params) {
		return tppAccountBizsysConfigMapper.select_tppAccountBizsysConfigList(params);
	}

	@Override
	public int select_tppAccountBizsysConfigList_count(
			Map<String, Object> params) {
		return tppAccountBizsysConfigMapper.select_tppAccountBizsysConfigList_count(params);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return tppAccountBizsysConfigMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TppAccountBizsysConfig record) {
		return tppAccountBizsysConfigMapper.insert(record);
	}

	@Override
	public int insertSelective(TppAccountBizsysConfig record) {
		return tppAccountBizsysConfigMapper.insertSelective(record);
	}

	@Override
	public TppAccountBizsysConfig selectByPrimaryKey(Long id) {
		return tppAccountBizsysConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TppAccountBizsysConfig record) {
		return tppAccountBizsysConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TppAccountBizsysConfig record) {
		return tppAccountBizsysConfigMapper.updateByPrimaryKey(record);
	}

	@Override
	public void batchDelete(List<Long> list) {
		tppAccountBizsysConfigMapper.batchDelete(list);
	}

	@Override
	public List select_tppAccountBizsysConfigForUpdateSelective(
			Map<String, Object> params) {
		return tppAccountBizsysConfigMapper.select_tppAccountBizsysConfigForUpdateSelective(params);
	}

	@Override
	public List select_tppAccountBizsysConfigSelective(
			Map<String, Object> params) {
		return tppAccountBizsysConfigMapper.select_tppAccountBizsysConfigSelective(params);
	}
}

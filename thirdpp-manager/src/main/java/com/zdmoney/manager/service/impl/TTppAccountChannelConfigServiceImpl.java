package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountChannelConfigMapper;
import com.zdmoney.manager.models.TppAccountChannelConfig;
import com.zdmoney.manager.service.TTppAccountChannelConfigService;

@Service
public class TTppAccountChannelConfigServiceImpl implements
		TTppAccountChannelConfigService {
	@Autowired
	private TppAccountChannelConfigMapper tppAccountChannelConfigMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return tppAccountChannelConfigMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public void batchDelete(List<Long> list) {
		tppAccountChannelConfigMapper.batchDelete(list);
	}

	@Override
	public int insert(TppAccountChannelConfig record) {
		return tppAccountChannelConfigMapper.insert(record);
	}

	@Override
	public int insertSelective(TppAccountChannelConfig record) {
		return tppAccountChannelConfigMapper.insertSelective(record);
	}

	@Override
	public TppAccountChannelConfig selectByPrimaryKey(Long id) {
		return tppAccountChannelConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TppAccountChannelConfig record) {
		return tppAccountChannelConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TppAccountChannelConfig record) {
		return tppAccountChannelConfigMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public List select_tppAccountChannelConfigList(Map<String, Object> params) {
		return tppAccountChannelConfigMapper.select_tppAccountChannelConfigList(params);
	}

	@Override
	public int select_tppAccountChannelConfigList_count(
			Map<String, Object> params) {
		return tppAccountChannelConfigMapper.select_tppAccountChannelConfigList_count(params);
	}

	@Override
	public List select_tppAccountChannelConfigSelective(
			Map<String, Object> params) {
		return tppAccountChannelConfigMapper.select_tppAccountChannelConfigSelective(params);
	}

	@Override
	public List select_tppAccountChannelConfigForUpdateSelective(
			Map<String, Object> params) {
		return tppAccountChannelConfigMapper.select_tppAccountChannelConfigForUpdateSelective(params);
	}
}

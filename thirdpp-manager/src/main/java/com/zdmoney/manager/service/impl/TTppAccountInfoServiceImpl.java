package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountInfoMapper;
import com.zdmoney.manager.models.TppAccountInfo;
import com.zdmoney.manager.service.TTppAccountInfoService;

@Service
public class TTppAccountInfoServiceImpl implements TTppAccountInfoService {
	
	@Autowired
	private TppAccountInfoMapper tppAccountInfoMapper;
	
	@Override
	public List select_tppAccountInfoList(Map<String, Object> params) {
		return tppAccountInfoMapper.select_tppAccountInfoList(params);
	}

	@Override
	public int select_tppAccountInfoList_count(Map<String, Object> params) {
		return tppAccountInfoMapper.select_tppAccountInfoList_count(params);
	}

	@Override
	public TppAccountInfo selectByPrimaryKey(Long id) {
		return tppAccountInfoMapper.selectByPrimaryKey(id);
	}
}

package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountBizsysInfoMapper;
import com.zdmoney.manager.service.TTppAccountBizsysInfoService;

@Service
public class TTppAccountBizsysInfoServiceImpl implements
		TTppAccountBizsysInfoService {
	
	@Autowired
	TppAccountBizsysInfoMapper accountBizsysInfoMapper;
	
	@Override
	public List select_tppAccountBizsysInfoList(Map<String, Object> params) {
		return accountBizsysInfoMapper.select_tppAccountBizsysInfoList(params);
	}

	@Override
	public int select_tppAccountBizsysInfoList_count(Map<String, Object> params) {
		return accountBizsysInfoMapper.select_tppAccountBizsysInfoList_count(params);
	}

}

package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountBizsysRequestMapper;
import com.zdmoney.manager.service.TTppAccountBizsysRequestService;

@Service
public class TTppAccountBizsysRequestServiceImpl implements
		TTppAccountBizsysRequestService {

	@Autowired
	private TppAccountBizsysRequestMapper tppAccountBizsysRequestMapper;
	
	@Override
	public List select_tppAccountBizsysRequestList(Map<String, Object> params) {
		return tppAccountBizsysRequestMapper.select_tppAccountBizsysRequestList(params);
	}

	@Override
	public int select_tppAccountBizsysRequestList_count(
			Map<String, Object> params) {
		return tppAccountBizsysRequestMapper.select_tppAccountBizsysRequestList_count(params);
	}

}

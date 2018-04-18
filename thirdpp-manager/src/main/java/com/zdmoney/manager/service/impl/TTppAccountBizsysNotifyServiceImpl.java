package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountBizsysNotifyMapper;
import com.zdmoney.manager.models.TppAccountBizsysNotify;
import com.zdmoney.manager.service.TTppAccountBizsysNotifyService;

@Service
public class TTppAccountBizsysNotifyServiceImpl implements
		TTppAccountBizsysNotifyService {
	
	@Autowired
	private TppAccountBizsysNotifyMapper tppAccountBizsysNotifyMapper;

	@Override
	public TppAccountBizsysNotify selectByPrimaryKey(Long id) {
		return tppAccountBizsysNotifyMapper.selectByPrimaryKey(id);
	}

	@Override
	public List select_tppAccountBizsysNotifyList(Map<String, Object> params) {
		return tppAccountBizsysNotifyMapper.select_tppAccountBizsysNotifyList(params);
	}

	@Override
	public int select_tppAccountBizsysNotifyList_count(
			Map<String, Object> params) {
		return tppAccountBizsysNotifyMapper.select_tppAccountBizsysNotifyList_count(params);
	}

}

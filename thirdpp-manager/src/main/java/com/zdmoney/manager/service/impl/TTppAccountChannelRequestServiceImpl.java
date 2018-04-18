package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppAccountChannelRequestMapper;
import com.zdmoney.manager.models.TppAccountChannelRequest;
import com.zdmoney.manager.service.TTppAccountChannelRequestService;

@Service
public class TTppAccountChannelRequestServiceImpl implements
		TTppAccountChannelRequestService {
	@Autowired
	private TppAccountChannelRequestMapper tppAccountChannelRequestMapper;

	@Override
	public int insert(TppAccountChannelRequest record) {
		return tppAccountChannelRequestMapper.insert(record);
	}

	@Override
	public int insertSelective(TppAccountChannelRequest record) {
		return tppAccountChannelRequestMapper.insertSelective(record);
	}

	@Override
	public List select_tppAccountChannelRequestList(Map<String, Object> params) {
		return tppAccountChannelRequestMapper.select_tppAccountChannelRequestList(params);
	}

	@Override
	public int select_tppAccountChannelRequestList_count(
			Map<String, Object> params) {
		return tppAccountChannelRequestMapper.select_tppAccountChannelRequestList_count(params);
	}
	
}

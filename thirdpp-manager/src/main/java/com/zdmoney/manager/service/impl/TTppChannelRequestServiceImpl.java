package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppChannelTRequestMapper;
import com.zdmoney.manager.service.TTppChannelRequestService;

@Service
public class TTppChannelRequestServiceImpl implements TTppChannelRequestService {
	
	@Autowired
	private TppChannelTRequestMapper tppChannelTRequestMapper;
	
	@Override
	public List select_tppChannelTRequestList(Map<String, Object> params) {
		return tppChannelTRequestMapper.select_tppChannelTRequestList(params);
	}

	@Override
	public Integer select_tppChannelTRequestList_count(
			Map<String, Object> params) {
		return tppChannelTRequestMapper.select_tppChannelTRequestList_count(params);
	}

	@Override
	public List select_tppChannelTRequestByTransferFlow(
			Map<String, Object> params) {
		return tppChannelTRequestMapper.select_tppChannelTRequestByTransferFlow(params);
	}

}

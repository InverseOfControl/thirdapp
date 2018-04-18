package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppChannelTradeResultMapper;
import com.zdmoney.manager.service.TTppChannelTradeResultService;

@Service
public class TTppChannelTradeResultServiceImpl implements
		TTppChannelTradeResultService {
	
	@Autowired
	TppChannelTradeResultMapper tppChannelTradeResultMapper;
	
	@Override
	public List select_tppChannelTradeResultList(Map<String, Object> params) {
		return tppChannelTradeResultMapper.select_tppChannelTradeResultList(params);
	}

	@Override
	public Integer select_tppChannelTradeResultList_count(
			Map<String, Object> params) {
		return tppChannelTradeResultMapper.select_tppChannelTradeResultList_count(params);
	}

	@Override
	public List select_tppChannelTradeResultByTransferFolw(
			Map<String, Object> params) {
		return tppChannelTradeResultMapper.select_tppChannelTradeResultByTransferFolw(params);
	}
	
	@Override
	public List select_tppChannelTradeResultByReqId(Map<String, Object> params) {
		return tppChannelTradeResultMapper.select_tppChannelTradeResultByReqId(params);
	}
}

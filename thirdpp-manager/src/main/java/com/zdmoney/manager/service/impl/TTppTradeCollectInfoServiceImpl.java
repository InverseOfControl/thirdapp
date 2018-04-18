package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTCollectInfoMapper;
import com.zdmoney.manager.models.TppTradeTCollectInfo;
import com.zdmoney.manager.service.TTppTradeCollectInfoService;

@Service
public class TTppTradeCollectInfoServiceImpl implements
		TTppTradeCollectInfoService {

	@Autowired
	private TppTradeTCollectInfoMapper tppTradeTCollectInfoMapper;
	@Override
	public List<TppTradeTCollectInfo> getInfoList(Map<String, Object> params) {
		return tppTradeTCollectInfoMapper.getInfoList(params);
	}
	
	@Override
	public List select_tppTradeTCollectInfoList(Map<String, Object> params) {
		return tppTradeTCollectInfoMapper.select_tppTradeTCollectInfoList(params);
	}

	@Override
	public Integer select_tppTradeTCollectInfoList_count(
			Map<String, Object> params) {
		return tppTradeTCollectInfoMapper.select_tppTradeTCollectInfoList_count(params);
	}

	@Override
	public List select_summary(Map<String, Object> params) {
		return tppTradeTCollectInfoMapper.select_summary(params);
	}

	@Override
	public List<TppTradeTCollectInfo> getCollectInfoByTradeFlow(String tradeFlow) {
		return tppTradeTCollectInfoMapper.getCollectInfoByTradeFlow(tradeFlow);
	}
	
	

}

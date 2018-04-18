package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTPayInfoMapper;
import com.zdmoney.manager.models.TppTradeTPayInfo;
import com.zdmoney.manager.service.TTppTradePayInfoService;

@Service
public class TTppTradePayInfoServiceImpl implements TTppTradePayInfoService {
	
	@Autowired
	private TppTradeTPayInfoMapper tppTradeTPayInfoMapper;
	@Override
	public List<TppTradeTPayInfo> getInfoList(Map<String, Object> params) {
		return tppTradeTPayInfoMapper.getInfoList(params);
	}
	@Override
	public List select_tppTradeTPayInfoList(Map<String, Object> params) {
		return tppTradeTPayInfoMapper.select_tppTradeTPayInfoList(params);
	}
	@Override
	public Integer select_tppTradeTPayInfoList_count(Map<String, Object> params) {
		return tppTradeTPayInfoMapper.select_tppTradeTPayInfoList_count(params);
	}
	@Override
	public List select_summary(Map<String, Object> params) {
		return tppTradeTPayInfoMapper.select_summary(params);
	}
	@Override
	public List<TppTradeTPayInfo> getPayInfoByTradeFlow(String tradeFlow) {
		return tppTradeTPayInfoMapper.getPayInfoByTradeFlow(tradeFlow);
	}

}

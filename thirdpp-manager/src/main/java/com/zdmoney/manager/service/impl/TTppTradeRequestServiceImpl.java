package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTRequestMapper;
import com.zdmoney.manager.models.TppTradeTRequest;
import com.zdmoney.manager.service.TTppTradeRequestService;

@Service
public class TTppTradeRequestServiceImpl implements TTppTradeRequestService {
	
	@Autowired
	private TppTradeTRequestMapper tppTradeTRequestMapper;
	@Override
	public List<TppTradeTRequest> getRequestList(Map<String, Object> params) {
		return tppTradeTRequestMapper.getRequestList(params);
	}
	@Override
	public List select_tppTradeTRequestList(Map<String, Object> params) {
		return tppTradeTRequestMapper.select_tppTradeTRequestList(params);
	}
	@Override
	public Integer select_tppTradeTRequestList_count(Map<String, Object> params) {
		return tppTradeTRequestMapper.select_tppTradeTRequestList_count(params);
	}
	
	

}

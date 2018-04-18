package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTPayWhiteListMapper;
import com.zdmoney.manager.models.TppTradeTPayWhiteList;
import com.zdmoney.manager.service.TTppTradePayWhiteListService;

@Service
public class TTppTradePayWhiteListServiceImpl implements TTppTradePayWhiteListService {

	@Autowired
	private TppTradeTPayWhiteListMapper tppTradeTPayWhiteListMapper;

	@Override
	public List<TppTradeTPayWhiteList> getPayWhiteList(Map<String, Object> params) {
		return tppTradeTPayWhiteListMapper.getWhiteList(params);
	}

	@Override
	public int getPayWhiteListCount(Map<String, Object> params) {
		return tppTradeTPayWhiteListMapper.getPayWhiteListCount(params);
	}

	@Override
	public List detailWhiteList(Map<String, Object> params) {
		return tppTradeTPayWhiteListMapper.getWhiteList(params);
	}

	@Override
	public void update(TppTradeTPayWhiteList whiteList) {
		tppTradeTPayWhiteListMapper.update(whiteList);
	}

	@Override
	public void insert(TppTradeTPayWhiteList whiteList) {
		tppTradeTPayWhiteListMapper.insert(whiteList);
	}

	@Override
	public void delete(List<Integer> idList) {
		tppTradeTPayWhiteListMapper.delete(idList);
	}
	
	
}

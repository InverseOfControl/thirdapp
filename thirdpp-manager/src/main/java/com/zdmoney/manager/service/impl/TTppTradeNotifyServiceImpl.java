package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTNotifyMapper;
import com.zdmoney.manager.models.TppTradeTNotify;
import com.zdmoney.manager.service.TTppTradeNotifyService;

@Service
public class TTppTradeNotifyServiceImpl implements TTppTradeNotifyService {

	@Autowired
	private TppTradeTNotifyMapper tppTradeTNotifyMapper;
	
	@Override
	public List<TppTradeTNotify> getNotifyList(Map<String, Object> params) {
		return tppTradeTNotifyMapper.getNotifyList(params);
	}

	@Override
	public List select_tppTradeTNotifyList(Map<String, Object> params) {
		return tppTradeTNotifyMapper.select_tppTradeTNotifyList(params);
	}

	@Override
	public Integer select_tppTradeTNotifyList_count(Map<String, Object> params) {
		return tppTradeTNotifyMapper.select_tppTradeTNotifyList_count(params);
	}

	@Override
	public List select_tppTradeTNotifyById(String notifyId) {
		return tppTradeTNotifyMapper.select_tppTradeTNotifyById(notifyId);
	}

	@Override
	public int updateNotifyCount(String id) {
		// TODO Auto-generated method stub
		return tppTradeTNotifyMapper.updateNotifyCount(id);
	}

	@Override
	public int updateNotifyHisCount(String id) {
		// TODO Auto-generated method stub
		return tppTradeTNotifyMapper.updateNotifyHisCount(id);
	}

	@Override
	public int updateNotifyHisAllCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tppTradeTNotifyMapper.updateNotifyHisAllCount(params);
	}

	@Override
	public int updateNotifyAllCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tppTradeTNotifyMapper.updateNotifyAllCount(params);
	}

}

package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTWaitingQueryMapper;
import com.zdmoney.manager.models.TppTradeTWaitingQuery;
import com.zdmoney.manager.service.TTppTradeWaitingQueryService;

@Service
public class TTppTradeWaitingQueryServiceImpl implements
		TTppTradeWaitingQueryService {
	@Autowired
	private TppTradeTWaitingQueryMapper tppTradeTWaitingQueryMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return tppTradeTWaitingQueryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TppTradeTWaitingQuery record) {
		return tppTradeTWaitingQueryMapper.insert(record);
	}

	@Override
	public int insertSelective(TppTradeTWaitingQuery record) {
		return tppTradeTWaitingQueryMapper.insertSelective(record);
	}

	@Override
	public TppTradeTWaitingQuery selectByPrimaryKey(Long id) {
		return tppTradeTWaitingQueryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TppTradeTWaitingQuery record) {
		return tppTradeTWaitingQueryMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public int updateByPrimaryKeySelectiveHis(TppTradeTWaitingQuery record) {
		return tppTradeTWaitingQueryMapper.updateByPrimaryKeySelectiveHis(record);
	}

	@Override
	public int updateByPrimaryKey(TppTradeTWaitingQuery record) {
		return tppTradeTWaitingQueryMapper.updateByPrimaryKey(record);
	}

	@Override
	public List select_tppTradeTWaitingQueryList(Map<String, Object> params) {
		return tppTradeTWaitingQueryMapper.select_tppTradeTWaitingQueryList(params);
	}

	@Override
	public int select_tppTradeTWaitingQueryList_count(Map<String, Object> params) {
		return tppTradeTWaitingQueryMapper.select_tppTradeTWaitingQueryList_count(params);
	}
	
	@Override
	public int batchCheckedWaitingQuery(Map<String, Object> params) {
		return tppTradeTWaitingQueryMapper.batchCheckedWaitingQuery(params);
	}
	
	@Override
	public int batchCheckedWaitingQueryHis(Map<String, Object> params) {
		return tppTradeTWaitingQueryMapper.batchCheckedWaitingQueryHis(params);
	}
}

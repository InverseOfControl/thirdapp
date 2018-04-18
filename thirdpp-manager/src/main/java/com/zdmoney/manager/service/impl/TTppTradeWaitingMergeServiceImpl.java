package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppTradeTWaitingMergeMapper;
import com.zdmoney.manager.models.TppTradeTWaitingMerge;
import com.zdmoney.manager.service.TTppTradeWaitingMergeService;

@Service
public class TTppTradeWaitingMergeServiceImpl implements TTppTradeWaitingMergeService {
	@Autowired
	private TppTradeTWaitingMergeMapper tppTradeTWaitingMergeMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return tppTradeTWaitingMergeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TppTradeTWaitingMerge record) {
		return tppTradeTWaitingMergeMapper.insert(record);
	}

	@Override
	public int insertSelective(TppTradeTWaitingMerge record) {
		return tppTradeTWaitingMergeMapper.insertSelective(record);
	}

	@Override
	public TppTradeTWaitingMerge selectByPrimaryKey(Long id) {
		return tppTradeTWaitingMergeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(TppTradeTWaitingMerge record) {
		return tppTradeTWaitingMergeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TppTradeTWaitingMerge record) {
		return tppTradeTWaitingMergeMapper.updateByPrimaryKey(record);
	}

	@Override
	public List select_tppTradeTWaitingMergeList(Map<String, Object> params) {
		return tppTradeTWaitingMergeMapper.select_tppTradeTWaitingMergeList(params);
	}

	@Override
	public int select_tppTradeTWaitingMergeList_count(Map<String, Object> params) {
		return tppTradeTWaitingMergeMapper.select_tppTradeTWaitingMergeList_count(params);
	}
}

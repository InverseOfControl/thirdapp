package com.zdmoney.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdmoney.manager.mapper.TppChannelTMessageMapper;
import com.zdmoney.manager.service.TTppChannelMessageService;

@Service
public class TTppChannelMessageServiceImpl implements TTppChannelMessageService {
	
	@Autowired
	private TppChannelTMessageMapper tppChannelTMessageMapper;
	@Override
	public List select_tppChannelTMessageList(Map<String, Object> params) {
		return tppChannelTMessageMapper.select_tppChannelTMessageList(params);
	}

	@Override
	public Integer select_tppChannelTMessageList_count(
			Map<String, Object> params) {
		return tppChannelTMessageMapper.select_tppChannelTMessageList_count(params);
	}

	@Override
	public List select_tppChannelTMessageByReqId(Map<String, Object> params) {
		return tppChannelTMessageMapper.select_tppChannelTMessageByReqId(params);
	}

}

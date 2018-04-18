package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountBizsysNotify;

public interface TTppAccountBizsysNotifyService {
	
	public TppAccountBizsysNotify selectByPrimaryKey(Long id);
	
	public List select_tppAccountBizsysNotifyList(Map<String, Object> params);
	
	public int select_tppAccountBizsysNotifyList_count(Map<String, Object> params);
}

package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

public interface TTppAccountBizsysRequestService {
	
	public List select_tppAccountBizsysRequestList(Map<String, Object> params);
	
	public int select_tppAccountBizsysRequestList_count(Map<String, Object> params);
	
}

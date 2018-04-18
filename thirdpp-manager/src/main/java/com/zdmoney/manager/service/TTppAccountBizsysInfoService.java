package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

public interface TTppAccountBizsysInfoService {
	
	List select_tppAccountBizsysInfoList(Map<String, Object> params);
    
    int select_tppAccountBizsysInfoList_count(Map<String, Object> params);
}

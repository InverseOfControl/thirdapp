package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountInfo;

public interface TTppAccountInfoService {
	
	public TppAccountInfo selectByPrimaryKey(Long id);
	
	public List select_tppAccountInfoList(Map<String, Object> params);
    
    public int select_tppAccountInfoList_count(Map<String, Object> params);
}

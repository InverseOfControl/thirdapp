package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zdmoney.manager.models.TppTradeTCollectInfo;

public interface TppOperationRequestService {
	public List selectOperationRequestList(Map<String, Object> params);
    
    public Integer selectOperationRequestListCount(Map<String, Object> params);
    
    public List selectOperationRequestListById(Map<String, Object> params);
}

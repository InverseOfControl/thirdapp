package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

public interface TppOperationRequestMapper {
 
    
    public List selectOperationRequestList(Map<String, Object> params);
    
    public Integer selectOperationRequestListCount(Map<String, Object> params);
    
    public List selectOperationRequestListById(Map<String, Object> params);
    
     
}
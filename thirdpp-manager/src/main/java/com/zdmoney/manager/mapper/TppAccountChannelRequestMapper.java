package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountChannelRequest;

public interface TppAccountChannelRequestMapper {
    int insert(TppAccountChannelRequest record);

    int insertSelective(TppAccountChannelRequest record);
    
    public List select_tppAccountChannelRequestList(Map<String, Object> params);
    
    public int select_tppAccountChannelRequestList_count(Map<String, Object> params);
}
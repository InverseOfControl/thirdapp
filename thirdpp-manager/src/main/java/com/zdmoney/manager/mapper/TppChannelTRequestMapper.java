package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppChannelTRequest;

public interface TppChannelTRequestMapper {
    int insert(TppChannelTRequest record);

    int insertSelective(TppChannelTRequest record);
    
    List select_tppChannelTRequestList(Map<String, Object> params);
    
    Integer select_tppChannelTRequestList_count(Map<String, Object> params);
    
    List select_tppChannelTRequestByTransferFlow(Map<String, Object> params);
}
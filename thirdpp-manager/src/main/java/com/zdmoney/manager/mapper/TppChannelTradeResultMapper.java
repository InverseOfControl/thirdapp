package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppChannelTradeResult;

public interface TppChannelTradeResultMapper {
    int insert(TppChannelTradeResult record);

    int insertSelective(TppChannelTradeResult record);
    
    List select_tppChannelTradeResultList(Map<String, Object> params);
    
    Integer select_tppChannelTradeResultList_count(Map<String, Object> params);
    
    List select_tppChannelTradeResultByTransferFolw(Map<String, Object> params);
    
    List select_tppChannelTradeResultByReqId(Map<String, Object> params);
}
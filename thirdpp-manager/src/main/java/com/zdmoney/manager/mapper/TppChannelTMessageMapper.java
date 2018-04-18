package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppChannelTMessage;

public interface TppChannelTMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TppChannelTMessage record);

    int insertSelective(TppChannelTMessage record);

    TppChannelTMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppChannelTMessage record);

    int updateByPrimaryKey(TppChannelTMessage record);
    
    List select_tppChannelTMessageList(Map<String, Object> params);
    
    Integer select_tppChannelTMessageList_count(Map<String, Object> params);
    
    List select_tppChannelTMessageByReqId(Map<String, Object> params);
    
}
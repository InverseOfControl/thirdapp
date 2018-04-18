package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountInfo;

public interface TppAccountInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TppAccountInfo record);

    int insertSelective(TppAccountInfo record);

    TppAccountInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppAccountInfo record);

    int updateByPrimaryKey(TppAccountInfo record);
    
    public List select_tppAccountInfoList(Map<String, Object> params);
    
    public int select_tppAccountInfoList_count(Map<String, Object> params);
}
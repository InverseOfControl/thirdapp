package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountBizsysInfo;

public interface TppAccountBizsysInfoMapper {
    int deleteByPrimaryKey(Short taskId);

    int insert(TppAccountBizsysInfo record);

    int insertSelective(TppAccountBizsysInfo record);

    TppAccountBizsysInfo selectByPrimaryKey(Short taskId);

    int updateByPrimaryKeySelective(TppAccountBizsysInfo record);

    int updateByPrimaryKey(TppAccountBizsysInfo record);
    
    List select_tppAccountBizsysInfoList(Map<String, Object> params);
    
    int select_tppAccountBizsysInfoList_count(Map<String, Object> params);
}
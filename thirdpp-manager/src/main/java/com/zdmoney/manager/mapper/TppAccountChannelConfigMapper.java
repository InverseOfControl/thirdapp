package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppAccountChannelConfig;

public interface TppAccountChannelConfigMapper {
    int deleteByPrimaryKey(Long id);

    void batchDelete(List<Long> list);
    
    int insert(TppAccountChannelConfig record);

    int insertSelective(TppAccountChannelConfig record);

    TppAccountChannelConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TppAccountChannelConfig record);

    int updateByPrimaryKey(TppAccountChannelConfig record);
    
    public List select_tppAccountChannelConfigList(Map<String, Object> params);
    
	public int select_tppAccountChannelConfigList_count(Map<String, Object> params);
	
	public List select_tppAccountChannelConfigSelective(Map<String, Object> params);
	
	public List select_tppAccountChannelConfigForUpdateSelective(Map<String, Object> params);
}
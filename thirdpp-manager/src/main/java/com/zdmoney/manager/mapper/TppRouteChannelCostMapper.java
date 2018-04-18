package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRouteChannelCost;

public interface TppRouteChannelCostMapper {

    List<Map> getCostInfoList(Map<String, Object> params);
    TppRouteChannelCost getCostInfoByID(Long parseLong);
    int insert(TppRouteChannelCost info);
    int update(TppRouteChannelCost info);
    int updateAll(TppRouteChannelCost info);
    void delete(String[] ids);
    List<TppRouteChannelCost> getSection(TppRouteChannelCost info);
}

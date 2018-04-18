package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRouteChannelCost;

public interface TppRouteChannelCostService {

    List<Map> getCostInfoList(Map<String, Object> params);

    TppRouteChannelCost getCostInfoByID(Long parseLong);

    int insert(TppRouteChannelCost info);

    int update(TppRouteChannelCost info) throws Exception;
    
    int updateAll(TppRouteChannelCost info) throws Exception;

    void delete(String[] ids) throws Exception;
    
    String checkAmount(TppRouteChannelCost info) throws Exception;

    String checkSection(TppRouteChannelCost info) throws Exception;
    String checkMaxAmount(TppRouteChannelCost info) throws Exception;
    String checkData(TppRouteChannelCost info) throws Exception; 

}

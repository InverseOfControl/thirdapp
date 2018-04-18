package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRouteConf;

public interface TppRouteConfMapper {
    
    List<Map> getRouteConfInfoList(Map<String, Object> params);
    TppRouteConf getRouteConfByID(long id);
    
    int update(TppRouteConf info);
    
    int insert(TppRouteConf info);

}

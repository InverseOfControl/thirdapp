package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRouteConf;

public interface TppRouteConfService {

    List<Map> getRouteConfInfoList(Map<String, Object> params);
    TppRouteConf getRouteConfByID(long id);

    int update(TppRouteConf info) throws Exception;
    int insert(TppRouteConf info);
    
    

}

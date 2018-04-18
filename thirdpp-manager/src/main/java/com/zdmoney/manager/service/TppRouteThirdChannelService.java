package com.zdmoney.manager.service;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRouteThirdChannel;

public interface TppRouteThirdChannelService {
    
    List<Map> getRouteChannelInfoList(Map<String, Object> params);
    
    TppRouteThirdChannel selectChannelInfoByThirdTypeNo(String ThirdTypeNo) throws Exception;
    
    boolean hasChannel(String ThirdTypeNo)throws Exception;

    TppRouteThirdChannel selectChannelInfoByID(Long parseLong) throws Exception;
    int update(TppRouteThirdChannel info) throws Exception;

    int insert(TppRouteThirdChannel info);
    
    boolean existChannel(TppRouteThirdChannel info) throws Exception;

    TppRouteThirdChannel getChannelByThirdTypeNo(String thirdTypeNo);

}

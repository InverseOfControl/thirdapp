package com.zdmoney.manager.mapper;

import java.util.List;
import java.util.Map;

import com.zdmoney.manager.models.TppRouteThirdChannel;

public interface TppRouteThirdChannelMapper {
    
    List<TppRouteThirdChannel> selectChannelInfoByThirdTypeNo(String ThirdTypeNo);
    List<Map> getRouteChannelInfoList(Map<String, Object> params);
    TppRouteThirdChannel selectChannelInfoByID(Long parseLong);
    int update(TppRouteThirdChannel info);
    int insert(TppRouteThirdChannel info);
    TppRouteThirdChannel getChannelByThirdTypeNo(String thirdTypeNo);

}

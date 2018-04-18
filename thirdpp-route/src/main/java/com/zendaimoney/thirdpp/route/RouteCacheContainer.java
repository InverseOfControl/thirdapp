package com.zendaimoney.thirdpp.route;

import com.zendaimoney.thirdpp.route.entity.RouteChannelCostEntity;
import com.zendaimoney.thirdpp.route.entity.SysThirdChannelInfoEntity;
import com.zendaimoney.thirdpp.route.entity.ThirdFieldMapperEntity;
import com.zendaimoney.thirdpp.route.pub.vo.RouteThirdChannelReqVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YM20051 on 2017/6/1.
 */
public class RouteCacheContainer {

    // 路由链
    public static List<Filter> filters = new ArrayList<Filter>();

    // 通道编码 与 名称映射关系
    public static final Map<String, String> thirdChannels = new HashMap<>();

    // 通道列表
    public static final Map<String, RouteThirdChannelReqVO> routeThirdChannels = new HashMap<>();

    // 信息类别
    public static final Map<String, String> infoCategories = new HashMap<>();

    // 第三方通道对应的产品通道
    public static final Map<String, SysThirdChannelInfoEntity> thirdChannelInfos = new HashMap<>();

    // 第三方通道对应的银行信息(包含单笔限额)
    public static final Map<String, ThirdFieldMapperEntity> thirdFieldMappers = new HashMap<>();

    // 沉淀量
    public static final Map<String, BigDecimal> precipitations = new HashMap<>();

    // 通道费率
    public static final Map<String, List<RouteChannelCostEntity>> channelCosts = new HashMap<>();

}

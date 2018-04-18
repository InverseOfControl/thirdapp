package com.zendaimoney.thirdpp.route.mapper;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.zendaimoney.thirdpp.route.entity.RouteChannelCostEntity;

import java.util.List;

/**
 * Created by YM20051 on 2017/6/5.
 */
public interface RouteChannelCostMapper extends AutoMapper<RouteChannelCostEntity> {

    public List<RouteChannelCostEntity> findByAll();
}

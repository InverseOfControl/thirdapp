package com.zendaimoney.thirdpp.route.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zendaimoney.thirdpp.route.entity.RouteConfEntity;
import com.zendaimoney.thirdpp.route.entity.SysThirdChannelInfoEntity;
import com.zendaimoney.thirdpp.route.mapper.SysThirdChannelInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by user on 2017/2/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class RouteConfTest {

    @Autowired
    private SysThirdChannelInfoMapper routeConfMapper;

    @Test
    public void findRouteConf(){
        EntityWrapper<RouteConfEntity> ew = new EntityWrapper<>();
        ew.and("is_available={0}", "1");   // 可用
        ew.orderBy("priority", false);

        List<SysThirdChannelInfoEntity> channelEntities = routeConfMapper.findByAll(1);

        System.out.println(channelEntities.size());
    }
    
}

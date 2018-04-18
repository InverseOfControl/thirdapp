package com.zendaimoney.thirdpp.route.facade;

import java.math.BigDecimal;

import com.alibaba.fastjson.JSON;
import com.zendaimoney.thirdpp.route.pub.service.IRouteService;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by YM20051 on 2017/5/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-consumer.xml")
public class RouteFacade {
    @Autowired
    private IRouteService routeSerivce;

    @Test
    public void route(){
        long before = System.currentTimeMillis();
        for(int i=0;i<1;i++){
            TaskReqVO taskReqVO = new TaskReqVO();
            taskReqVO.setAmount(new BigDecimal(100000));
            taskReqVO.setPayerBankCardNo("3333333333");
            taskReqVO.setPayerBankCode("00080003");
            taskReqVO.setInfoCategoryCode("20011");
            Response response = routeSerivce.route(taskReqVO);
            System.out.println(JSON.toJSONString(response));
        }
        long after = System.currentTimeMillis();
        System.err.println("时间---------["+(after-before)+"]");
    }

}

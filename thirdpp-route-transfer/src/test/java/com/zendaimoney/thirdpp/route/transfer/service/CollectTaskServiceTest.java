package com.zendaimoney.thirdpp.route.transfer.service;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zendaimoney.thirdpp.route.pub.service.IRouteService;
import com.zendaimoney.thirdpp.route.pub.vo.Response;
import com.zendaimoney.thirdpp.route.pub.vo.TaskReqVO;
import com.zendaimoney.thirdpp.route.transfer.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.route.transfer.entity.CollectTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class CollectTaskServiceTest {

    @Autowired
    private CollectTaskService collectTaskService;
    @Autowired
    private IRouteService irouteSerivce;
    @Autowired
    private CollectTaskDao collectTaskDao;

    @Test
    public void test() throws SQLException {
        CollectTask task = new CollectTask();
        task.setId(73568);
        task.setPaySysNo("4");
        System.out.println(collectTaskDao.get(73568).getPaySysNo());
       /* int i= collectTaskService.update(task);
        System.out.println(i);*/
    }
    
    @Test
    public void route(){
        long before = System.currentTimeMillis();
        for(int i=0;i<1;i++){
            TaskReqVO taskReqVO = new TaskReqVO();
            taskReqVO.setAmount(new BigDecimal(100000));
            taskReqVO.setPayerBankCardNo("3333333333");
            taskReqVO.setPayerBankCode("00080003");
            taskReqVO.setInfoCategoryCode("20011");
            Response response = irouteSerivce.route(taskReqVO);
            System.out.println(JSON.toJSONString(response));
        }
        long after = System.currentTimeMillis();
        System.err.println("时间---------["+(after-before)+"]");
    }
    
    

}

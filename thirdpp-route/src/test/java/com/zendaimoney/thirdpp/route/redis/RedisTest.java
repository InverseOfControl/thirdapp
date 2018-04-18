package com.zendaimoney.thirdpp.route.redis;

import com.ymkj.base.cache.redis.client.ICacheClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by YM20051 on 2017/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-redis.xml")
public class RedisTest {
    @Autowired
    private ICacheClient cacheClient;

    @Test
    public void testString(){
        String key = "18_3333333333";
        String s = cacheClient.setCurrDay(key, "3");
        System.out.println(cacheClient.get(key));
    }
}

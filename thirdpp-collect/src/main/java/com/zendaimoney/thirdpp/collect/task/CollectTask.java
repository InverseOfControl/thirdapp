package com.zendaimoney.thirdpp.collect.task;

import com.zendaimoney.thirdpp.collect.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by YM20051 on 2017/7/4.
 */
@Component
public class CollectTask {

    @Autowired
    private DataService dataService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     *  5 分钟执行一次
     */
    @Scheduled(cron="${system.task.cron}")
    public void start(){

        long b = System.currentTimeMillis();
        logger.info("=========== 开始报盘处理 ===========");

        dataService.parse();
        logger.info("=========== 结束报盘处理,耗时({}s) ===========", (System.currentTimeMillis() - b)/1000);


    }
}

package com.zendaimoney.thirdpp.notify.consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.notify.service.IMergeService;

/**
 * 待处理合并订单查询任务
 * 
 * @author 00225642
 * 
 */
@Service
public class WaitingQueryTask {

	@Autowired
	private IMergeService mergeService;

	/**
	 * 启动线程
	 */
	@PostConstruct
	public void startNotifyTread() {
		// 开启线程执行通知任务
		new Thread(new QueryThread(mergeService)).start();
	}
}

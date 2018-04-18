package com.zendaimoney.thirdpp.notify.consumer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.notify.service.INotifyService;
import com.zendaimoney.thirdpp.notify.util.Constants;

/**
 * 通知线程
 * 
 * @author 00225642
 * 
 */
@Service
public class NotifyTasker {

	@Autowired
	private INotifyService notifyService;

	/**
	 * 启动线程
	 */
	@PostConstruct
	public void startNotifyTread() {
		// 开启线程执行通知任务
		new Thread(new NotifyThread(notifyService, Constants.OP_MODE_OFFLINE)).start();
	}

}

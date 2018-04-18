package com.zendaimoney.thirdpp.notify.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.notify.entity.TradeNotify;

/**
 * @author 00225642
 * 
 */
@Service
public interface INotifyService {

	/**
	 * 根据进程名查询符合条件的推送消息
	 * 
	 * @return
	 * @throws SQLException
	 */
	List<TradeNotify> queryByAppName(String opMode) throws SQLException;


	/**
	 * 通知业务系统
	 * 
	 * @return
	 * @throws Exception
	 */
	void notifyBusiSys(List<TradeNotify> notifys) throws Exception;
	
	

}

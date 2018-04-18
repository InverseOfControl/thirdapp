package com.zendaimoney.thirdpp.notify.action;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.notify.annotations.BizActionAnnotation;
import com.zendaimoney.thirdpp.notify.dao.CollectTaskDao;
import com.zendaimoney.thirdpp.notify.dao.SysAppDao;
import com.zendaimoney.thirdpp.notify.dao.TotalOrderDao;
import com.zendaimoney.thirdpp.notify.entity.CollectTask;
import com.zendaimoney.thirdpp.notify.entity.SysApp;
import com.zendaimoney.thirdpp.notify.entity.TotalOrder;

@BizActionAnnotation(bizType = BizType.BROKER_COLLECT)
@Component("com.zendaimoney.thirdpp.notify.action.CollectAction")
public class CollectAction extends Action {

	@Resource(name = "collectTaskDao")
	private CollectTaskDao collectTaskDao;

	@Resource(name = "totalOrderDao")
	private TotalOrderDao totalOrderDao;
	
	@Autowired
	private SysAppDao sysAppDao;
	

	@Override
	protected TotalOrder query(long taskId) throws SQLException {
		
		TotalOrder order = totalOrderDao.queryCollectOrderByReqTaskId(taskId);
		
		if (order != null) {
			
			// 查询业务系统配置表
			SysApp sysApp = sysAppDao.queryAppUrlByAppCode(order.getBizSysNo());
			// 设置通知URL
			if (sysApp != null) {
				
				order.setNotifyUrl(sysApp.getCollectNotifyUrl());
			}
			
			if(StringUtils.isNotEmpty(order.getMerId())){
				order.setMerId(order.getMerId().split(",")[0]);
			}
		}
		
		return order;
	}

	@Override
	protected int modify(TotalOrder totalOrder) throws DataAccessException {
		CollectTask task = new CollectTask();
		task.setId(totalOrder.getTaskId());
		task.setTradeStatus(totalOrder.getNotifyStatus());
		task.setTradeSuccessAmount(totalOrder.getNotifySuccessAmt());
		task.setTradeResultInfo(totalOrder.getNotifyDesc());
		return collectTaskDao.update(task);
	}

	@Override
	protected Long queryTaskIdByFlow(String tradeFlow) throws SQLException {
		return collectTaskDao.queryTaskIdByFlow(tradeFlow);
	}

	@Override
	protected String queryFailReason(Long taskId) throws SQLException {
		return collectTaskDao.queryFailReason(taskId);
	}

}

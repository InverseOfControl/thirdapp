package com.zendaimoney.thirdpp.trade.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.trade.dao.PayInfoDao;
import com.zendaimoney.thirdpp.trade.dao.PayTaskDao;
import com.zendaimoney.thirdpp.trade.dao.RequestDao;
import com.zendaimoney.thirdpp.trade.entity.PayInfo;
import com.zendaimoney.thirdpp.trade.entity.PayTask;
import com.zendaimoney.thirdpp.trade.entity.Request;

@Transactional
@Service
public class PayTaskService {
	@Resource(name = "payTaskDao")
	private PayTaskDao payTaskDao;

	@Resource(name = "requestDao")
	private RequestDao requestDao;

	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	public void insert(PayTask payTask) {
		payTaskDao.insert(payTask);
	}

	/**
	 * 同步代付功能
	 * 
	 * @param request
	 * @param task
	 * @param info
	 * @throws SQLException
	 */
	public void syncPay(Request request, PayTask task, PayInfo info)
			throws SQLException {
		// TODO Auto-generated method stub
		long taskId = payTaskDao.insertReturnKey(task);
		requestDao.insert(request);
		// 交易明细中设置taskid
		info.setTaskId(taskId);
		payInfoDao.insert(info);
	}

	public void asynPay(Request request, List<PayTask> tasks) throws SQLException{
		// TODO Auto-generated method stub
		requestDao.insert(request);
		payTaskDao.batchInsert(tasks);
	}
	
	
	/**
	 * 更新代付任务表
	 * @param payTask
	 */
	public void update(PayTask payTask) {
		payTaskDao.update(payTask);
	}

	/**
	 * 更新代付明细表
	 * @param payInfo
	 */
	public void updatePayInfo(PayInfo payInfo) {
		payInfoDao.update(payInfo);
	}
	
	/**
	 * 根据银行卡号获取当天代付记录
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public List<PayTask> queryPayTaskByReceiverBankCardNo(String param) throws SQLException {
		
		return payTaskDao.queryPayTaskByReceiverBankCardNo(param);
	}
	
	/**
	 * 业务流水号 获取状态，查询范围两周内
	 * @param param
	 * @return
	 * @throws SQLException 
	 */
	public List<PayTask> queryPayTaskBybizFlow(HashMap<String, Object> param) throws SQLException{
		
		return payTaskDao.queryPayTaskBybizFlow(param);
		
	}


	public List<PayInfo> queryPayInfo(HashMap<String, Object> param) throws SQLException{
		return payInfoDao.queryPayInfoByBizflowAndBizSysNo(param);
	}


}

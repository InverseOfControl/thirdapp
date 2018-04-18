package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.PayTask;

@Repository
public class PayTaskDao extends BaseDao<PayTask> {
	// PayTask实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYTASK = "com.zendaimoney.thirdpp.trade.entity.PayTask";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	public long insertReturnKey(PayTask task) throws SQLException {
		long key = (Long) this.sqlMapClient.insert(SQL_PREFIX_PAYTASK
				+ ".insertReturnKey", task);
		return key;
	}

	public void batchInsert(List<PayTask> tasks) throws SQLException {
		int count = 0;
		if (tasks != null && !tasks.isEmpty()) {
			sqlMapClient.startBatch();
			for (PayTask task : tasks) {
				count++;
				this.insert(task);
				if (count % 15000 == 0) {
					sqlMapClient.executeBatch();
				}
			}
			sqlMapClient.executeBatch();
		}

	}
	
	/**
	 * 根据银行卡号获取当天代付记录
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PayTask> queryPayTaskByReceiverBankCardNo(String param) throws SQLException {
		
		return  this.sqlMapClient.queryForList(SQL_PREFIX_PAYTASK
				+ ".queryPayTaskByReceiverBankCardNo",param);
	}
	
	
	/**
	 * 查询重复发送任务，主要基于bizFlow来判断
	 * @param param
	 * @return payTask
	 * @throws SQLException 
	 */
	public PayTask queryPayTaskByBizflowAndBizSysNo(HashMap<String, Object> param) throws SQLException{
		PayTask payTask = (PayTask) this.sqlMapClient
				.queryForObject(SQL_PREFIX_PAYTASK
						+ ".queryPayTaskByBizflowAndBizSysNo", param);

		return payTask;
	}

	/**
	 * 业务流水号 获取状态，查询范围两周内
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PayTask> queryPayTaskBybizFlow(HashMap<String, Object> param) throws SQLException {
		List<PayTask> payTasks =  this.sqlMapClient
				.queryForList(SQL_PREFIX_PAYTASK
						+ ".queryPayTaskBybizFlow", param);

		return payTasks;
	}


}

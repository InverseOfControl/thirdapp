package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.account.entity.PayTask;

@Repository
public class PayTaskDao extends BaseDao<PayTask> {
	// PayTask实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYTASK = "com.zendaimoney.thirdpp.account.entity.PayTask";

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
	 * 查询重复发送任务，主要基于bizFlow来判断
	 * @param param
	 * @return
	 * @throws SQLException 
	 */
	public PayTask queryPayTaskByTaskId(HashMap<String, Object> param) throws SQLException{
		PayTask payTask = (PayTask) this.sqlMapClient
				.queryForObject(SQL_PREFIX_PAYTASK + ".queryPayTaskByTaskId", param);
		if (payTask == null) {
			payTask = (PayTask) this.sqlMapClient
					.queryForObject(SQL_PREFIX_PAYTASK + ".queryPayTaskHisByTaskId", param);
		}
		return payTask;
	}
}

package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.CollectTask;

@Repository
public class CollectTaskDao extends BaseDao<CollectTask> {
	// CollectTask实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTTASK = "com.zendaimoney.thirdpp.trade.entity.CollectTask";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	public long insertReturnKey(CollectTask task) throws SQLException {
		long key = (Long) this.sqlMapClient.insert(SQL_PREFIX_COLLECTTASK
				+ ".insertReturnKey", task);
		return key;
	}

	public void batchInsert(List<CollectTask> tasks) throws SQLException {
		int count = 0;
		if (tasks != null && !tasks.isEmpty()) {
			sqlMapClient.startBatch();
			for (CollectTask task : tasks) {
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
	 * @return
	 * @throws SQLException 
	 */
	public CollectTask queryCollectTaskByBizflowAndBizSysNo(HashMap<String, Object> param) throws SQLException{
		CollectTask collectTask = (CollectTask) this.sqlMapClient
				.queryForObject(SQL_PREFIX_COLLECTTASK
						+ ".queryCollectTaskByBizflowAndBizSysNo", param);

		return collectTask;
	}

}

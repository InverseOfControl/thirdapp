package com.zendaimoney.thirdpp.transfer.dao;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask;

@Repository
public class CollectTaskDao extends BaseDao<CollectTask> {
	// CollectTask实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTTASK = "com.zendaimoney.thirdpp.transfer.entity.collect.CollectTask";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 获取待发送任务
	 * 
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public CollectTask queryWaitingSendTask(HashMap<String, Object> param)
			throws SQLException {
		CollectTask collectTask = (CollectTask) this.sqlMapClient
				.queryForObject(SQL_PREFIX_COLLECTTASK
						+ ".queryWaitingSendTask", param);

		return collectTask;
	}

	/**
	 * 查询重复发送任务，主要基于bizFlow来判断
	 * @param param
	 * @return
	 * @throws SQLException 
	 */
	public CollectTask queryRepeatSendTask(HashMap<String, Object> param) throws SQLException{
		CollectTask collectTask = (CollectTask) this.sqlMapClient
				.queryForObject(SQL_PREFIX_COLLECTTASK
						+ ".queryRepeatSendTask", param);

		return collectTask;
	}
}

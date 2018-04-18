package com.zendaimoney.thirdpp.notify.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.notify.entity.CollectTask;

@Repository
public class CollectTaskDao extends BaseDao<CollectTask> {

	// CollectTask实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTTASK = "com.zendaimoney.thirdpp.notify.entity.CollectTask";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据交易流水查询taskId
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long queryTaskIdByFlow(String tradeFlow) throws SQLException {
		Long taskId = (Long) this.sqlMapClient.queryForObject(
				SQL_PREFIX_COLLECTTASK + ".selectTaskIdByFlow", tradeFlow);
		return taskId;
	}

	public String queryFailReason(Long taskId) throws SQLException {
		String failReason = (String) this.sqlMapClient.queryForObject(
				SQL_PREFIX_COLLECTTASK + ".selectFailReason", taskId);
		return failReason;
	}
}

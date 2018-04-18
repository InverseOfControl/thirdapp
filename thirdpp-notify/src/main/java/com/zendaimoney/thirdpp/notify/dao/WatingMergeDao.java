package com.zendaimoney.thirdpp.notify.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.notify.entity.WaitingMergeEntity;

@Repository
public class WatingMergeDao extends BaseDao<WaitingMergeEntity> {

	// TradeNotify实体类数据库操作前缀.
	private static final String SQL_PREFIX_WAITINGMERGE = "com.zendaimoney.thirdpp.notify.entity.WaitingMergeEntity";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 查询待处理的记录
	 * @param map
	 * @return
	 */
	public List<WaitingMergeEntity> queryWaitingResults(Map<String, Object> map)
			throws SQLException {
		return sqlMapClient.queryForList(SQL_PREFIX_WAITINGMERGE
				+ ".selectWaiting", map);
	}
}

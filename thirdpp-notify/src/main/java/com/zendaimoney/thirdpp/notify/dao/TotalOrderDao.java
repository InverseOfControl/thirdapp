package com.zendaimoney.thirdpp.notify.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.notify.entity.TotalOrder;

@Repository
public class TotalOrderDao extends BaseDao<TotalOrderDao> {
	// TotalOrder实体类数据库操作前缀.
	private static final String SQL_PREFIX_TOTALORDER = "com.zendaimoney.thirdpp.notify.entity.TotalOrder";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据taskId获取请求记录(selectCollectInfo)
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public TotalOrder queryCollectOrderByReqTaskId(long taskId) throws SQLException {
		TotalOrder totalOrder = (TotalOrder) this.sqlMapClient.queryForObject(
				SQL_PREFIX_TOTALORDER + ".selectCollectInfo", taskId);
		return totalOrder;
	}
	
	/**
	 * 根据taskId获取请求记录(selectPayInfo)
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public TotalOrder queryPayOrderByReqTaskId(long taskId) throws SQLException {
		TotalOrder totalOrder = (TotalOrder) this.sqlMapClient.queryForObject(
				SQL_PREFIX_TOTALORDER + ".selectPayInfo", taskId);
		return totalOrder;
	}
}

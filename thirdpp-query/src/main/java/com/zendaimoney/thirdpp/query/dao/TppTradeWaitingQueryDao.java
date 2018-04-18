package com.zendaimoney.thirdpp.query.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.query.entity.TradeWaitingQuery;

@Repository
@Component("com.zendaimoney.thirdpp.query.dao.TppTradeWaitingQueryDao")
public class TppTradeWaitingQueryDao extends BaseDao<TradeWaitingQuery> {
	// TradeWaitingQuery实体类数据库操作前缀.
	private static final String SQL_PREFIX_WAITINGQUERYINFO = "com.zendaimoney.thirdpp.query.entity.TradeWaitingQuery";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public List<TradeWaitingQuery> queryTppTradeWaitingQueryDaos(String appName, String status, int limit) throws SQLException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		// 进程名称
		if (!StringUtils.isEmpty(appName)) {
			paraMap.put("queryModuleName", appName);
		}
		
		// 状态
		if (!StringUtils.isEmpty(status)) {
			paraMap.put("status", status);
		}
		
		if (limit > 0) {
			paraMap.put("limit", limit);
		}
		
		List<TradeWaitingQuery> tradeWaitingQueries = (List<TradeWaitingQuery>) this.sqlMapClient.queryForList(SQL_PREFIX_WAITINGQUERYINFO + ".queryTppTradeWaitingQueryDaos", paraMap);

		return tradeWaitingQueries;
	}
	
	/**
	 * 更新交易状态及结果code
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public int updateStatus(TradeWaitingQuery tradeWaitingQuery, String targetStatus) throws SQLException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		// 如果业务类型不为空，添加参数bizType
		if (!StringUtils.isEmpty(tradeWaitingQuery.getId())) {
			
			paraMap.put("id", tradeWaitingQuery.getId());
		}
		
		// 交易流水号
		if (!StringUtils.isEmpty(tradeWaitingQuery.getTradeFlow())) {
			paraMap.put("tradeFlow", tradeWaitingQuery.getTradeFlow());
		}
		
		// 进程号
		if (!StringUtils.isEmpty(tradeWaitingQuery.getQueryModuleName())) {
			paraMap.put("queryModuleName", tradeWaitingQuery.getQueryModuleName());
		}
		// 修改状态
		if (!StringUtils.isEmpty(targetStatus)) {
			
			paraMap.put("targetStatus", targetStatus);
		}
		
		int count = this.sqlMapClient.update(SQL_PREFIX_WAITINGQUERYINFO + ".updateStatus", paraMap);

		return count;
	}
	
	/**
	 * 批量插入数据库
	 * @param waitingQueries
	 * @throws SQLException
	 */
	public void batchInsert(List<TradeWaitingQuery> waitingQueries) throws SQLException {
		int count = 0;
		if (waitingQueries != null && !waitingQueries.isEmpty()) {
			sqlMapClient.startBatch();
			for (TradeWaitingQuery task : waitingQueries) {
				count++;
				this.insert(task);
				if (count % 5000 == 0) {
					sqlMapClient.executeBatch();
				}
			}
			sqlMapClient.executeBatch();
		}

	}
}

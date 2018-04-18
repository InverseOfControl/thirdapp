package com.zendaimoney.thirdpp.notify.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.notify.entity.TradeNotify;

@Repository
public class TradeNotifyDao extends BaseDao<TradeNotify> {
	// TradeNotify实体类数据库操作前缀.
	private static final String SQL_PREFIX_TRADENOTIFY = "com.zendaimoney.thirdpp.notify.entity.TradeNotify";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据appName获取一定数量的请求记录
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<TradeNotify> queryNotifysByAppName(Map<String, Object> map)
			throws SQLException {
		List<TradeNotify> notifys = (List<TradeNotify>) this.sqlMapClient
				.queryForList(SQL_PREFIX_TRADENOTIFY + ".selectByAppName", map);
		return notifys;
	}

}

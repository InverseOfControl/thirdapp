package com.zendaimoney.trust.channel.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.trust.channel.entity.AcctCommand;

/**
 * 资金操作Dao
 * @author mencius
 *
 */
@Repository
public class AcctCommandDao extends BaseDao<AcctCommand> {
	// TradeErrorLog实体类数据库操作前缀.
	private static final String SQL_PREFIX_REQUEST = "com.zendaimoney.trust.channel.entity.AcctCommand";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

}

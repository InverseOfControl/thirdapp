package com.zendaimoney.thirdpp.trade.dao;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.Request;

@Repository
public class RequestDao extends BaseDao<Request> {
// Request实体类数据库操作前缀.
	private static final String SQL_PREFIX_REQUEST = "com.zendaimoney.thirdpp.trade.entity.Request";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
	
	
}

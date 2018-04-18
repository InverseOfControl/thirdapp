package com.zendaimoney.thirdpp.channel.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class RequestDao extends BaseDao<Request> {
	// TradeErrorLog实体类数据库操作前缀.
	private static final String SQL_PREFIX_REQUEST = "com.zendaimoney.thirdpp.channel.entity.Request";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据reqid获取请求记录
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public Request queryRequestByReqId(String reqId) throws SQLException {
		Request request = (Request) this.sqlMapClient.queryForObject(
				SQL_PREFIX_REQUEST + ".queryRequestByReqId", reqId);

		return request;
	}
	 

}

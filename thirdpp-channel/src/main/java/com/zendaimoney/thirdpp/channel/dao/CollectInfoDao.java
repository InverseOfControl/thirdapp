package com.zendaimoney.thirdpp.channel.dao;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.CollectInfo;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class CollectInfoDao extends BaseDao<CollectInfo> {
	// CollectInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTINFO = "com.zendaimoney.thirdpp.channel.entity.CollectInfo";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public Map<String,Object> getAgrmno(Map<String,Object> map) throws SQLException {
		return (Map<String, Object>) sqlMapClient.queryForObject(SQL_PREFIX_COLLECTINFO + ".getAgrmno", map);
	}
	 
}

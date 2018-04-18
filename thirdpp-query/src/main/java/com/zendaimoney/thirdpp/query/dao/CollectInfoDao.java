package com.zendaimoney.thirdpp.query.dao;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.query.entity.CollectInfo;

@Repository
public class CollectInfoDao extends BaseDao<CollectInfo> {
	// CollectInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTINFO = "com.zendaimoney.thirdpp.query.entity.CollectInfo";

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
	public CollectInfo queryCollectInfoByReqId(Map<String, Object> paramMap) throws SQLException {
		CollectInfo collectInfo = (CollectInfo) this.sqlMapClient.queryForObject(
				SQL_PREFIX_COLLECTINFO + ".queryCollectInfoByReqId", paramMap);

		return collectInfo;
	}
	
	/**
	 * 更新交易状态及结果code
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public int updateByStatus(CollectInfo collectInfo) throws SQLException {
		int count = this.sqlMapClient.update(SQL_PREFIX_COLLECTINFO + ".updateByStatus", collectInfo);

		return count;
	}
	
	 

}

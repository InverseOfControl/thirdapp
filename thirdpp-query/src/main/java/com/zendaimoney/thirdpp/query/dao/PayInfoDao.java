package com.zendaimoney.thirdpp.query.dao;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.query.entity.PayInfo;

@Repository
public class PayInfoDao extends BaseDao<PayInfo> {
	// PayInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYINFO = "com.zendaimoney.thirdpp.query.entity.PayInfo";

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
	public PayInfo queryPayInfoByReqId(Map<String, Object> paramMap) throws SQLException {
		PayInfo payInfo = (PayInfo) this.sqlMapClient.queryForObject(
				SQL_PREFIX_PAYINFO + ".queryPayInfoByReqId", paramMap);

		return payInfo;
	}
	
	/**
	 * 更新交易状态及结果code
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public int updateByStatus(PayInfo payInfo) throws SQLException {
		int count = this.sqlMapClient.update(SQL_PREFIX_PAYINFO + ".updateByStatus", payInfo);

		return count;
	}
	 

}

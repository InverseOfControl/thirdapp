package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.TppWhiteList;

@Repository
public class TppWhiteListMapperDao extends BaseDao<TppWhiteList> {

	// ThirdFieldMapper实体类数据库操作前缀.
	private static final String SQL_PREFIX_THIRDFIELDMAPPER = "com.zendaimoney.thirdpp.trade.entity.TppWhiteList";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}


	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param infoCategoryCode 信息类别
	 * @param bankCardNo 银行卡号
	 * @param accountNo 账户号
	 * @param bizSysNo 业务系统号
	 * @return
	 * @throws SQLException
	 */
	public List<TppWhiteList> queryTppWhiteList(Map<String, Object> paramMap) throws SQLException {
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		return this.sqlMapClient.queryForList(SQL_PREFIX_THIRDFIELDMAPPER + ".queryTppWhiteList", paramMap);
	}
	
	
}

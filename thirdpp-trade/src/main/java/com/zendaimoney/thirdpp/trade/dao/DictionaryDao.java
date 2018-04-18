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
import com.zendaimoney.thirdpp.trade.entity.Dictionary;

@Repository
public class DictionaryDao extends BaseDao<Dictionary> {

	private static final String SQL_PREFIX_DICTIONARY = "com.zendaimoney.thirdpp.trade.entity.Dictionary";
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getSupportedPayPlatforms(String dicType) throws SQLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dicType", dicType);
		return sqlMapClient.queryForList(SQL_PREFIX_DICTIONARY + ".select", parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Dictionary> getDictionaryByDicCode(String dicCode) throws SQLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("dicCode", dicCode);
		return sqlMapClient.queryForList(SQL_PREFIX_DICTIONARY + ".selectByDicCode", parameters);
	}
	
}

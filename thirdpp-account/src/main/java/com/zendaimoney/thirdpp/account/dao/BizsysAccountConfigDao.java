package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class BizsysAccountConfigDao extends BaseDao<BizsysAccountConfig>{

	private static final String SQL_PREFFIX_BIZSYS_ACCOUNT_CONFIG = "com.zendaimoney.thirdpp.account.entity.BizsysAccountConfig";
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@PostConstruct
	@SuppressWarnings("deprecation")
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	public BizsysAccountConfig getBizsysAccountConfigByBizSysNo(String bizSysNo) throws SQLException{
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("bizSysNo", bizSysNo);
		return (BizsysAccountConfig)sqlMapClient.queryForObject(SQL_PREFFIX_BIZSYS_ACCOUNT_CONFIG + ".queryBizsysAccountConfig", parameterMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<BizsysAccountConfig> getAvaiableBizsysAccountConfig() throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appName", ServerConfig.systemConfig.getAppName());
		List<BizsysAccountConfig> list = sqlMapClient.queryForList(SQL_PREFFIX_BIZSYS_ACCOUNT_CONFIG + ".queryAvaiableBizsysAccountConfig", paramsMap);
		return list;
	}
	
	public BizsysAccountConfig getBizsysAccountConfigByConfigId(String configId) throws SQLException{
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("configId", configId);
		return (BizsysAccountConfig)sqlMapClient.queryForObject(SQL_PREFFIX_BIZSYS_ACCOUNT_CONFIG + ".queryBizsysAccountConfigByConfigId", parameterMap);
	}
	
}

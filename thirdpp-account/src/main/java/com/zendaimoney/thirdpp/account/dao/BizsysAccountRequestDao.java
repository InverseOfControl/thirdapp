package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.account.conf.ServerConfig;
import com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class BizsysAccountRequestDao extends BaseDao<BizsysAccountRequest>{
	
	private static final String SQL_PREFFIX_BIZSYS_ACCOUNT_REQUEST = "com.zendaimoney.thirdpp.account.entity.BizsysAccountRequest";
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	public BizsysAccountRequest getBizsysAccountRequestByConfigId(String configId) throws SQLException {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("configId", configId);
		String accountDay = CalendarUtils.getShortFormatNow();
		parameterMap.put("accountDay", accountDay);
		parameterMap.put("appName", ServerConfig.systemConfig.getAppName());
		return (BizsysAccountRequest)sqlMapClient.queryForObject(SQL_PREFFIX_BIZSYS_ACCOUNT_REQUEST + ".queryBizsysAccountRequestByConfig", parameterMap);
	}
	
	public BizsysAccountRequest getBizsysAccountRequestByConfigIdAndAccountDay(String configId, String accountDay) throws SQLException {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("configId", configId);
		parameterMap.put("accountDay", accountDay);
		return (BizsysAccountRequest)sqlMapClient.queryForObject(SQL_PREFFIX_BIZSYS_ACCOUNT_REQUEST + ".queryBizsysAccountRequestByConfigAndAccountDay", parameterMap);
	}
	
	public BizsysAccountRequest getBizsysAccountRequestByRequestId(String requestId) throws SQLException {
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("requestId", requestId);
		return (BizsysAccountRequest)sqlMapClient.queryForObject(SQL_PREFFIX_BIZSYS_ACCOUNT_REQUEST + ".queryBizsysAccountRequestByRequest", parameterMap);
	}
	
	public int updateHandleAccountStatus(BizsysAccountRequest bar) throws SQLException {
		return sqlMapClient.update(SQL_PREFFIX_BIZSYS_ACCOUNT_REQUEST + ".updateHandleAccountStatus", bar);
	}
}

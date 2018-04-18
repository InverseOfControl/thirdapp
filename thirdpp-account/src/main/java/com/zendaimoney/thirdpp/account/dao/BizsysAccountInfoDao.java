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
import com.zendaimoney.thirdpp.account.entity.BizsysAccountInfo;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class BizsysAccountInfoDao extends BaseDao<BizsysAccountInfo>{

	private static final String SQL_PREFFIX_BIZSYS_ACCOUNT_STATISTIC = "com.zendaimoney.thirdpp.account.entity.BizsysAccountInfo";
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	public List<BizsysAccountInfo> getBizsysAccountStatistic(String bizSysNo, String bizType, String accountDay) throws SQLException{
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("bizSysNo", bizSysNo);
		parameterMap.put("bizType", bizType);
		parameterMap.put("accountDay", accountDay);
		return (List<BizsysAccountInfo>)sqlMapClient.queryForList(SQL_PREFFIX_BIZSYS_ACCOUNT_STATISTIC + ".bizsysAccountStatistic", parameterMap);
	}
	
	public void batchInsert(List<BizsysAccountInfo> accountInfos) throws SQLException {
		int count = 0;
		if (accountInfos != null && !accountInfos.isEmpty()) {
			sqlMapClient.startBatch();
			for (BizsysAccountInfo accountInfo : accountInfos) {
				count++;
				this.insert(accountInfo);
				int batchDefinitionSize = ServerConfig.systemConfig.getBatchSize();
				if (batchDefinitionSize <= 0) {
					batchDefinitionSize = 500;
				}
				if (count % batchDefinitionSize == 0) {
					sqlMapClient.executeBatch();
				}
			}
			sqlMapClient.executeBatch();
		}
	}
	
}

package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.account.entity.AccountInfo;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class ChannelAccountInfoDao extends BaseDao<AccountInfo>{
	
	private static final String SQL_PREFIX_ACCOUNT_INFO = "com.zendaimoney.thirdpp.account.entity.AccountInfo";

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	public AccountInfo getLastByChannelRequest(String reqId) throws SQLException{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("reqId", reqId);
		return (AccountInfo)sqlMapClient.queryForObject(SQL_PREFIX_ACCOUNT_INFO + ".queryCurrentRowIndex", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getRelatedBizsysNoByReqId(String reqId) throws SQLException{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("reqId", reqId);
		return (List<String>)sqlMapClient.queryForList(SQL_PREFIX_ACCOUNT_INFO + ".queryRelatedBizsysNoByReqId", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List<AccountInfo> getAccountInfoByTaskId(String taskId) throws SQLException{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("taskId", taskId);
		return (List<AccountInfo>)sqlMapClient.queryForList(SQL_PREFIX_ACCOUNT_INFO + ".queryAccountInfoByTaskId", paramMap);
	}
	
	public void mergeInfo(AccountInfo accountInfo) throws SQLException {
		sqlMapClient.update(SQL_PREFIX_ACCOUNT_INFO + ".mergeIntoAccountInfo", accountInfo);
	}
	
	public void batchInsert(List<AccountInfo> accountInfos) throws SQLException {
		if (accountInfos != null && !accountInfos.isEmpty()) {
			sqlMapClient.startBatch();
			for (AccountInfo accountInfo : accountInfos) {
				mergeInfo(accountInfo);
			}
			sqlMapClient.executeBatch();
		}
	}
	
	public void batchUpdate(List<AccountInfo> accountInfos) throws SQLException {
		if (accountInfos != null && !accountInfos.isEmpty()) {
			sqlMapClient.startBatch();
			for (AccountInfo accountInfo : accountInfos) {
				if (accountInfo !=null) {
					this.update(accountInfo);
				}
			}
			sqlMapClient.executeBatch();
		}
	}
	
	public AccountInfo getLastAccountInfoByBizsysRequest(String bizsysRequestId) throws SQLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("bizsysReqId", bizsysRequestId);
		Object obj = sqlMapClient.queryForObject(SQL_PREFIX_ACCOUNT_INFO + ".queryLastSuccessByBizsysReqID", paramMap);
		if (obj != null) {
			return (AccountInfo)obj;
		}
		return null;
	}
	
	public AccountInfo getLastSuccessByReqId(String reqId) throws SQLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("reqId", reqId);
		Object obj = sqlMapClient.queryForObject(SQL_PREFIX_ACCOUNT_INFO + ".queryLastSuccess", paramMap);
		if (obj != null) {
			return (AccountInfo)obj;
		}
		return null;
	}
	
	public List<AccountInfo> getNotAccountByReqIdAndBizType(String reqId, String bizType) throws SQLException{
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("reqId", reqId);
		paramMap.put("bizType", bizType);
		@SuppressWarnings("unchecked")
		List<AccountInfo> list = sqlMapClient.queryForList(SQL_PREFIX_ACCOUNT_INFO + ".queryNotAccount", paramMap);
		return list;
	}
}

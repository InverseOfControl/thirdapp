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
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class ChannelAccountConfigDao extends BaseDao<ChannelAccountConfig>{
	
	public static final String SQL_PREFIX_TPP_CHANNEL_ACCOUNT_CONFIG = "com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig";
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelAccountConfig> getAvailableChannelAccountConfig() throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appName", ServerConfig.systemConfig.getAppName());
		List<ChannelAccountConfig> configList = sqlMapClient.queryForList(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_CONFIG + ".queryAvailableChannelAccountConfig", paramsMap);
		return configList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ChannelAccountConfig> getChannelAccountConfigByBizType(String bizType) throws SQLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("bizType", bizType);
		List<ChannelAccountConfig> configList = sqlMapClient.queryForList(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_CONFIG + ".queryChannelAccountConfigByBizType", paramMap);
		return configList;
	}
	
	public ChannelAccountConfig getChannelAccountConfigByConfigId(long configId) throws SQLException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("configId", String.valueOf(configId));
		Object obj = sqlMapClient.queryForObject(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_CONFIG + ".queryChannelAccountConfigByConfigId", paramMap);
		if (obj != null) {
			return (ChannelAccountConfig)obj;
		}
		return null;
	}
}

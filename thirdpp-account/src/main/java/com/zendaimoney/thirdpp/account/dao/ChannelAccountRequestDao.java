package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountConfig;
import com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest;
import com.zendaimoney.thirdpp.account.util.CalendarUtils;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class ChannelAccountRequestDao extends BaseDao<ChannelAccountRequest>{
	
	private static final String SQL_PREFIX_TPP_CHANNEL_ACCOUNT_REQUEST = "com.zendaimoney.thirdpp.account.entity.ChannelAccountRequest";
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient(){
		super.setSqlMapClient(sqlMapClient);
	}
	
	public ChannelAccountRequest getChannelAccountRequestByConfig(ChannelAccountConfig config) throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		long configId = config.getId();
		String thirdTypeNo = config.getThirdTypeNo();
		String merchantNo = config.getMerchantNo();
		String accountDay = CalendarUtils.getShortFormatNow();
		
		paramsMap.put("thirdTypeNo", thirdTypeNo);
		paramsMap.put("merchantNo", merchantNo);
		paramsMap.put("configId", String.valueOf(configId));
		paramsMap.put("accountDay", accountDay);
		Object obj = sqlMapClient.queryForObject(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_REQUEST + ".queryChannelAccountRequestByConfig", paramsMap);
		if (obj != null) {
			return (ChannelAccountRequest)obj;
		}
		return null;
	}
	
	public ChannelAccountRequest getChannelAccountRequestByConfig(ChannelAccountConfig config, String accountDay) throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		long configId = config.getId();
		String thirdTypeNo = config.getThirdTypeNo();
		String merchantNo = config.getMerchantNo();
		
		paramsMap.put("thirdTypeNo", thirdTypeNo);
		paramsMap.put("merchantNo", merchantNo);
		paramsMap.put("configId", String.valueOf(configId));
		paramsMap.put("accountDay", accountDay);
		Object obj = sqlMapClient.queryForObject(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_REQUEST + ".queryChannelAccountRequestByConfig", paramsMap);
		if (obj != null) {
			return (ChannelAccountRequest)obj;
		}
		return null;
	}
	
	public ChannelAccountRequest getChannelAccountRequestByRequestId(String requestId) throws SQLException {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("requestId", requestId);
		Object obj = sqlMapClient.queryForObject(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_REQUEST + ".queryChannelAccountRequestByRequestId", paramsMap);
		if (obj != null) {
			return (ChannelAccountRequest)obj;
		}
		return null;
	}
	
	public int updateHandleAccountStatus(ChannelAccountRequest car) throws SQLException {
		return sqlMapClient.update(SQL_PREFIX_TPP_CHANNEL_ACCOUNT_REQUEST + ".updateHandleAccountStatus", car);
	}
	
}

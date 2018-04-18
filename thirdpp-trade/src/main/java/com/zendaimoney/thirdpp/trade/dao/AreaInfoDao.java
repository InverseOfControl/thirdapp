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
import com.zendaimoney.thirdpp.trade.entity.AreaInfo;

@Repository
public class AreaInfoDao extends BaseDao<AreaInfo> {

	private static final String SQL_PREFIX_AREAINFO = "com.zendaimoney.thirdpp.trade.entity.AreaInfo";
	
	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
	
	@SuppressWarnings("unchecked")
	public List<AreaInfo> getAreaInfos(String parentId) throws SQLException {
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("parentId", parentId);
		return (List<AreaInfo>)this.sqlMapClient.queryForList(SQL_PREFIX_AREAINFO + ".queryAreaInfos", parameters);
	}
	
	public AreaInfo getAreaInfoByAreaCodeAndParentCode(String areaCode, String parentAreaCode) throws SQLException {
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("areaCode", areaCode);
		parameters.put("parentId", parentAreaCode);
		return (AreaInfo)this.sqlMapClient.queryForObject(SQL_PREFIX_AREAINFO + ".queryAreaInfoByAreaCodeAndParentCode", parameters);
	}
	
	public AreaInfo getAreaInfoByAreaCode(String areaCode) throws SQLException {
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("areaCode", areaCode);
		return (AreaInfo)this.sqlMapClient.queryForObject(SQL_PREFIX_AREAINFO + ".queryAreaInfoByAreaCode", parameters);
	}
}

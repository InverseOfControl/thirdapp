package com.zendaimoney.thirdpp.query.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.query.entity.ThreadPoolInfo;

@Repository
public class ThreadPoolInfoDao extends BaseDao<ThreadPoolInfo> {
	// ThreadPoolInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYINFO = "com.zendaimoney.thirdpp.query.entity.ThreadPoolInfo";

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
	@SuppressWarnings("unchecked")
	public List<ThreadPoolInfo> queryThreadPoolInfos(String bizType, String infType, String paySysNo) throws SQLException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bizType", bizType);
		paraMap.put("infType", infType);
		paraMap.put("paySysNo", paySysNo);
		
		List<ThreadPoolInfo> threadPoolInfos = (List<ThreadPoolInfo>) this.sqlMapClient.queryForList(
				SQL_PREFIX_PAYINFO + ".queryThreadPoolInfos", paraMap);

		return threadPoolInfos;
	}
	 

}

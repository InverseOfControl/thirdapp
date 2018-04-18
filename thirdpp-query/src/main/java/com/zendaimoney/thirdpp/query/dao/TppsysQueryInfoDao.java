package com.zendaimoney.thirdpp.query.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.query.entity.ThreadPoolInfo;
import com.zendaimoney.thirdpp.query.entity.TppsysQueryInfo;

@Repository
public class TppsysQueryInfoDao extends BaseDao<TppsysQueryInfo> {
	// TppsysQueryInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYINFO = "com.zendaimoney.thirdpp.query.entity.TppsysQueryInfo";

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
	public List<TppsysQueryInfo> queryTppsysQueryInfos(String bizType, String paySysNo, String appName) throws SQLException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		// 如果业务类型不为空，添加参数bizType
		if (!StringUtils.isEmpty(bizType)) {
			
			paraMap.put("bizType", bizType);
		}
		
		// 如果通道编码不为空，添加参数paySysNo
		if (!StringUtils.isEmpty(paySysNo)) {
			
			paraMap.put("paySysNo", paySysNo);
		}
		
		if (!StringUtils.isEmpty(appName)) {
			paraMap.put("appName", appName);
		}
		
		List<TppsysQueryInfo> threadPoolInfos = (List<TppsysQueryInfo>) this.sqlMapClient.queryForList(
				SQL_PREFIX_PAYINFO + ".queryTppsysQueryInfos", paraMap);

		return threadPoolInfos;
	}
	 

}

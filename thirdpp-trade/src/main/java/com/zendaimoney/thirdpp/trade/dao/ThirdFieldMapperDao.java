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
import com.zendaimoney.thirdpp.trade.entity.ThirdFieldMapper;

@Repository
public class ThirdFieldMapperDao extends BaseDao<ThirdFieldMapper> {

	// ThirdFieldMapper实体类数据库操作前缀.
	private static final String SQL_PREFIX_THIRDFIELDMAPPER = "com.zendaimoney.thirdpp.trade.entity.ThirdFieldMapper";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据字段映射类型获取相应记录集
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ThirdFieldMapper> queryThirdFieldMappersByFieldType(String fieldType) throws SQLException {
		List<ThirdFieldMapper> list = this.sqlMapClient
				.queryForList(SQL_PREFIX_THIRDFIELDMAPPER
						+ ".queryThirdFieldMappersByFieldType",fieldType);

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<ThirdFieldMapper> queryThirdPartyPayPlatformSupportBanks(String thirdPartyPayPlatformCode) throws SQLException {
		return this.sqlMapClient.queryForList(SQL_PREFIX_THIRDFIELDMAPPER + ".queryThirdPartyPayPlatformSupportBanks", thirdPartyPayPlatformCode);
	}
	
	public ThirdFieldMapper queryThirdPartyPayPlatformSupportBankInfo(String thirdPartyPayPlatformCode, String thirdPartyPayPlatformSupportBankCode) throws SQLException {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("thirdPartyType", thirdPartyPayPlatformCode);
		parameters.put("tppFieldCode", thirdPartyPayPlatformSupportBankCode);
		return (ThirdFieldMapper)this.sqlMapClient.queryForObject(SQL_PREFIX_THIRDFIELDMAPPER + ".queryThirdPartyPayPlatformSupportBankInfo", parameters);
	}
	
}

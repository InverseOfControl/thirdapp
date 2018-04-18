package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategoryBanks;

@Repository
public class SysInfoCategoryBanksDao extends BaseDao<SysInfoCategoryBanks> {

	// SysInfoCategoryBanks实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSINFOCATEGORYBANKS = "com.zendaimoney.thirdpp.trade.entity.SysInfoCategoryBanks";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 查询信息类别银行通道关系列表
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<SysInfoCategoryBanks> querySysInfoCategoryBanks(Map<String, Object> paramMap) throws SQLException {
		return (List<SysInfoCategoryBanks>) this.sqlMapClient.queryForList(
				SQL_PREFIX_SYSINFOCATEGORYBANKS + ".querySysInfoCategoryBanks", paramMap);
	}

}

package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategoryAPPS;

@Repository
public class SysInfoCategoryAPPSDao extends BaseDao<SysInfoCategoryAPPS> {

	// SysInfoCategoryAPPS实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSINFOCATEGORYAPPS = "com.zendaimoney.thirdpp.trade.entity.SysInfoCategoryAPPS";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 查询信息类别业务系统列表
	 * @param paramMap
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<SysInfoCategoryAPPS> querySysInfoCategoryAPPS(Map<String, Object> paramMap) throws SQLException {
		return (List<SysInfoCategoryAPPS>) this.sqlMapClient.queryForList(
				SQL_PREFIX_SYSINFOCATEGORYAPPS + ".querySysInfoCategoryAPPS", paramMap);
	}

}

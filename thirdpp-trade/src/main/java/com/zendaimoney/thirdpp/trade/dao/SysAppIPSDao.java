package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.SysAppIPS;

@Repository
public class SysAppIPSDao extends BaseDao<SysAppIPS> {

	// SysAppIPS实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSAPPIPS = "com.zendaimoney.thirdpp.trade.entity.SysAppIPS";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 获取应用系统IP列表。
	 * @param appCode
	 * @param ip
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<SysAppIPS> querySysAppIPS(Map<String, Object> paramMap) throws SQLException {
		return  (List<SysAppIPS>)this.sqlMapClient.queryForList(
				SQL_PREFIX_SYSAPPIPS + ".querySysAppIPS",
				paramMap);
	}


}

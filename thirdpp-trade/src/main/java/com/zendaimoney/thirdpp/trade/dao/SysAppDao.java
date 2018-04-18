package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.SysApp;

@Repository
public class SysAppDao extends BaseDao<SysApp> {

	// SysApp实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSAPP = "com.zendaimoney.thirdpp.trade.entity.SysApp";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据appCode获取应用系统信息。
	 * @param appCode
	 * @param ip
	 * @return
	 * @throws SQLException
	 */
	public SysApp querySysApp(String appCode) throws SQLException {
		SysApp sysApp = (SysApp) this.sqlMapClient.queryForObject(
				SQL_PREFIX_SYSAPP + ".querySysApp", appCode);
		return sysApp;
	}


}

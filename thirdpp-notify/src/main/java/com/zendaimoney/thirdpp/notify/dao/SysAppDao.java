package com.zendaimoney.thirdpp.notify.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.notify.entity.SysApp;

@Repository
public class SysAppDao extends BaseDao<SysApp> {
	// TradeNotify实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSAPP = "com.zendaimoney.thirdpp.notify.entity.SysApp";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 * 根据appName获取一定数量的请求记录
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String queryUrlByAppCode(String appCode) throws SQLException {
		String notifyUrl = (String) this.sqlMapClient.queryForObject(
				SQL_PREFIX_SYSAPP + ".selectUrlByAppCode", appCode);
		return notifyUrl;
	}
	
	/**
	 * 根据appName获取一定数量的请求记录
	 * 
	 * @return
	 * @throws SQLException
	 */
	public SysApp queryAppUrlByAppCode(String appCode) throws SQLException {
		SysApp sysApp = (SysApp) this.sqlMapClient.queryForObject(
				SQL_PREFIX_SYSAPP + ".queryAppUrlByAppCode", appCode);
		return sysApp;
	}
}

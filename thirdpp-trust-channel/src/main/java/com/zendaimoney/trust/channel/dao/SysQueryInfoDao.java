package com.zendaimoney.trust.channel.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.trust.channel.entity.SysQueryInfo;

@Repository
public class SysQueryInfoDao extends BaseDao<SysQueryInfo> {
	
	// SysQueryInfo实体类数据库操作前缀.
		private static final String SQL_PREFIX_SYSQUERYINFO = "com.zendaimoney.trust.channel.entity.SysQueryInfo";

		// 第二个数据源注入
		@Resource(name = "sqlMapClient")
		public SqlMapClient sqlMapClient;

		@SuppressWarnings("deprecation")
		@PostConstruct
		public void initSqlMapClient() {
			super.setSqlMapClient(sqlMapClient);
		}

		/**
		 *获取所有查询模块信息
		 * 
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public List<SysQueryInfo> querySysQueryInfos() throws SQLException {
			List<SysQueryInfo> list = this.sqlMapClient
					.queryForList(SQL_PREFIX_SYSQUERYINFO
							+ ".querySysQueryInfos");

			return list;
		}

}

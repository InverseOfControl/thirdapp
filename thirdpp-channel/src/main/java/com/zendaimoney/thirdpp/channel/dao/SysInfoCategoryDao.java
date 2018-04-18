package com.zendaimoney.thirdpp.channel.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.channel.entity.SysInfoCategory;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class SysInfoCategoryDao extends BaseDao<SysInfoCategory> {

	// SysInfoCategory实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSINFOCATEGORY = "com.zendaimoney.thirdpp.channel.entity.SysInfoCategory";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	/**
	 *获取所有信息类别记录集
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysInfoCategory> querySysInfoCategorys() throws SQLException {
		List<SysInfoCategory> list = this.sqlMapClient
				.queryForList(SQL_PREFIX_SYSINFOCATEGORY
						+ ".querySysInfoCategorys");

		return list;
	}

}

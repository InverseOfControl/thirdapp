package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.SysInfoCategory;

@Repository
public class SysInfoCategoryDao extends BaseDao<SysInfoCategory> {
	// SysInfoCategory实体类数据库操作前缀.
	private static final String SQL_PREFIX_SYSINFOCATEGORY = "com.zendaimoney.thirdpp.trade.entity.SysInfoCategory";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 根据信息类别编码查询信息类别
	 * @param infoCategoryCode
	 * @return
	 * @throws SQLException 
	 */
	public SysInfoCategory querySysInfoCategoryByCode(String infoCategoryCode) throws SQLException{
		return  (SysInfoCategory)this.sqlMapClient.queryForObject(
				SQL_PREFIX_SYSINFOCATEGORY + ".querySysInfoCategoryByCode",
				infoCategoryCode);
	}

}

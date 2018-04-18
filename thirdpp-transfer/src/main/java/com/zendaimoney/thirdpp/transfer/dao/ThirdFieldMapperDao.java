package com.zendaimoney.thirdpp.transfer.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.transfer.entity.ThirdFieldMapper;

@Repository
public class ThirdFieldMapperDao extends BaseDao<ThirdFieldMapper> {

	// ThirdFieldMapper实体类数据库操作前缀.
	private static final String SQL_PREFIX_THIRDFIELDMAPPER = "com.zendaimoney.thirdpp.transfer.entity.ThirdFieldMapper";

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

}

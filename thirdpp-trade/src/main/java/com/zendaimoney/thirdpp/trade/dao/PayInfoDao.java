package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.PayInfo;
import com.zendaimoney.thirdpp.trade.entity.PayTask;

@Repository
public class PayInfoDao extends BaseDao<PayInfo> {

	// PayInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYINFO = "com.zendaimoney.thirdpp.trade.entity.PayInfo";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	public void batchInsert(List<PayTask> tasks) {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	public List<PayInfo> queryPayInfoByBizflowAndBizSysNo(HashMap<String, Object> param) throws SQLException {
		List<PayInfo> payInfos =  this.sqlMapClient
				.queryForList(SQL_PREFIX_PAYINFO
						+ ".queryPayInfoByBizflowAndBizSysNo", param);

		return payInfos;
	}

}

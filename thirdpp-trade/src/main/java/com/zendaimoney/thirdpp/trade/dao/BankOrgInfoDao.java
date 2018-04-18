package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.BankOrgInfo;

@Repository
public class BankOrgInfoDao extends BaseDao<BankOrgInfo> {

	private static final String SQL_PREFIX_BANKORGINFO = "com.zendaimoney.thirdpp.trade.entity.BankOrgInfo";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}

	@SuppressWarnings("unchecked")
	public List<BankOrgInfo> getBankOrgInfoByBankCodeAndBankArea(
			String bankCode, String bankOrgProvinceNo,
			String bankOrgProvinceCityNo) throws SQLException {
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("bankCode", bankCode);
		parameters.put("bankOrgProvinceNo", bankOrgProvinceNo);
		parameters.put("bankOrgProvinceCityNo", bankOrgProvinceCityNo);
		return (List<BankOrgInfo>)sqlMapClient.queryForList(SQL_PREFIX_BANKORGINFO
				+ ".selectBankOrgByBankCodeAndBankArea", parameters);
		
	}
}

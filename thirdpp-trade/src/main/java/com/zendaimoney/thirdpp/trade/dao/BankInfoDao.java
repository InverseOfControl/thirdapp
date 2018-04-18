package com.zendaimoney.thirdpp.trade.dao;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.trade.entity.BankInfo;

@Repository
public class BankInfoDao extends BaseDao<BankInfo> {

	// BankInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_BANKINFO = "com.zendaimoney.thirdpp.trade.entity.BankInfo";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
	
	
	/**
	 * 根据银行编码获取银行记录
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public BankInfo queryBankInfoByCode(String bankCode) throws SQLException {
		BankInfo bankInfo = (BankInfo) this.sqlMapClient.queryForObject(
				SQL_PREFIX_BANKINFO + ".queryBankInfoByCode", bankCode);

		return bankInfo;
	}
	
	
	/**
	 * 获取银行信息列表。
	 * @param appCode
	 * @param ip
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<BankInfo> queryBankInfos() throws SQLException {
		return  (List<BankInfo>)this.sqlMapClient.queryForList(
				SQL_PREFIX_BANKINFO + ".queryBankInfos");
	}

}

package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.common.dao.BaseDao;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.PayInfo;
import com.zendaimoney.thirdpp.account.entity.PayTask;

@Repository
public class PayInfoDao extends BaseDao<PayInfo> {

	// PayInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_PAYINFO = "com.zendaimoney.thirdpp.account.entity.PayInfo";

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

	/**
	 * 根据reqid获取请求记录
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public PayInfo queryPayInfoByTradeFlow(Map<String, Object> paramMap) throws SQLException {
		PayInfo payInfo = (PayInfo) this.sqlMapClient.queryForObject(
				SQL_PREFIX_PAYINFO + ".queryPayInfoByTradeFlow", paramMap);
		if (payInfo != null) {
			payInfo.setFromHis(false);
			return payInfo;
		} else {
			payInfo = (PayInfo) this.sqlMapClient.queryForObject(
					SQL_PREFIX_PAYINFO + ".queryPayInfoByTradeFlowFromHis", paramMap);
			if (payInfo != null) {
				payInfo.setFromHis(true);
			}
		}
		return payInfo;
	}
	
	private void updateSettleTime(AccountInfoTemple payInfo) throws SQLException {
		if (!payInfo.isFromHis()) {
			this.sqlMapClient.update(SQL_PREFIX_PAYINFO + ".updateSettleTime", payInfo);
		} else {
			this.sqlMapClient.update(SQL_PREFIX_PAYINFO + ".updateHisSettleTime", payInfo);
		}
	}
	
	public void batchUpdate(List<AccountInfoTemple> payInfoList) throws SQLException {
		if (payInfoList != null && !payInfoList.isEmpty()) {
			sqlMapClient.startBatch();
			for (AccountInfoTemple payInfo : payInfoList) {
				if (payInfo != null) {
					this.updateSettleTime(payInfo);
				}
			}
			sqlMapClient.executeBatch();
		}
	}
	
}

package com.zendaimoney.thirdpp.account.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.CollectInfo;
import com.zendaimoney.thirdpp.common.dao.BaseDao;

@Repository
public class CollectInfoDao extends BaseDao<CollectInfo> {
	// CollectInfo实体类数据库操作前缀.
	private static final String SQL_PREFIX_COLLECTINFO = "com.zendaimoney.thirdpp.account.entity.CollectInfo";

	// 第二个数据源注入
	@Resource(name = "sqlMapClient")
	public SqlMapClient sqlMapClient;

	@SuppressWarnings("deprecation")
	@PostConstruct
	public void initSqlMapClient() {
		super.setSqlMapClient(sqlMapClient);
	}
	
	/**
	 * 根据reqid获取请求记录
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public CollectInfo queryCollectInfoByTradeFlow(Map<String, Object> paramMap) throws SQLException {
		CollectInfo collectInfo = (CollectInfo) this.sqlMapClient.queryForObject(
				SQL_PREFIX_COLLECTINFO + ".queryCollectInfoByTradeFlow", paramMap);
		if (collectInfo != null) {
			collectInfo.setFromHis(false);
			return collectInfo;
		} else {
			collectInfo = (CollectInfo) this.sqlMapClient.queryForObject(
					SQL_PREFIX_COLLECTINFO + ".queryCollectInfoHisByTradeFlow", paramMap);
			if (collectInfo != null) {
				collectInfo.setFromHis(true);
				return collectInfo;
			}
		}
		return collectInfo;
	}
	
	/**
	 * 更新交易状态及结果code
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public int updateByStatus(CollectInfo collectInfo) throws SQLException {
		int count = this.sqlMapClient.update(SQL_PREFIX_COLLECTINFO + ".updateByStatus", collectInfo);
		return count;
	}
	
	private void updateCollectInfo(AccountInfoTemple collectInfo) throws SQLException {
		if (!collectInfo.isFromHis()) {
			this.sqlMapClient.update(SQL_PREFIX_COLLECTINFO + ".updateCollectInfo", collectInfo);
		} else {
			this.sqlMapClient.update(SQL_PREFIX_COLLECTINFO + ".updateCollectInfoHis", collectInfo);
		}
	}
	
	public void batchUpdate(List<AccountInfoTemple> collectInfoList) throws SQLException {
		if (collectInfoList != null && !collectInfoList.isEmpty()) {
			sqlMapClient.startBatch();
			for (AccountInfoTemple collectInfo : collectInfoList) {
				if (collectInfo != null) {
					updateCollectInfo(collectInfo);
				}
			}
			sqlMapClient.executeBatch();
		}
	}

}

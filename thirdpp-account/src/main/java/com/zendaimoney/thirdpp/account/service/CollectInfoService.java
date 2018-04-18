package com.zendaimoney.thirdpp.account.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zendaimoney.thirdpp.account.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.CollectInfo;

@Service
public class CollectInfoService {

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;

	/**
	 * collectInfo修改操作
	 * 
	 * @param collectInfo
	 */
	public int update(CollectInfo collectInfo) throws SQLException{
		return collectInfoDao.update(collectInfo);
	}
	
	/**
	 * collectInfo批量修改操作
	 * 
	 * @param collectInfoList
	 */
	public void update(List<AccountInfoTemple> collectInfoList) throws SQLException{
		collectInfoDao.batchUpdate(collectInfoList);
	}
	
	/**
	 * 查询交易信息
	 * @param taskId
	 * @param tradeFlow
	 * @return
	 * @throws SQLException
	 */
	public CollectInfo queryCollectInfoByReqId(String tradeFlow) throws SQLException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("tradeFlow", tradeFlow);
		return collectInfoDao.queryCollectInfoByTradeFlow(paraMap);
	}
	
	/**
	 * collectInfo修改操作
	 * 
	 * @param collectInfo
	 * @throws SQLException 
	 */
	public int updateByStatus(CollectInfo collectInfo) throws SQLException {
		return collectInfoDao.updateByStatus(collectInfo);
	}

	public CollectInfo queryBizInfoByTradeFlow(String tradeFlow) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tradeFlow", tradeFlow);
		return collectInfoDao.queryCollectInfoByTradeFlow(paramMap);
	}
}

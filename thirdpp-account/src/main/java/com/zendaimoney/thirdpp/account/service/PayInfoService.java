package com.zendaimoney.thirdpp.account.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.zendaimoney.thirdpp.account.dao.PayInfoDao;
import com.zendaimoney.thirdpp.account.entity.AccountInfoTemple;
import com.zendaimoney.thirdpp.account.entity.PayInfo;

@Service
public class PayInfoService {
	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	public void insert(PayInfo payInfo) {
		payInfoDao.insert(payInfo);
	}
	
	public void update(PayInfo payInfo){
		payInfoDao.update(payInfo);
	}

	public PayInfo queryBizInfoByTradeFlow(String tradeFlow) throws SQLException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tradeFlow", tradeFlow);
		return payInfoDao.queryPayInfoByTradeFlow(paramMap);
	}
	
	/**
	 * collectInfo批量修改操作
	 * 
	 * @param collectInfoList
	 */
	public void update(List<AccountInfoTemple> payInfoList) throws SQLException{
		payInfoDao.batchUpdate(payInfoList);
	}
}

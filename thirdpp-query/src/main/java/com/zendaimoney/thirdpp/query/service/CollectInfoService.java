package com.zendaimoney.thirdpp.query.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.query.dao.CollectInfoDao;
import com.zendaimoney.thirdpp.query.entity.CollectInfo;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;

@Service
@Transactional
public class CollectInfoService {

	@Resource(name = "collectInfoDao")
	private CollectInfoDao collectInfoDao;

	@Autowired
	private AmqpService amqpService;
	
	/**
	 * collectInfo修改操作
	 * 
	 * @param collectInfo
	 */
	public int update(CollectInfo collectInfo) {
		return collectInfoDao.update(collectInfo);
	}
	
	public int updateAndSendMQ(CollectInfo collectInfo, MongoQueryVO vo) throws Exception {
		
		int count = 0;
		count = collectInfoDao.updateByStatus(collectInfo);
		
		if (count > 0) {
			// MQ消息推送
			amqpService.push(vo);
		}
		
		return count;
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
		return collectInfoDao.queryCollectInfoByReqId(paraMap);
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

}

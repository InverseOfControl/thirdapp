package com.zendaimoney.thirdpp.query.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.query.dao.PayInfoDao;
import com.zendaimoney.thirdpp.query.entity.PayInfo;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;

@Service
@Transactional
public class PayInfoService {

	@Resource(name = "payInfoDao")
	private PayInfoDao payInfoDao;

	@Autowired
	private AmqpService amqpService;

	/**
	 * PayInfo修改操作
	 * 
	 * @param PayInfo
	 */
	public int update(PayInfo payInfo) {
		return payInfoDao.update(payInfo);
	}
	
	public int updateAndSendMQ(PayInfo payInfo, MongoQueryVO vo) throws Exception {
		
		int count = 0;
		count = payInfoDao.updateByStatus(payInfo);
		
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
	public PayInfo queryPayInfoByReqId(String tradeFlow) throws SQLException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("tradeFlow", tradeFlow);
		return payInfoDao.queryPayInfoByReqId(paraMap);
	}
	
	public int updateByStatus(PayInfo payInfo) throws SQLException {
		return payInfoDao.updateByStatus(payInfo);
	}

}

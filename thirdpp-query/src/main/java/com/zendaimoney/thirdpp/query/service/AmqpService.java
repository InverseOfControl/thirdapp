package com.zendaimoney.thirdpp.query.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.entity.MqMessageInfo;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.util.Constants;
import com.zendaimoney.thirdpp.query.util.JSONHelper;

/**
 * MQ消息推送 服务
 * @author mencius
 *
 */
@Component
public class AmqpService {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(AmqpService.class);
		
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	private String MQ_MESSAGE_KEY = null;
	
	public void push(MongoQueryVO vo) throws Exception {
		
		logger.debug("进入MQ处理器");
		
		MqMessageInfo info = new MqMessageInfo();
		
		info.setBizType(vo.getBizTypeNo()); // 业务类型
		info.setTradeFlow(vo.getTradeFlow()); // 交易流水号
		
		// 运营方式(0线下运营1线上运营)
		if (Constants.OP_MODE_ONLINE.equals(vo.getOpMode())) {
			
			MQ_MESSAGE_KEY = ServerConfig.systemConfig.getMerge_online_key();
		} else if (Constants.OP_MODE_OFFLINE.equals(vo.getOpMode())) {
			MQ_MESSAGE_KEY = ServerConfig.systemConfig.getMerge_offline_key();
		}
		
		// 放入消息队列中
		amqpTemplate.convertAndSend(MQ_MESSAGE_KEY, JSONHelper.bean2json(info));
	}

}

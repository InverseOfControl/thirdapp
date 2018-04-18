package com.zendaimoney.thirdpp.query.mq.producter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.entity.MqQueryInfo;
import com.zendaimoney.thirdpp.query.exception.PlatformException;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.util.JSONHelper;
import com.zendaimoney.thirdpp.query.util.RabbitMQContext;

/**
 * MQ消息推送 服务
 * @author mencius
 *
 */
@Component
public class AmqpProductService {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(AmqpProductService.class);
		
	private AmqpTemplate amqpTemplate;
	
	public void push(MongoQueryVO vo, String collectionName) throws Exception {
		
		logger.debug("进入MQ处理器");
			
		MqQueryInfo info = new MqQueryInfo();
		
		info.set_id(vo.get_id()); // 查询对象主键
		info.setTradeFlow(vo.getTradeFlow()); // 交易流水号
		info.setCollectionName(collectionName); // 集合名称
		
		amqpTemplate = (AmqpTemplate) ServerConfig.getBean("amqpTemplate");
		// 放入消息队列中
		String routeKey = vo.getBizTypeNo() + vo.getPaySysNo();
		if (RabbitMQContext.routeKeyMap.containsKey(routeKey)) {
			
			amqpTemplate.convertAndSend(RabbitMQContext.routeKeyMap.get(routeKey), JSONHelper.bean2json(info));
		} else {
			throw new PlatformException("MongoQueryVO: " + vo + ",routeKey: " + routeKey + " not found!");
		}
		
		logger.info("send MQ Message:" + info);
	}

}

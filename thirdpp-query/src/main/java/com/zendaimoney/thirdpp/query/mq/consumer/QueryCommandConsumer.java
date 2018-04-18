package com.zendaimoney.thirdpp.query.mq.consumer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import com.alibaba.druid.util.StringUtils;
import com.rabbitmq.client.Channel;
import com.zendaimoney.thirdpp.query.action.Action;
import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.entity.MqQueryInfo;
import com.zendaimoney.thirdpp.query.mongo.MongoQueryVO;
import com.zendaimoney.thirdpp.query.thread.QueryActionTarget;
import com.zendaimoney.thirdpp.query.util.Constants;
import com.zendaimoney.thirdpp.query.util.JSONHelper;
import com.zendaimoney.thirdpp.query.util.MongoDBUtils;
import com.zendaimoney.thirdpp.query.util.QueryActionUtil;

/**
 * 通联支付 查询命令监听
 * @author mencius
 */
public class QueryCommandConsumer implements ChannelAwareMessageListener {

	private static final Log logger = LogFactory.getLog(QueryCommandConsumer.class);

	private AmqpTemplate amqpTemplate;
	
	/**
	 * 休眠时间
	 */
	private long sleepTime;
	
	/**
	 * 路由key
	 */
	private String queueKey;
	
	public QueryCommandConsumer(long sleepTime, AmqpTemplate amqpTemplate, String queueKey) {
		this.sleepTime = sleepTime;
		this.amqpTemplate = amqpTemplate;
		this.queueKey = queueKey;
		
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onMessage(Message message, Channel channel) {
		
		String jsonMsg = new String(message.getBody());
		try {
			
			// 第三方通道限制查询频率时间
			Thread.sleep(sleepTime);
			if (StringUtils.isEmpty(jsonMsg)) {
				return;
			}
			
			// 将接收到的消息转化为 MqQueryInfo对象
			MqQueryInfo mqQueryInfo = (MqQueryInfo) JSONHelper.json2Object(jsonMsg, MqQueryInfo.class);
			
			// 拼装查询条件 根据_id
			Map<String, Object> paramMap =new HashMap<String, Object>();
			paramMap.put("_id", mqQueryInfo.get_id());
			List<Document> lists = MongoDBUtils.query(mqQueryInfo.getCollectionName(), paramMap, 1);
			
			// 如果查询出结果集存在
			if (lists != null && lists.size() > 0) {
				
				Document vo = lists.get(0);
				
				MongoQueryVO queryVO = new MongoQueryVO();
				queryVO.fromDbObject(vo);
				
				QueryActionTarget target = QueryActionUtil.targetMap.get(queryVO.getBizTypeNo());
				if (target != null) {
					Class<? extends Action> clazz = target.getActionClazz();
					Action action = null;
					try {
						action = (Action) ServerConfig.getBean(clazz.getName());
						action.execute(queryVO, mqQueryInfo.getCollectionName());
						
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						MongoDBUtils.moveAndDelete(mqQueryInfo.getCollectionName(), vo);
					}
				} else {
					logger.info("QueryActionTarget is not found!");
					MongoDBUtils.moveAndDelete(mqQueryInfo.getCollectionName(), vo);
				}
			} else {
				MongoQueryVO vo = new MongoQueryVO();
				vo.set_id(mqQueryInfo.get_id());
				vo.setDealFlag(Constants.TPP_QUERY_DEALFLAG_DEALED);
				
				MongoDBUtils.updateOne(mqQueryInfo.getCollectionName(), vo, Constants.TPP_QUERY_DEALFLAG_INIT);
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			// 重新加入消息队列
			amqpTemplate.convertAndSend(queueKey, jsonMsg);
		}
	}
	
}

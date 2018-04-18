package com.zendaimoney.thirdpp.query.util;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.zendaimoney.thirdpp.query.conf.ServerConfig;
import com.zendaimoney.thirdpp.query.dao.TppsysQueryInfoDao;
import com.zendaimoney.thirdpp.query.entity.TppsysQueryInfo;
import com.zendaimoney.thirdpp.query.mq.consumer.QueryCommandConsumer;

/**
 * rabbit for java
 * @author user
 * MQ消息消费处理器组装类
 */
public class RabbitMQContext {
	
	// 日志工具类
	public static Log logger = LogFactory.getLog(RabbitMQContext.class);
	
	/**
	 * 通道集合
	 */
	public static Map<String, String> routeKeyMap = new HashMap<String, String>();
	
	@Autowired
	private TppsysQueryInfoDao tppsysQueryInfoDao;
	
	/**
	 * MQ 服务器地址
	 */
	@Value("${rabbitmq.host}")
	private String RABBIT_ADDRESS;
	
	/**
	 * MQ 服务器端口
	 */
	@Value("${rabbitmq.port}")
	private int RABBIT_PORT;
	
	/**
	 * MQ 服务器用户名
	 */
	@Value("${rabbitmq.username}")
	private String RABBIT_NAME;
	
	/**
	 * MQ 服务器密码
	 */
	@Value("${rabbitmq.password}")
	private String RABBIT_PASS;
	
	/**
	 * MQ 服务器虚拟主机
	 */
	@Value("${rabbitmq.virtualhost}")
	private String VIRTAL_HOST;
	
	/**
	 * Rabbit 交换机名称
	 */
	@Value("${rabbitmq.exchangeName}")
	private String exchangeName;
	
	/**
	 * 初始化
	 */
	public void init() {
		
		try {
			
			// 获取数据库配置信息
			List<TppsysQueryInfo> tppsysQueryInfos = tppsysQueryInfoDao.queryTppsysQueryInfos(null, null, ServerConfig.systemConfig.getAppName());
			
			// 循环遍历配置表
			for (TppsysQueryInfo info : tppsysQueryInfos) {
				
				// 获取Rabbit连接工厂
				CachingConnectionFactory factory = getCachingConnectionFactory();
				
				// set up the queue, exchange, binding on the broker
				RabbitAdmin admin = new RabbitAdmin(factory);
				
				/* 
				 * 初始化交换机对象 TopicExchange
				 * <!-- durable=true,交换机持久化,rabbitmq服务重启交换机依然存在,保证不丢失; durable=false,相反 -->
				 * <!-- auto-delete=true:无消费者时，队列自动删除; auto-delete=false：无消费者时，队列不会自动删除 -->
				 */
				TopicExchange exchange = new TopicExchange(exchangeName, true, false);
				
				admin.isAutoStartup();
				// 是否要声明交换机
	//			if (exchange.shouldDeclare()) {
	//				admin.declareExchange(exchange);
	//			}
				 // 实例化
				 RabbitTemplate template = new RabbitTemplate(factory);
				 template.setExchange(exchangeName);
				 
				 String bizType = info.getBizType();
				 String paySysNo = info.getPaySysNo();
				 
				 String queueName = "q_tpp_query_" + bizType + "_" + paySysNo;
				 String routeKey = "r_tpp_" + bizType + "_" + paySysNo;
				 
				 String key = bizType + paySysNo;
				 
				 if (!routeKeyMap.containsKey(key)) {
					 routeKeyMap.put(key, routeKey);
				 }
				 /* 实例化队列对象
				  * <!-- durable=true,交换机持久化,rabbitmq服务重启交换机依然存在,保证不丢失; durable=false,相反 -->
				  * <!-- auto-delete=true:无消费者时，队列自动删除; auto-delete=false：无消费者时，队列不会自动删除 -->
				  * <!-- 排他性，exclusive=true:首次申明的connection连接下可见; exclusive=false：所有connection连接下都可见 -->
				  */
				 Queue queue = new Queue(queueName, true, false, false);
				 // 是否要声明队列
				 if (queue.shouldDeclare()) {
					 admin.declareQueue(queue);
				 }
				 
				 // set up the queue, exchange, binding on the broker
				 admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routeKey));
				 
				 // set up the listener and container
				 SimpleMessageListenerContainer container =
				            new SimpleMessageListenerContainer(factory);
				 
				 // 实例化消费者监听器
				 QueryCommandConsumer listener= new QueryCommandConsumer(info.getSleepTime(), template, routeKey);
				 
				 // exchange queue binging key 绑定
				 MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
				 container.setMessageListener(adapter);
				 container.setQueueNames(queueName);
				 container.start();
			}
			
		} catch (BeansException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * 获取Rabbit连接工厂
	 * @param props
	 * @return factory
	 */
	public CachingConnectionFactory getCachingConnectionFactory() {
		
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setAddresses(RABBIT_ADDRESS);
		factory.setPort(RABBIT_PORT);
		factory.setUsername(RABBIT_NAME);
		factory.setPassword(RABBIT_PASS);
		factory.setVirtualHost(VIRTAL_HOST);
		// 设置心跳时间，防止长时间未活动被防火墙杀死,默认600秒,单位：秒
		factory.setRequestedHeartBeat(60*4);
		return factory;
	}
	
}

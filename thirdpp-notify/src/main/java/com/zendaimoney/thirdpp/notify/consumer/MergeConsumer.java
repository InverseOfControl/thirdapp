package com.zendaimoney.thirdpp.notify.consumer;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.rabbitmq.client.Channel;
import com.zendaimoney.thirdpp.notify.conf.ServerConfig;
import com.zendaimoney.thirdpp.notify.entity.MqMessage;
import com.zendaimoney.thirdpp.notify.entity.TotalOrder;
import com.zendaimoney.thirdpp.notify.service.IMergeService;
import com.zendaimoney.thirdpp.notify.util.JackJsonUtil;

/**
 * 消费消息,合并订单
 * 
 * @author 00225642
 * 
 */
@Service
public class MergeConsumer implements ChannelAwareMessageListener {

	private static final Logger logger = LoggerFactory
			.getLogger(MergeConsumer.class);

	private long MERGE_SLEEP_TIME = ServerConfig.systemConfig
			.getMergeSleepTime();

	@Autowired
	private IMergeService mergeService;

	@Override
	public void onMessage(Message message, Channel channel) throws IOException {
		String jsonMsg = new String(message.getBody());
		try {
			if (StringUtils.isEmpty(jsonMsg)) {
				return;
			}
			Thread.sleep(MERGE_SLEEP_TIME);
			MqMessage resultMsg = (MqMessage) JackJsonUtil.strToObj(jsonMsg,
					MqMessage.class);
			logger.debug("【接收mq消息】{}", resultMsg.toString());
			// 合单操作
			TotalOrder totalOrder = mergeService.merge(resultMsg);
			if (null == totalOrder) {
				return;
			}
			// 更新操作
			mergeService.updateTaskAndSaveNotify(totalOrder);
		} catch (InterruptedException e) {
			logger.error("【线程运行时异常】" + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("【数据库异常】" + e.getMessage());
			// 重新加入消息队列
			mergeService.putMergeMq(jsonMsg);
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error("【数据库异常】" + e.getMessage());
			// 重新加入消息队列
			mergeService.putMergeMq(jsonMsg);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【系统异常】" + e.getMessage());
		} finally {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(),
					false);
		}
	}

}

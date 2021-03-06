package com.zendaimoney.thirdpp.trade.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zendaimoney.thirdpp.trade.util.JSONHelper;

@Component
public class MqProducter {

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 
	 * @param keyName
	 * @param message
	 */
	public void sendMessage(String keyName, TaskMergeVo vo) {
		if (vo != null) {
			amqpTemplate.convertAndSend(keyName, JSONHelper.bean2json(vo));
		}

	}

}

package com.zendaimoney.thirdpp.channel.communication;

import org.springframework.stereotype.Component;

/**
 * @author vincent
 * 2013-12-30下午6:45:34
 * 
 */
@Component
public abstract class GenericCommunication extends Communication {
	
	public Message sendMessageByHttp(Message message) throws Exception{
		return message;
	}

	
}

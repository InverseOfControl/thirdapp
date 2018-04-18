package com.zendaimoney.thirdpp.channel.communication;


public abstract class Communication {
	public abstract Message sendMessageByHttp(Message message) throws Exception;

}

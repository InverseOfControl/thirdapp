
package com.zendaimoney.trust.channel.socket.clent;

import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.exception.PlatformException;

/**
 *  命令发送接口 
 *	@author: mencius
 */
public interface SocketSender {
	
	/**
	 * 同步发送请求
	 * @param request 请求消息，json格式字串
	 * @return  请求应答消息，json格式字串
	 * @throws PlatformException
	 */
	public String syncRequest(String request) throws PlatformException;
	
	/**
	 * 同步通信请求
	 * @param message 
	 * @param encodeType 编码
	 * @throws PlatformException
	 */
	public Message syncRequest(Message message, String encodeType) throws PlatformException;
}

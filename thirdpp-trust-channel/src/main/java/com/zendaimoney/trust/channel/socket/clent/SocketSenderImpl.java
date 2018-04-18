package com.zendaimoney.trust.channel.socket.clent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.zendaimoney.trust.channel.communication.Message;
import com.zendaimoney.trust.channel.exception.PlatformErrorCode;
import com.zendaimoney.trust.channel.exception.PlatformException;
import com.zendaimoney.trust.channel.util.Constants;
import com.zendaimoney.trust.channel.util.LogPrn;


/**
 *  socket请求发送实现
 *	@author: mencius
 */
@Service
public class SocketSenderImpl implements SocketSender{
	
	// 日志工具类
	private static final LogPrn logger = new LogPrn(SocketSenderImpl.class);
		
	private static Random r=new Random();
	private static int BUFFERSIZE=4096;
	private InetSocketAddress[] hosts; //服务器地址集合，支持集群
	
	/**
	 * 招商银行通讯socket地址
	 */
	@Value("${CMB_ADDRESS}")
	private String address;
	
	/**
	 * 招商银行通讯socket端口
	 */
	@Value("${CMB_PORT}")
	private String port;
	
	
	public SocketSenderImpl(InetSocketAddress[] hosts) {
		super();
		this.hosts = hosts;
	}

	public SocketSenderImpl(InetSocketAddress host){
		this(new InetSocketAddress[]{host});
	}
	
	public SocketSenderImpl() {
	}
	
	
	@Override
	public String syncRequest(String request) throws PlatformException {
		if(hosts==null||hosts.length==0)
			throw new PlatformException(PlatformErrorCode.SYSTEM_BUSY, "No server hosts information");
		InetSocketAddress hostAddr=hosts[r.nextInt(hosts.length)];
		String response=null;
		SocketChannel channel=null;
		try{
			channel=SocketChannel.open(hostAddr);
			Charset charset=Charset.forName("utf-8");
			channel.write(charset.encode(request));
			ByteBuffer buffer=ByteBuffer.allocate(BUFFERSIZE);
			if(channel.read(buffer)!=-1){
				buffer.flip();
				CharBuffer cb=charset.decode(buffer);
				buffer.clear();//清空缓存
				response=cb.toString();
			}
		}catch(IOException e){
			logger.error(e.getMessage(), e);
		}finally{
			if(channel!=null)
				try{
					channel.close();
				}catch(IOException e){
					e.printStackTrace();
				}
		}
		return response;
	}

	/**
	 * 与招商银行进行socket通信
	 */
	@Override
	public Message syncRequest(Message message, String encodeType) throws PlatformException {
		
		String response= null;
		SocketChannel channel=null;
		Charset charset = null;
		try{
			
			// socket发送请求
			try {
				// 通信socket地址对象
				InetSocketAddress hostAddr= new InetSocketAddress(address, Integer.valueOf(port));
				// 建立socket连接通道
				channel=SocketChannel.open(hostAddr);
				charset = Charset.forName(encodeType);
				channel.write(charset.encode(message.getRequestInfo()));
				logger.info("request:"+message.getRequestInfo());
			} catch (Exception e) {
				logger.error("发送socket请求：" + e.getMessage(), e);
				message.setStatusCode(Constants.CommunicationStatus.SOCKET_REQUEST_FAIL.getCode());
				
			}
			
			// 如果发送socket请求过程中发送异常，Message状态码为SOCKET_REQUEST_FAIL("101", "socket请求失败")
			if (Constants.CommunicationStatus.SOCKET_REQUEST_FAIL.getCode().equals(message.getStatusCode())) {
				return message;
			}
			
			
			// socket接收响应
			ByteBuffer buffer=ByteBuffer.allocate(BUFFERSIZE);
			
			try {
				if(channel.read(buffer)!=-1){
					buffer.flip();
					CharBuffer cb=charset.decode(buffer);
					buffer.clear();//清空缓存
					response=cb.toString();
				}
			} catch (Exception e) {
				
				logger.error("接收socket响应：" + e.getMessage(), e);
				message.setStatusCode(Constants.CommunicationStatus.SOCKET_RESPONSE_FAIL.getCode());
			}
			
			// 如果接收socket响应过程中发送异常，Message状态码为SOCKET_RESPONSE_FAIL("102", "socket响应失败")
			if (Constants.CommunicationStatus.SOCKET_RESPONSE_FAIL.getCode().equals(message.getStatusCode())) {
				return message;
			}
			
			logger.info("response:" + response);
			// 记录响应报文
			message.setResponseInfo(response);
			message.setMessage(response);
			message.setStatusCode(Constants.CommunicationStatus.SOCKET_OK_STATUS.getCode());
			
		}finally{
			if(channel!=null)
				try{
					channel.close();
				}catch(IOException e){
					logger.error(e.getMessage(), e);
				}
		}
		
		return message;
	}

}

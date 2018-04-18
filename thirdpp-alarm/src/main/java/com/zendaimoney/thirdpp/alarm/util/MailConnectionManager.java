package com.zendaimoney.thirdpp.alarm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;
import com.zendaimoney.thirdpp.alarm.common.MailChannel;

public class MailConnectionManager{
	public static Log logger = LogFactory.getLog(MailConnectionManager.class);
	
	private static MailConnectionManager manager = new MailConnectionManager();
	
	private Map<String, MailConnection> map = new HashMap <String, MailConnection>(); 
	
	private MailConnection defaultCon = null;
	
	private MailConnectionManager(){}
	
	class MailConnection{
		
		private Session session;
		
		private Transport transport;
		
		private MailChannel channel;
		
		public Session getSession() {
			if(channel.isAuth()){
				this.connect();
			}
			return this.session;
		}
		
		public Transport getTransport() throws MessagingException {
			// 获取邮件服务器SMTP
			try{
				transport = session.getTransport("smtp");
				if(channel.isAuth()){
					this.transport.connect(channel.getHost(), channel.getPort(), channel.getUserName(), channel.getPassword());
				} else {
					this.transport.connect(channel.getHost(), channel.getPort(), null, null);
				}
			} catch(MessagingException e){
				logger.error("邮件链接异常,邮件服务器："+channel.getHost(), e);
				throw new CsswebException("邮件链接异常,需重新尝试", e);
			}
			return this.transport;
		}

		public MailConnection(MailChannel channel){
			this.channel = channel;
			this.connect();
		}
		
		public boolean isConnected() {
			if (transport != null) {
				return this.transport.isConnected();
			}
			return false;
		}
		/**
		 * 连接邮件服务器
		 * 
		 * @throws MessagingException
		 */
		private void connect() {
			// 设置邮件服务器属性
			Properties props = new Properties();
			props.put("mail.smtp.host", channel.getHost());
			props.put("mail.smtp.port", channel.getPort());
			props.put("mail.smtp.auth", channel.isAuth());
			// 获取一个邮件会话对象
			Authenticator authenticator = null;   
			//如果需要身份认证，则创建一个密码验证器     
			if (channel.isAuth()) {    
				authenticator = new MyAuthenticator(channel.getUserName(), channel.getPassword());   
			}	
			this.session = Session.getDefaultInstance(props, authenticator);
			// 设置邮件会话模式,主要用于调试
			this.session.setDebug(channel.isDebug());
		}
	}
	
	public static MailConnectionManager getInstance() {
		if(null == manager.defaultCon){
			manager.getDefaultMailConnection();
		}
		return manager;
	}
	
	private MailConnection getDefaultMailConnection() {
		if(null == this.defaultCon) {
			MailChannel channel = this.getDefaulMailChannel();
			defaultCon = new MailConnection(channel);
		}
		return defaultCon;
	}
	
	public MailChannel getDefaulMailChannel(){
		MailConfig mailConfig = CsswebConfig.mailConfig;
		MailChannel channel = new MailChannel();
		channel.setHost(mailConfig.getHost());
		channel.setPort(mailConfig.getPort());
		channel.setUserName(mailConfig.getUsername());
		channel.setPassword(mailConfig.getPassword());
		channel.setAuth(mailConfig.getAuth());
		channel.setDebug(mailConfig.getDebug());
		channel.setDisplayName(mailConfig.getDisplayName());
		return channel;
	}
	
	public Session getSession(MailChannel channel) throws MessagingException {
		Session session = null;
		if(null != channel){
			MailConnection con = map.get(channel.getChannelId());
			if(null == con){
				con = new MailConnection(channel);
				map.put(channel.getChannelId(), con);
			}
			session = con.getSession();
		} else {
			session = this.getDefaultSession();
		}
		return session;
	}
	
	public Transport getTransport(MailChannel channel) throws MessagingException {
		Transport transport = null;
		if(null != channel){
			MailConnection con = map.get(channel.getChannelId());
			if(null == con){
				con = new MailConnection(channel);
				map.put(channel.getChannelId(), con);
			}
			transport = con.getTransport();
			logger.warn("邮件服务器："+transport.isConnected());	
		} else {
			transport = this.getDefaultTransport();
		}
		return transport;
	}
	
	public Session getDefaultSession() throws MessagingException{
		return getDefaultMailConnection().getSession();
	}
	
	public Transport getDefaultTransport() throws MessagingException{
		Transport transport = getDefaultMailConnection().getTransport();
		MailConnection con = getDefaultMailConnection();
		transport = con.getTransport();
		if(!transport.isConnected()){
			con.connect();
		}			
		return transport;		
	}	
	
	public void clear() {
		map.clear();
	}
}
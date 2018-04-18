package com.zendaimoney.thirdpp.alarm.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zendaimoney.thirdpp.alarm.common.exception.CsswebException;
import com.zendaimoney.thirdpp.alarm.common.MailChannel;

public class MailServer {

	public static Log logger = LogFactory.getLog(MailServer.class);
	private static MailServer javaMailServer;
	private MailServer() {}

	enum TYPE {
		HTML("text/html; charset=utf-8"), TXT("text/txt; charset=utf-8");

		private String value;

		TYPE(String s) {
			this.value = s;
		}

		public String getValue() {
			return value;
		}
	}

	public static MailServer getInstance() {
		if (javaMailServer == null) {
			try {
				javaMailServer = new MailServer();
			} catch (Exception e) {
				logger.error("连接邮件服务器失败");
				throw new CsswebException(e);
			}
		}
		return javaMailServer;
	}

	
	public void send(MailInfo mailInfo) throws SendFailedException,
			UnsupportedEncodingException, MessagingException, Exception {
		MimeMessage mimeMessage = buildMimeMessage(mailInfo);
		MailChannel channel = MailConnectionManager.getInstance().getDefaulMailChannel();
		Transport transport = MailConnectionManager.getInstance().getTransport(channel);
		Address[] addresses = mimeMessage.getAllRecipients();
		transport.sendMessage(mimeMessage, addresses);
	}

	private MimeMessage buildMimeMessage(MailInfo mailInfo)
			throws UnsupportedEncodingException, MessagingException, Exception {
		MailChannel channel = MailConnectionManager.getInstance().getDefaulMailChannel();
		Session session = MailConnectionManager.getInstance().getSession(channel);
		MimeMessage mimeMessage = new MimeMessage(session);
		if(null != channel){
			if(null != channel.getDisplay() && !channel.getDisplay().isEmpty()){
				mailInfo.setFrom(channel.getDisplay());	
			} else {
				if(null == mailInfo.getFrom() || mailInfo.getFrom().isEmpty()){
					mailInfo.setFrom(CsswebConfig.mailConfig.getDisplay());	
				}
			}
		} else {
			mailInfo.setFrom(CsswebConfig.mailConfig.getDisplay());	
		}
		Address from = new InternetAddress(mailInfo.getFrom());
		mimeMessage.setFrom(from);
		try{
			if (mailInfo.getFrom() != null && !mailInfo.getFrom().isEmpty()) {
				mimeMessage.setReplyTo(InternetAddress.parse(mailInfo.getFrom()));
			}
		}catch(AddressException e){
			logger.error("setReplyTo格式错误："+mailInfo.getFrom());
		}	
		try{
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailInfo.getTo()));
		}catch(AddressException e){
			logger.error("RecipientType.TO格式错误："+mailInfo.getTo());
		}		
		if (mailInfo.getCc() != null) {
			try{
				mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mailInfo.getCc(), false));
			}catch(AddressException e){
				logger.error("CC格式错误："+mailInfo.getCc());
			}
		}
		if (mailInfo.getBcc() != null) {
			try{
				mimeMessage.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailInfo.getBcc(), false));
			}catch(AddressException e){
				logger.error("BCC格式错误："+mailInfo.getBcc());
			}
		}
		mimeMessage.setSubject(mailInfo.getTitle());
		mimeMessage.setSentDate(new Date());
		Multipart mainPart = new MimeMultipart();
		BodyPart html = new MimeBodyPart();
		html.setContent(new String(mailInfo.getBody()), TYPE.HTML.getValue());
		mainPart.addBodyPart(html);
		mimeMessage.setContent(mainPart);
		return mimeMessage;
	}
}
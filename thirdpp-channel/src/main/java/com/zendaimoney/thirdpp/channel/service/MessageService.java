package com.zendaimoney.thirdpp.channel.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.thirdpp.channel.dao.MessageDao;
import com.zendaimoney.thirdpp.channel.entity.MessageInfo;

@Service
@Transactional
public class MessageService {

	@Resource(name = "messageDao")
	private MessageDao messageDao;

	/**
	 * 记录请求操作
	 * 
	 * @param request
	 */
	public void insert(MessageInfo message) {
		messageDao.insert(message);
	}

	
}

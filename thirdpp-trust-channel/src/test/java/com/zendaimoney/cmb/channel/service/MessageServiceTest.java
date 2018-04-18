package com.zendaimoney.cmb.channel.service;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.trust.channel.entity.MessageInfo;
import com.zendaimoney.trust.channel.service.MessageService;
import com.zendaimoney.trust.channel.util.IDGenerateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class MessageServiceTest {

	@Autowired
	private MessageService messageService;

	@Test
	public void test() throws SQLException {
		
		MessageInfo message = new MessageInfo();
		
		message.setMessage("message");
		message.setMsgType(MessageInfo.MSG_TYPE_Q);
		message.setPaySysNo(ThirdType.ALLINPAY.getCode());
		message.setReqId(IDGenerateUtil.createReqId());
		message.setSpare1("spare1");
        message.setSpare2("spare2");
        messageService.insert(message);
	}

}

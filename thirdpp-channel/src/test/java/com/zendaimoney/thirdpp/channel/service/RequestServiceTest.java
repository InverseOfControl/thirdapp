package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.channel.entity.Request;
import com.zendaimoney.thirdpp.channel.util.Constants.MessageStatus;
import com.zendaimoney.thirdpp.channel.util.IDGenerateUtil;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class RequestServiceTest {
	
	@Autowired
	private RequestService requestService;
	
	@Test
	public void test() throws SQLException{
		
		Request r = new Request();
		
		r.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		r.setBizType(BizType.BROKER_COLLECT.getCode());
		r.setPaySysNo(ThirdType.ALLINPAY.getCode());
		r.setPayTransFlow("payTransFlow");
		r.setReqId(IDGenerateUtil.createReqId());
		r.setSpare1("spare1");
		r.setSpare2("spare2");
		r.setStatus(MessageStatus.MESSAGE_INIT.getCode());
		r.setTransferFlow("transferFlow");
		requestService.insert(r);
		
		r = requestService.queryRequestByReqId("20150423171702410563");
		
		System.out.println(r.getReqId());
		
		r = new Request();
		
		r.setReqId("20150423171702410563");
		r.setStatus(MessageStatus.RECEIVED_RESPONSE.getCode());
		//r.setFailReason("刘");
		requestService.update(r);
		
		
		
	}

}

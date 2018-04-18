package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.channel.entity.TradeResult;
import com.zendaimoney.thirdpp.channel.util.IDGenerateUtil;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class TradeResultServiceTest {

	@Autowired
	private TradeResultService tradeResultService;

	@Test
	public void test() throws SQLException {
		TradeResult tradeResult = new TradeResult();
        tradeResult.setBizType(BizType.BROKER_COLLECT.getCode());
        tradeResult.setPaySysNo(ThirdType.ALLINPAY.getCode());
        tradeResult.setPayTransFlow("payTransFlow");
        tradeResult.setReqId(IDGenerateUtil.createReqId());
        tradeResult.setTransferFlow("transferFlow");
        tradeResult.setTransRepCode("21");
        tradeResult.setTransRepInfo("transRepInfo");
        
        tradeResultService.insert(tradeResult);
	}

}

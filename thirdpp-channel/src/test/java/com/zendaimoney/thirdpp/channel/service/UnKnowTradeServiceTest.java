package com.zendaimoney.thirdpp.channel.service;

import java.sql.SQLException;
import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.thirdpp.channel.mongo.UnKnowTradeService;
import com.zendaimoney.thirdpp.channel.mongo.UnknowTradeVO;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class UnKnowTradeServiceTest {

	@Autowired
	private UnKnowTradeService unKnowTradeService;

	@Test
	public void test() throws SQLException {
		UnknowTradeVO unknowTradeVO = new UnknowTradeVO();
		unknowTradeVO.setAmount("1");
		unknowTradeVO.setBizSysNo(BizSys.ZENDAI_2001_SYS.getCode());
		unknowTradeVO.setBizTypeNo(BizType.BROKER_COLLECT.getCode());
		unknowTradeVO.setCreateTime(CalendarUtils.parsefomatCalendar(
				Calendar.getInstance(), CalendarUtils.LONG_FORMAT_LINE));
		unknowTradeVO.setPaySysNo(ThirdType.YONGYOUUNIONPAY.getCode());
		unknowTradeVO.setSource("source");
		unknowTradeVO.setTradeFlow("tradeFlow");
		unKnowTradeService.add(unknowTradeVO);
	}

}

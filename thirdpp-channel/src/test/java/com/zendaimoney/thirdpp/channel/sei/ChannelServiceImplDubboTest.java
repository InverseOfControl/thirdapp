package com.zendaimoney.thirdpp.channel.sei;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zendaimoney.thirdpp.channel.action.DispatchAction;
import com.zendaimoney.thirdpp.channel.filter.BankRequestFilter;
import com.zendaimoney.thirdpp.channel.filter.ChannelRequestFilter;
import com.zendaimoney.thirdpp.channel.pub.service.IChannelService;
import com.zendaimoney.thirdpp.channel.pub.vo.*;
import com.zendaimoney.thirdpp.channel.service.RequestService;
import com.zendaimoney.thirdpp.channel.util.CalendarUtils;
import com.zendaimoney.thirdpp.channel.util.LogPrn;
import com.zendaimoney.thirdpp.common.enums.BizSys;
import com.zendaimoney.thirdpp.common.enums.BizType;
import com.zendaimoney.thirdpp.common.enums.ThirdType;
import com.zendaimoney.thirdpp.common.vo.AttachmentResponse;
import com.zendaimoney.thirdpp.common.vo.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test.xml"})
public class ChannelServiceImplDubboTest {
	
	private static final LogPrn logger = new LogPrn(ChannelServiceImplDubboTest.class);



	@Autowired
	IChannelService channelService;

	@Test
	public void kjtpayCommond() throws SQLException, InterruptedException {

		CollectReqVo vo = new CollectReqVo();

		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.KJTPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setInfoCategoryCode("20011");
		;
		vo.setAmount(new BigDecimal("0.01"));
		vo.setTradeFlow(CalendarUtils.getFormatNow());
		vo.setPayerBankCode("00080003");// 付款方银行支行行号
		vo.setPayerBankCardNo("6222021001072465757");
		vo.setPayerName("郝文");
		vo.setPayerIdType("1");// 付款方证件类型
		vo.setPayerId("370983198706191356");// 付款方证件号
		vo.setCurrency("0");
		vo.setPayerBankCardType("1");
		vo.setPayerAccountNo("88992222");
		vo.setPayerIdType("0");
		
		Response response = channelService.collectCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId()
				+ "tradeFlow:" + vo.getTradeFlow());
	}
	
	@Test
	public void kjtQueryCommond() throws SQLException, InterruptedException {


		QueryReqVo vo = new QueryReqVo();
		vo.setBizType(BizType.BROKER_COLLECT);
		vo.setThirdType(ThirdType.KJTPAY);
		vo.setBizSys(BizSys.ZENDAI_2004_SYS);
		vo.setTradeFlow("20161230105327");
		vo.setInfoCategoryCode("20011");

		Response response = channelService.queryCommond(vo);
		System.out.println("code:" + response.getCode() + "msg:"
				+ response.getMsg() + ",flow:" + response.getFlowId());
	}



}
